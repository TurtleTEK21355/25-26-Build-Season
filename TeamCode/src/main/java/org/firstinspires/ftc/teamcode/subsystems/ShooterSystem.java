package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class ShooterSystem {
    public static final int MAX_RANGE_INCHES = 100;
    public static final int MAX_VELOCITY_RPM = 1500;
    private FlyWheel flyWheel;
    private GateSystem gateSystem;
    private Intake intake;
    private Motif motif;
    private AllianceSide side;
    private final double[] RANGE_TO_VELOCITY = new double[]{1000,1050,1100,1150,1199,1232,1278,1328,1379,1430};
    private final double GRAVITY = 386.09; //Inches per second squared
    private final double HEIGHT = 40; //inches tall + ball diameter
    private final double THETA = 1.13446401; //Ramp Angle in Radians
    private final double REGRESSION_VARIABLE = 5.43557;
    private final double MAX_RPM = 3214;
    private final double TICKS_PER_ROTATION = 28; //ticks per rotation of 5000 series motor
    ElapsedTime velocityChangeTimer = new ElapsedTime();
    double tpsChange = 0;
    private final int FLYWHEEL_VELOCITY_TOLERANCE_TPS = 40;
    private final int GATEWAITTIME = 1250;
    ElapsedTime gateTimer = new ElapsedTime();
    boolean gateWait = false;


    public ShooterSystem(FlyWheel flyWheel, GateSystem gateSystem, Intake intake, AllianceSide side){
        this.flyWheel = flyWheel;
        this.intake = intake;
        this.gateSystem = gateSystem;
        this.side = side;
    }

    public void flywheelSetVelocity(double velocity) {
            flyWheel.setVelocity(velocity);
    }
    public double flywheelGetVelocity() {
        return flyWheel.getVelocity();
    }
    public void intakeSetPower(double power) {
        intake.setPower(power);
    }
    public void openGate() {
        gateSystem.openGate();
    }
    public void closeGate() {
        gateSystem.closeGate();
    }
    public boolean ballReady() {return gateSystem.ballReady();}

    public void teleOpControl(Pose2D position, boolean intakeForward, boolean shoot, double intakeBackward, boolean tweakUp, boolean tweakDown) {
        position.x *= -1; // This is temporary until the odometry is facing the correct direction
        position.y *= -1; // ^
        double range = getDistanceFromGoal(position, side);
        TelemetryPasser.telemetry.addData("Range from Goal:", range);
        double flyWheelTargetSpeed;
        if (range <= 0) {
            flyWheelTargetSpeed = RANGE_TO_VELOCITY[0];
        } else if (range >= MAX_RANGE_INCHES){
            flyWheelTargetSpeed = MAX_VELOCITY_RPM;
        } else {
            int rangeSector = (int) range / RANGE_TO_VELOCITY.length;
            flyWheelTargetSpeed = RANGE_TO_VELOCITY[rangeSector];
        }
        TelemetryPasser.telemetry.addData("Flywheel Calulated Ticks Per second:", flyWheelTargetSpeed);
        TelemetryPasser.telemetry.addData("Flywheel Actual Ticks Per second:", flywheelGetVelocity());


//        if (tweakUp && velocityChangeTimer.milliseconds()>250) {
//            tpsChange += 10;
//        }
//        else if (tweakDown && velocityChangeTimer.milliseconds()>250) {
//            tpsChange -= 10;
//        }
        //        double editedTicksPerSecond = Math.sqrt(flyWheelTargetSpeed) * 3.5;
//        double editedTicksPerSecond = flyWheelTargetSpeed+tpsChange;
//        flywheelSetVelocity(Range.clip(editedTicksPerSecond, -1500, 1500));
//        TelemetryPasser.telemetry.addData("Edited: ", editedTicksPerSecond);
        flywheelSetVelocity(flyWheelTargetSpeed);
        if (shoot) {
            openGate();
            if (!gateWait) {
                gateTimer.reset();
                gateWait = true;
            } else {
                if (gateTimer.milliseconds() > GATEWAITTIME) {
                    intakeSetPower(0.75);
                }
            }
        } else {
            if (intakeForward) {
                intakeSetPower(1);
            }
            else if (intakeBackward > 0.2) {
                intakeSetPower(-0.6);
            } else {
                intakeSetPower(0);
            }
            closeGate();
            gateWait = false;
        }
//        if (shoot && intakeForward && (flywheelGetVelocity() > (editedTicksPerSecond - FLYWHEEL_VELOCITY_TOLERANCE_TPS))) {
//            intakeSetPower(0.5);
//        } else {
//            intakeSetPower(0);
//        }

//        TelemetryPasser.telemetry.addData("ball ready", ballReady());


    }

    public void teleOpControlTest(boolean intakeForward, boolean shoot, double intakeBackward) {
        if (intakeForward) {
            intake.setPower(1);
        } else if(intakeBackward > 0.2) {
            intake.setPower(0.8);
        } else {
            intake.setPower(0);
        }
        if (shoot) {
            flyWheel.setVelocity(1500);
        } else {
            flyWheel.setVelocity(0);
        }
    }

    public void teleOpControlConfigurableVelocity(double velocity, boolean intakeForward, boolean shoot, double intakeBackward, AllianceSide side, Pose2D position) {
        flyWheel.setVelocity(velocity);
        if (intakeForward) {
            intake.setPower(0.8);
        } else if (intakeBackward > 0.1)
        {intake.setPower(-0.7);} else {
            intake.setPower(0);
        }
        if (shoot) {
                gateSystem.openGate();
            if (intakeForward && flywheelGetVelocity() > (velocity- FLYWHEEL_VELOCITY_TOLERANCE_TPS)) {
                intake.setPower(0.8);
            } else {
                intake.setPower(0);
            }
        } else {
            gateSystem.closeGate();}

        TelemetryPasser.telemetry.addData("FlyWheel Velocity in ticks/s", flyWheel.getVelocity());
        TelemetryPasser.telemetry.addData("Range", getDistanceFromGoal(position, side));
        TelemetryPasser.telemetry.addData("shoot", ballReady());
    }

    private double getDistanceFromGoal(Pose2D position, AllianceSide side) {
            return Math.abs(Math.hypot(side.getGoalPosition().x - position.x, side.getGoalPosition().y - position.y));

    }

    private double getTicksPerSecondForRange(double range) {
        return (
            REGRESSION_VARIABLE *
                    Math.sqrt(
                            (-GRAVITY*(Math.pow(range, 2)))
                            /
                    ((2*Math.cos(THETA)) * (HEIGHT-(range*Math.tan(THETA))))
                    )
        );

    }

    public Motif getMotif() {
        return motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

}
