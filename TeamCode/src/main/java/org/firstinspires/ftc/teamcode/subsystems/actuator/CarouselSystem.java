package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;

/**
 * Indexed carousel with slots, color sensing, and
 * smooth ramping servo control between positions.
 */
@Config // Optional: for FTC Dashboard live tuning (if you use it)
public class CarouselSystem {

    private final Servo carouselServo;
    private final ColorSensorArray colorSensorArray;

    private ArtifactState targetArtifactState = ArtifactState.EMPTY;
    private CarouselPosition currentPosition = CarouselPosition.UNSET;

    // ========== Panel / Dashboard Configurables ==========
    /** Max change in servo position per second (0..1 / second). */
    public static double MAX_POSITION_CHANGE_PER_SEC = 2.0;

    /** Safety bounds for servo positions. */
    public static double MIN_SERVO_POS = 0.0;
    public static double MAX_SERVO_POS = 1.0;

    // ========== Ramping State ==========
    private double currentServoPos;  // what we’ve actually sent to the servo
    private double targetServoPos;   // where we WANT the servo to go
    private final ElapsedTime rampTimer = new ElapsedTime();

    public CarouselSystem(Servo carouselServo, ColorSensorArray colorSensorArray) {
        this.carouselServo = carouselServo;
        this.colorSensorArray = colorSensorArray;

        // Initialize ramping state to current hardware position
        this.currentServoPos = carouselServo.getPosition();
        this.targetServoPos  = currentServoPos;
        rampTimer.reset();
    }

    // ========== Sensor Access ==========
    public ArtifactState getArtifactState(ColorSensorPosition colorSensorPosition) {
        return colorSensorArray.getArtifactState(colorSensorPosition);
    }

    // Internal: send to servo and clamp values
    private void setServoPositionInternal(double position) {
        currentServoPos = Range.clip(position, MIN_SERVO_POS, MAX_SERVO_POS);
        carouselServo.setPosition(currentServoPos);
    }

    public double getServoPosition() {
        return currentServoPos;
    }

    // ========== Public Position API (Command-Based Style) ==========
    public CarouselPosition getPosition() {
        return currentPosition;
    }

    /**
     * High-level command: move carousel to this logical position.
     * Applies ramping over time via update().
     */
    public void setPosition(CarouselPosition position) {
        currentPosition = position;
        targetServoPos = position.getServoPosition();
    }

    /**
     * Force servo to jump instantly to a logical position.
     * (Use sparingly – mainly at init if needed.)
     */
    public void forceImmediatePosition(CarouselPosition position) {
        currentPosition = position;
        targetServoPos = position.getServoPosition();
        setServoPositionInternal(targetServoPos);
    }

    /**
     * Call this once per loop (TeleOp & Auto) to apply ramping.
     */
    public void update() {
        double dt = rampTimer.seconds();
        rampTimer.reset();

        if (dt <= 0) return;

        double maxDelta = MAX_POSITION_CHANGE_PER_SEC * dt;
        double delta = targetServoPos - currentServoPos;

        // If we're close enough, no need to move
        if (Math.abs(delta) < 1e-4) {
            return;
        }

        // Limit how much we move this cycle
        delta = Range.clip(delta, -maxDelta, maxDelta);
        setServoPositionInternal(currentServoPos + delta);
    }

    // ========== Carousel Navigation Commands ==========

    public void goToNextShootPosition() {
        setPosition(CarouselPosition.nextShootPosition(currentPosition));
    }

    public void goToPreviousShootPosition() {
        // BUGFIX: this previously called nextShootPosition(...)
        setPosition(CarouselPosition.previousShootPosition(currentPosition));
    }

    public void goToNextIntakePosition() {
        setPosition(CarouselPosition.nextIntakePosition(currentPosition));
    }

    public void goToPreviousIntakePosition() {
        setPosition(CarouselPosition.previousIntakePosition(currentPosition));
    }

    // ========== Targeting Logic ==========

    /**
     * Sets the artifact of the wanted state to the shoot position so that it can be shot.
     * @param state artifact state wanted in shoot position
     */
    public void setTargetArtifactToShoot(ArtifactState state) {
        // If we’re on an intake slot, first move to next shoot position
        if (currentPosition == CarouselPosition.INTAKE_SLOT_0 ||
            currentPosition == CarouselPosition.INTAKE_SLOT_1 ||
            currentPosition == CarouselPosition.INTAKE_SLOT_2) {

            setPosition(CarouselPosition.nextShootPosition(currentPosition));
            return;
        }

        boolean ballInShootSlot;
        boolean ballInRightSlot;
        boolean ballInLeftSlot;

        if (state == ArtifactState.ANY) {
            ballInShootSlot = (getArtifactState(ColorSensorPosition.SHOOT) != ArtifactState.EMPTY);
            ballInRightSlot = (getArtifactState(ColorSensorPosition.RIGHT) != ArtifactState.EMPTY);
            ballInLeftSlot  = (getArtifactState(ColorSensorPosition.LEFT)  != ArtifactState.EMPTY);
        } else {
            ballInShootSlot = (getArtifactState(ColorSensorPosition.SHOOT) == state);
            ballInRightSlot = (getArtifactState(ColorSensorPosition.RIGHT) == state);
            ballInLeftSlot  = (getArtifactState(ColorSensorPosition.LEFT)  == state);
        }

        setSlotToNearestBall(currentPosition, ballInShootSlot, ballInRightSlot, ballInLeftSlot);
    }

    private void setSlotToNearestBall(CarouselPosition currentPosition,
                                      boolean ballInShootSlot,
                                      boolean ballInRightSlot,
                                      boolean ballInLeftSlot) {

        if (!ballInShootSlot) {
            switch (currentPosition) {
                case SHOOT_SLOT_0:
                    if (ballInRightSlot) {
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setPosition(CarouselPosition.SHOOT_SLOT_1);
                    } else if (ballInLeftSlot) {
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setPosition(CarouselPosition.SHOOT_SLOT_2);
                    }
                    break;

                case SHOOT_SLOT_1:
                    if (ballInLeftSlot) {
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setPosition(CarouselPosition.SHOOT_SLOT_0);
                    } else if (ballInRightSlot) {
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setPosition(CarouselPosition.SHOOT_SLOT_2);
                    }
                    break;

                case SHOOT_SLOT_2:
                    if (ballInLeftSlot) {
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setPosition(CarouselPosition.SHOOT_SLOT_1);
                    } else if (ballInRightSlot) {
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setPosition(CarouselPosition.SHOOT_SLOT_0);
                    }
                    break;
            }
        }
    }

    public ArtifactState getTargetArtifactState() {
        return targetArtifactState;
    }

    public boolean shootSlotIsTarget() {
        ArtifactState shootState = getArtifactState(ColorSensorPosition.SHOOT);

        if (targetArtifactState != ArtifactState.ANY) {
            return shootState == targetArtifactState;
        } else {
            // ANY = any non-empty artifact. If you only care about GREEN/PURPLE, this is fine.
            return (shootState == ArtifactState.GREEN) || (shootState == ArtifactState.PURPLE);
        }
    }
}
