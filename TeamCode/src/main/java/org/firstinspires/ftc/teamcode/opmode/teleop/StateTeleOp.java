package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.commands.LifterDownCommand;
import org.firstinspires.ftc.teamcode.commands.LifterUpCommand;
import org.firstinspires.ftc.teamcode.commands.NearestArtifactCommand;
import org.firstinspires.ftc.teamcode.commands.RotateToGoalCommand;
import org.firstinspires.ftc.teamcode.commands.SelectArtifactCommand;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.ShootAllInOrderCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.math.ShootMath;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "TeleOp", group = "teleop")
public class StateTeleOp extends OpMode {
    private StateRobot robot;

    private final CommandScheduler commandScheduler = new CommandScheduler();
    private ElapsedTime cooldownTimer = new ElapsedTime();
    private int velocity = 1500;
    private double angle = 25;

    private SequentialCommand shootCommand;
    private SequentialCommand selectGreenArtifactCommand;
    private SequentialCommand selectPurpleArtifactCommand;
    private SequentialCommand selectNearestArtifactCommand;
    private SequentialCommand rotateToGoal;

    private boolean lock = false;
    private int intakeSlot = 0;
    private boolean shooting = false;

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;
        Pose2D startingPosition = (Pose2D) blackboard.getOrDefault(StateRobot.POSITION_BLACKBOARD_KEY, new Pose2D(0,0, 0));
        AllianceSide side = (AllianceSide) blackboard.getOrDefault(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        robot = StateRobot.build(hardwareMap);
        robot.getOtosSensor().setPosition(startingPosition);
        robot.setAllianceSide(side);

        shootCommand = new SequentialCommand(
                new LifterUpCommand(robot.getShooterSystem()),
                new LifterDownCommand(robot.getShooterSystem()));
        selectGreenArtifactCommand = new SequentialCommand(
                new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.GREEN),
                new TimerCommand(600));
        selectPurpleArtifactCommand = new SequentialCommand(
                new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.PURPLE),
                new TimerCommand(600));
        selectNearestArtifactCommand = new SequentialCommand(
                new NearestArtifactCommand(robot.getShooterSystem().getCarouselSystem()));
        rotateToGoal = new SequentialCommand(
                new RotateToGoalCommand(robot));

// Shooting
        telemetry.addLine("🔫 G2 Right Trigger → FIRE ONE");
        telemetry.addLine("⚙️ G1 Right Trigger → Intake Power");

// Artifact Selection
        telemetry.addLine("👅 G2 DPAD L & R → Rotate Carousel");

// Driving
        telemetry.addLine("🕹️ G1 Left Stick → Drive (forward/back + strafe)");
        telemetry.addLine("🔄 G1 Right Stick X → Rotate");

    }

    @Override
    public void loop() {
        if (gamepad1.back) {
            robot.getOtosSensor().resetPosition();
        }
        Pose2D position = robot.getOtosSensor().getPosition();
        robot.getLimelight().updateRobotOrientation(position.h); //gets proper position
        robot.getOtosSensor().setPosition(robot.getLimelight().getCorrectedPositionFromLL(position));

        if (cooldownTimer.milliseconds() > 333) {
            if(gamepad2.b && velocity < 1700) {
                velocity += 10;
                cooldownTimer.reset();
            }
            else if (gamepad2.a && velocity > 0) {
                velocity -= 10;
                cooldownTimer.reset();
            }

            if(gamepad2.y) {
                angle += 1;
                cooldownTimer.reset();
            }
            else if (gamepad2.x) {
                angle -= 1;
                cooldownTimer.reset();
            }
        }
        if (gamepad2.left_bumper) {
            angle = 32;
            velocity = 1200;
        }

        if (gamepad2.right_bumper) {
            angle = 20;
            velocity = 1400;
        }

        robot.getShooterSystem().setFlywheelVelocity(velocity);
        robot.getShooterSystem().setHoodAngle(angle);
        if (gamepad2.right_bumper) {
            robot.getShooterSystem().setIntakePower(-1);
        }
        else {
            robot.getShooterSystem().setIntakePower(gamepad2.left_trigger);
        }

        telemetry.addData("Velocity: ", velocity);
        telemetry.addData("Angle: ", angle);

        //shooting command
        if (gamepad2.right_trigger > 0.1 && !shooting) {
            commandScheduler.add(new ShootAllInOrderCommand(robot.getShooterSystem()));
            shooting = true;

        }
        if (gamepad2.back) {
            commandScheduler.emptyAll();
        }

        commandScheduler.loop();
        if (!commandScheduler.isCompleted()) {
            telemetry.addLine("Shooting!!!");
        }
        telemetry.addLine(commandScheduler.getTelemetry());

        //not shooting
        if (commandScheduler.isCompleted()) {
            shooting = false;

            robot.getDrivetrain().control(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);


            if (gamepad2.dpad_left&&!lock) {
                lock = true;
                intakeSlot--;
            }
            else if (gamepad2.dpad_right&&!lock) {
                lock = true;
                intakeSlot++;
            }
            else if (!gamepad2.dpad_right && !gamepad2.dpad_left){
                lock = false;
            }

            if (intakeSlot < 0) {
                intakeSlot = 0;
            }
            if (intakeSlot > 2) {
                intakeSlot = 2;
            }

            robot.getShooterSystem().setSlotInIntake(intakeSlot);

        }
        telemetry.addData("Position", position);
        telemetry.update();
    }


}
