package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.commands.RotatePIDCommand;
import org.firstinspires.ftc.teamcode.commands.Shoot3Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

import java.util.Collection;

@Autonomous(name="Blue Front", group = "auto")
public class BlueFront extends StateAutoOpMode {
    AllianceSide side = AllianceSide.BLUE;


    @Override
    public void initialize() {
        setAllianceSide(side);
        super.initialize();
    }

    @Override
    public void commands() {
        CommandList commands = new CommandList(
                new MovePIDEncoderCommand(-49, Constants.linearSpeed, robot.getDrivetrain()),
                new RotatePIDCommand(40, Constants.angularSpeed, robot.getDrivetrain(), robot.getIMU()),
                new Shoot3Command(robot.getShooterSystem())
        );
        commandScheduler.addAll(commands);

    }
}
