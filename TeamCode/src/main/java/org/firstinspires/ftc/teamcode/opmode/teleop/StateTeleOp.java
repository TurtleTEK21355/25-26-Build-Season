package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.commands.NextShootCommand;
import org.firstinspires.ftc.teamcode.commands.PreviousShootCommand;
import org.firstinspires.ftc.teamcode.commands.SelectArtifactCommand;
import org.firstinspires.ftc.teamcode.commands.SetCarouselPositionCommand;
import org.firstinspires.ftc.teamcode.commands.ShootAllArtifactsCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

@TeleOp(name = "TeleOp", group = "teleop")
public class StateTeleOp extends OpMode {
    private StateRobot robot;

    private final CommandScheduler commandScheduler = new CommandScheduler();

    private final ElapsedTime delta = new ElapsedTime();
    private int velocity = Constants.shootCloseVelocity;
    private double angle = Constants.shootCloseAngle;

    private CarouselPosition firstShot = CarouselPosition.UNSET;
    private int shotCount = 0;

    private boolean artifactStateShootEnabled = false;
    private Motif motif = Motif.NONE;

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;
        Pose2D startingPosition = (Pose2D) blackboard.getOrDefault(StateRobot.POSITION_BLACKBOARD_KEY, new Pose2D(0,0, 0));
        AllianceSide side = (AllianceSide) blackboard.getOrDefault(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        robot = StateRobot.build(hardwareMap);
        robot.getOtosSensor().setPosition(startingPosition);
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
        boolean resetPositionButton = gamepad1.back;
        double velocityTweakStick = -gamepad2.left_stick_y;
        double angleTweakStick = -gamepad2.right_stick_y;
        boolean closeShootPresetButton = gamepad2.a;
        boolean farShootPresetButton = gamepad2.b;
        boolean stopFlywheelButton = gamepad2.x;
        double intakePowerAxis = gamepad2.right_trigger - gamepad2.left_trigger;
        boolean rotateToGoalButton = gamepad1.right_bumper;
        double speedFactorAxis = gamepad1.right_trigger + gamepad1.left_trigger;
        double forwardBackwardsStick = -gamepad1.left_stick_y;
        double strafeStick = gamepad1.left_stick_x;
        double rotateStick = gamepad1.right_stick_x;
        boolean gppMotif = gamepad1.dpad_left;
        boolean pgpMotif = gamepad1.dpad_down;
        boolean ppgMotif = gamepad1.dpad_right;
        boolean artifactStateShoot = gamepad2.leftBumperWasPressed();
        boolean normalShoot = gamepad2.rightBumperWasPressed();
        boolean cancelShoot = gamepad2.back && gamepad2.start;
        boolean previousCarouselSlot = gamepad2.dpad_left;
        boolean nextCarouselSlot = gamepad2. dpad_right;


        if (resetPositionButton) {
            robot.getOtosSensor().resetPosition();
            robot.getIMU().resetYaw();
        }
        Pose2D position = robot.getOtosSensor().getPosition();
        robot.getLimelight().updateRobotOrientation(position.h); //gets proper position
        robot.getOtosSensor().setPosition(robot.getLimelight().getCorrectedPositionFromLL(position));
        telemetry.addData("Position", position);
        telemetry.addData("IMU Yaw: ", robot.getIMU().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));


        velocity += (int) (velocityTweakStick*delta.seconds()*150); //inverted bc of gamepad
        angle += angleTweakStick*delta.seconds()*3;
        delta.reset();

        if (closeShootPresetButton) {
            angle = Constants.shootCloseAngle;
            velocity = Constants.shootCloseVelocity;
        }
        if (farShootPresetButton) {
            angle = Constants.shootFarAngle;
            velocity = Constants.shootFarVelocity;
        }
        if (stopFlywheelButton) {
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
        
        robot.getShooterSystem().setIntakePower(intakePowerAxis);

        if (rotateToGoalButton) {
            if (robot.getLimelight().isDetectingGoal(robot.getAllianceSide())) {
                double currentAngleError = robot.getLimelight().getAngleFromGoal();
                PIDControllerSpeedLimit hPID = new PIDControllerHeading(Constants.getAngularPIDConstants(), Constants.cameraAngleOffset, Constants.getPIDTolerance().h, Constants.blindRotateSpeed);
                if (!hPID.atTarget(currentAngleError)) {
                    robot.getDrivetrain().control(0.0, 0.0, hPID.calculate(currentAngleError));
                }
            } else {
                robot.getDrivetrain().control(0,0,rotateStick);
            }
        } else {
            double speedFactor = 1 - Range.clip(speedFactorAxis, 0, 0.5);
            robot.getDrivetrain().fcControl(forwardBackwardsStick * speedFactor, strafeStick * speedFactor, rotateStick * speedFactor, robot.getIMU().getRobotYawPitchRollAngles().getYaw());
        }

        if (artifactStateShootEnabled) {
            //motif override for testing
            if (gppMotif) {
                motif = Motif.GPP;
            }
            else if (pgpMotif) {
                motif = Motif.PGP;
            }
            else if (ppgMotif) {
                motif = Motif.PPG;
            }
            //motif from apriltag
            if (motif == Motif.NONE) {
                motif = robot.getLimelight().getMotif();
            }
            telemetry.addData("Motif", motif.toString());

            if (artifactStateShoot) {
                if (commandScheduler.isCompleted()) {
                    commandScheduler.add(new ShootAllArtifactsCommand(robot.getShooterSystem(), motif));
                    shotCount = 3;
                }
            }
        }

        if (normalShoot) {
            if (commandScheduler.isCompleted()) {
                firstShot = robot.getShooterSystem().getCarouselPosition();
            }

            if (firstShot == CarouselPosition.INTAKE_SLOT_0 && shotCount <= 2) {
                commandScheduler.add(new NextShootCommand(robot.getShooterSystem()));
                shotCount++;
            }
            else if (firstShot == CarouselPosition.INTAKE_SLOT_1 && shotCount <=1) {
                commandScheduler.add(new PreviousShootCommand(robot.getShooterSystem()));
                shotCount++;
            }
            else if (firstShot == CarouselPosition.INTAKE_SLOT_2 && shotCount <= 2){
                commandScheduler.add(new PreviousShootCommand(robot.getShooterSystem()));
                shotCount++;
            }
        }

        if (cancelShoot) {
            commandScheduler.emptyAll();
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

            if (previousCarouselSlot) {
                robot.getShooterSystem().getCarouselSystem().goToPreviousIntakePosition();
            }
            else if (nextCarouselSlot) {
                robot.getShooterSystem().getCarouselSystem().goToNextIntakePosition();
            }
        }


        telemetry.update();
    }


}
