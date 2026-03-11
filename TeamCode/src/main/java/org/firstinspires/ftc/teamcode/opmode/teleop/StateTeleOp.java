package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.commands.NextShootCommand;
import org.firstinspires.ftc.teamcode.commands.PreviousShootCommand;
import org.firstinspires.ftc.teamcode.commands.ShootAllArtifactsCommand;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@Configurable
@TeleOp(name = "TeleOp", group = "teleop")
public class StateTeleOp extends OpMode {
    protected StateRobot robot;

    private final CommandScheduler commandScheduler = new CommandScheduler();

    private final ElapsedTime delta = new ElapsedTime();
    private int velocity = Constants.shootCloseVelocity;
    private double angle = Constants.shootCloseAngle;

    private boolean carouselButtonsLock = false;
    private boolean shootButtonLock = false;
    private CarouselPosition firstShot = CarouselPosition.UNSET;
    private int shotCount = 0;


    private final PIDControllerHeading hPID = new PIDControllerHeading(Constants.getAngularPIDConstants(), Constants.cameraAngleOffset, Constants.getPIDTolerance().h, Constants.blindRotateSpeed);

    private Motif motif = Motif.NONE;

    public static boolean stateShootCommandUpdating = false;
    public static boolean positionUpdating = true;
    public static boolean turretUpdating = true;
    public static boolean intakeUpdating = true;
    public static boolean rotateToGoalUpdating = true;
    public static boolean shootingCommandUpdating = true;
    public static boolean carouselMoveUpdating = true;

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;
        AllianceSide side = (AllianceSide) blackboard.getOrDefault(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        robot = StateRobot.build(hardwareMap);
        robot.setAllianceSide(side);

        telemetry.addLine("G1 Left Stick → Drive & Strafe");
        telemetry.addLine("G1 Right Stick X → Rotate");
        telemetry.addLine("G1 Back Button → Reset Position");
        telemetry.addLine();
        telemetry.addLine("G2 Left Trigger → Reverse Intake");
        telemetry.addLine("G2 Right Trigger → Intake");
        telemetry.addLine("G2 DPAD L & R → Rotate Carousel");
        telemetry.addLine();
        telemetry.addLine("G2 A → Select Close Shoot Preset");
        telemetry.addLine("G2 B → Select Far Shoot Preset");
        telemetry.addLine("G2 Left Stick Y → Adjust Velocity");
        telemetry.addLine("G2 Left Stick X → Adjust Angle");
        telemetry.addLine();
        telemetry.addLine("G2 Right Bumper → FIRE");
        telemetry.addLine("G2 Left Bumper → Fire 3 In Motif");
        telemetry.addLine("G2 Back + Start → Cancel Firing");
    }

    @Override
    public void loop() {

        if (positionUpdating) {
            if (gamepad1.back) {
                robot.getIMU().resetYaw();
            }
        }

        if (turretUpdating) {
            velocity += (int) (-gamepad2.left_stick_y * delta.seconds() * 150);
            angle += -gamepad2.right_stick_y * delta.seconds() * 3;
            delta.reset();

            if (gamepad2.a) {
                angle = Constants.shootCloseAngle;
                velocity = Constants.shootCloseVelocity;
            }
            if (gamepad2.b) {
                angle = Constants.shootFarAngle;
                velocity = Constants.shootFarVelocity;
            }
            if (gamepad2.x) {
                velocity = 0;
            }

            velocity = Range.clip(velocity, 0, 1500);
            angle = Range.clip(angle, 25, 40);
            robot.getShooterSystem().setFlywheelVelocity(velocity);
            robot.getShooterSystem().setHoodAngle(angle);
            telemetry.addData("Velocity (Expected/Actual)", "%d/%.2f", velocity, robot.getShooterSystem().getFlywheelVelocity());
            telemetry.addData("Angle", "%.2f", angle);
            telemetry.addData("Lifter Position", robot.getShooterSystem().getArtifactLift().getLiftPosition());
            telemetry.addData("Carousel Position", robot.getShooterSystem().getCarouselPosition().name());
        }

        if (intakeUpdating) {
            robot.getShooterSystem().setIntakePower(Range.clip(gamepad2.right_trigger - gamepad2.left_trigger, -1, 1));
        }

        if (rotateToGoalUpdating) {
            if (gamepad1.right_bumper) {
                if (robot.getLimelight().isDetectingGoal(robot.getAllianceSide())) {
                    double currentAngleError = robot.getLimelight().getAngleFromGoal();
                    if (!hPID.atTarget(currentAngleError)) {
                        robot.getDrivetrain().control(0.0, 0.0, hPID.calculate(currentAngleError));
                    }
                } else {
                    robot.getDrivetrain().control(0, 0, gamepad1.right_stick_x);
                }
            } else {
                double speedFactor = 1 - Range.clip(gamepad1.right_trigger + gamepad1.left_trigger, 0, 0.5);
                robot.getDrivetrain().fcControl(-gamepad1.left_stick_y * speedFactor, gamepad1.left_stick_x * speedFactor, gamepad1.right_stick_x * speedFactor, robot.getIMU().getRobotYawPitchRollAngles().getYaw());
            }
        }

        if (stateShootCommandUpdating) {
            //motif override for testing
            if (gamepad1.dpad_left) {
                motif = Motif.GPP;
            }
            else if (gamepad1.dpad_down) {
                motif = Motif.PGP;
            }
            else if (gamepad1.dpad_right) {
                motif = Motif.PPG;
            }
            //motif from apriltag
            if (motif == Motif.NONE) {
                motif = robot.getLimelight().getMotif();
            }
            telemetry.addData("Motif", motif.toString());
            if (gamepad2.left_bumper) {
                if (!shootButtonLock) {
                    if (commandScheduler.isCompleted()) {
                        commandScheduler.add(new ShootAllArtifactsCommand(robot.getShooterSystem(), motif));
                        shotCount = 3;
                    }
                }
                shootButtonLock = true;
            }
            else {
                shootButtonLock = false;
            }
        }

        if (shootingCommandUpdating) {
            if (gamepad2.right_bumper) {
                if (!shootButtonLock) {
                    if (commandScheduler.isCompleted()) {
                        firstShot = robot.getShooterSystem().getCarouselPosition();
                    }

                    if (firstShot == CarouselPosition.INTAKE_SLOT_0 && shotCount <= 2) {
                        commandScheduler.add(new NextShootCommand(robot.getShooterSystem()));
                        shotCount++;
                    } else if (firstShot == CarouselPosition.INTAKE_SLOT_1 && shotCount <= 1) {
                        commandScheduler.add(new PreviousShootCommand(robot.getShooterSystem()));
                        shotCount++;
                    } else if (firstShot == CarouselPosition.INTAKE_SLOT_2 && shotCount <= 2) {
                        commandScheduler.add(new PreviousShootCommand(robot.getShooterSystem()));
                        shotCount++;
                    }
                }
                shootButtonLock = true;
            }
            else {
                shootButtonLock = false;
            }
        }

        if (gamepad2.back && gamepad2.start) {
            commandScheduler.emptyAll();
            robot.getShooterSystem().getArtifactLift().setLiftDownNoLimit();
        }

        commandScheduler.loop();
        if (!commandScheduler.isCompleted()) {
            telemetry.addLine("Shooting!!!");
            telemetry.addData("Shots Queued", shotCount);
        }
        telemetry.addLine(commandScheduler.getTelemetry());


        if (commandScheduler.isCompleted()) { //these things only run while the robot is not shooting
            if (shotCount != 0) {
                robot.getShooterSystem().getCarouselSystem().goToNextIntakePosition();
            }
            shotCount = 0;

            if (carouselMoveUpdating) {
                if (gamepad2.dpad_left) {
                    if (!carouselButtonsLock) {
                        robot.getShooterSystem().getCarouselSystem().goToPreviousIntakePosition();
                    }
                    carouselButtonsLock = true;
                }
                else if (gamepad2.dpad_right) {
                    if (!carouselButtonsLock) {
                        robot.getShooterSystem().getCarouselSystem().goToNextIntakePosition();
                    }
                    carouselButtonsLock = true;
                }
                else {
                    carouselButtonsLock = false;
                }
            }
        }

        telemetry.update();

    }


}
