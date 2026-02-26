package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.LifterDownCommand;
import org.firstinspires.ftc.teamcode.commands.LifterUpCommand;
import org.firstinspires.ftc.teamcode.commands.SelectArtifactCommand;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "USE THIS FOR STATE MANUAL TESTING!!!!")
public class StateTeleOp extends OpMode {
    private StateRobot robot;
    private CommandScheduler shootCommand;
    private CommandScheduler selectGreenArtifactCommand;
    private CommandScheduler selectPurpleArtifactCommand;
    private CommandScheduler selectEmptyArtifactCommand;


    private boolean shooting = false;

    @Override
    public void init() {
        Pose2D startingPosition = (Pose2D) blackboard.getOrDefault(StateRobot.POSITION_BLACKBOARD_KEY, new Pose2D(0,0, 0));
        AllianceSide side = (AllianceSide) blackboard.get(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY);
        robot = StateRobot.build(hardwareMap);
        robot.getOtosSensor().setPosition(startingPosition);
        robot.setAllianceSide(side);

        shootCommand = new CommandScheduler(
                new LifterUpCommand(robot.getShooterSystem()),
                new LifterDownCommand(robot.getShooterSystem()));
        selectGreenArtifactCommand = new CommandScheduler(
                new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.GREEN));
        selectPurpleArtifactCommand = new CommandScheduler(
                new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.PURPLE));
        selectEmptyArtifactCommand = new CommandScheduler(
                new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.EMPTY));

    }

    @Override
    public void loop() {
        if(!gamepad2.right_bumper) {
            robot.getDrivetrain().fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, robot.getAllianceSide(), robot.getOtosSensor().getPosition());
        } else {
            robot.rotateToGoal(true);
        }
        robot.getShooterSystem().manualControls(gamepad1.left_trigger, gamepad1.right_trigger, gamepad2.left_trigger, gamepad2.right_trigger);
        if (gamepad2.left_bumper && !shooting) {
            shooting = true;
        }
        
        if (shooting) {
            shootCommand.loop();
            if (shootCommand.isCompleted()) {
                shooting = false;
            }
        }

        telemetry.update();
    }


}
