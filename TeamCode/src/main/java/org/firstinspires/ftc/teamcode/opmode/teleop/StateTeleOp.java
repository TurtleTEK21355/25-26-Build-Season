package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.commands.LifterDownCommand;
import org.firstinspires.ftc.teamcode.commands.LifterUpCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.commands.NearestArtifactCommand;
import org.firstinspires.ftc.teamcode.commands.RotateToGoalCommand;
import org.firstinspires.ftc.teamcode.commands.SelectArtifactCommand;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.ShootAllInOrderCommand;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.math.ShootMath;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "USE THIS FOR STATE MANUAL TESTING!!!!")
public class StateTeleOp extends OpMode {
    private StateRobot robot;

    private CommandScheduler commandScheduler = new CommandScheduler();

    private SequentialCommand shootCommand;
    private SequentialCommand selectGreenArtifactCommand;
    private SequentialCommand selectPurpleArtifactCommand;
    private SequentialCommand selectNearestArtifactCommand;
    private SequentialCommand rotateToGoal;

    private boolean shooting = false;
    private ArtifactState preferredArtifactState = ArtifactState.ANY;

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
                new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.GREEN));
        selectPurpleArtifactCommand = new SequentialCommand(
                new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.PURPLE));
        selectNearestArtifactCommand = new SequentialCommand(
                new NearestArtifactCommand(robot.getShooterSystem().getCarouselSystem()));
        rotateToGoal = new SequentialCommand(
                new RotateToGoalCommand(robot));
    }

    @Override
    public void loop() {
        robot.correctPositionFromLL();

        robot.getDrivetrain().fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, robot.getAllianceSide(), robot.getOtosSensor().getPosition());

        ShootMath.ShootResults shootResults = ShootMath.velocityHood(robot.getOtosSensor().getRangeFromPosition(robot.getAllianceSide()));
        robot.getShooterSystem().setFlywheelVelocity(shootResults.velocity);
        robot.getShooterSystem().setHoodAngle(shootResults.theta);
        robot.getShooterSystem().setIntakePower(gamepad2.left_trigger);

             if(gamepad1.a) preferredArtifactState = ArtifactState.GREEN;
        else if(gamepad1.x) preferredArtifactState = ArtifactState.PURPLE;
        else if(gamepad1.y) preferredArtifactState = ArtifactState.ANY;

        if (gamepad2.right_bumper && !shooting) {
            commandScheduler.add(rotateToGoal);
            if (preferredArtifactState != ArtifactState.ANY) {
                switch (preferredArtifactState) {
                    case GREEN:  commandScheduler.add(selectGreenArtifactCommand);  break;
                    case PURPLE: commandScheduler.add(selectPurpleArtifactCommand); break;
                }
                commandScheduler.add(shootCommand);
            } else commandScheduler.add(new ShootAllInOrderCommand(robot.getShooterSystem()));
            shooting = true;
        }

        commandScheduler.loop();
        TelemetryPasser.telemetry.addLine(commandScheduler.getTelemetry());

        if (commandScheduler.isCompleted()) {
            shooting = false;
        }

        telemetry.update();
    }


}
