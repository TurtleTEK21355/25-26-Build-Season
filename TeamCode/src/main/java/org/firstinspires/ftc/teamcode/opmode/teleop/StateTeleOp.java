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
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "USE THIS FOR STATE MANUAL TESTING!!!!")
public class StateTeleOp extends OpMode {
    private StateRobot robot;

    private CommandScheduler commandScheduler;

    private SequentialCommand shootCommand;
    private SequentialCommand selectGreenArtifactCommand;
    private SequentialCommand selectPurpleArtifactCommand;
    private SequentialCommand selectEmptyArtifactCommand;
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
        selectEmptyArtifactCommand = new SequentialCommand(
                new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.EMPTY));
        selectNearestArtifactCommand = new SequentialCommand(
                new NearestArtifactCommand(robot.getShooterSystem().getCarouselSystem()));
        rotateToGoal = new SequentialCommand(
                new RotateToGoalCommand(robot));
    }

    @Override
    public void loop() {
        if(!gamepad2.right_bumper) {
            robot.getDrivetrain().fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, robot.getAllianceSide(), robot.getOtosSensor().getPosition());
        } else {
            robot.getDrivetrain().rotateToGoal(robot.getOtosSensor().getPosition(), robot.getAllianceSide(), true);
        }

        robot.getShooterSystem().manualControls(gamepad1.left_trigger, gamepad1.right_trigger, gamepad2.left_trigger, gamepad2.right_trigger);

        if(gamepad1.a) preferredArtifactState = ArtifactState.GREEN;
        else if(gamepad1.x) preferredArtifactState = ArtifactState.PURPLE;
        else if(gamepad1.b) preferredArtifactState = ArtifactState.EMPTY;
        else if(gamepad1.y) preferredArtifactState = ArtifactState.ANY;

        if (gamepad2.left_bumper && !shooting) {
            switch (preferredArtifactState) {
                case ANY:
                    commandScheduler.add(selectNearestArtifactCommand);
                    break;
                case GREEN:
                    commandScheduler.add(selectGreenArtifactCommand);
                    break;
                case PURPLE:
                    commandScheduler.add(selectPurpleArtifactCommand);
                    break;
                case EMPTY:
                    commandScheduler.add(selectEmptyArtifactCommand);
                    break;
            }
            commandScheduler.add(rotateToGoal);
            commandScheduler.add(shootCommand);
            shooting = true;
        }

        commandScheduler.loop();

        if (commandScheduler.isCompleted()) {
            shooting = false;
        }

        telemetry.update();
    }


}
