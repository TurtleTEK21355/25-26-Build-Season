package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.commands.PreviousShootCommand;
import org.firstinspires.ftc.teamcode.commands.RotatePIDCommand;
import org.firstinspires.ftc.teamcode.commands.SetCarouselPositionCommand;
import org.firstinspires.ftc.teamcode.commands.SetFlywheelVelocityCommand;
import org.firstinspires.ftc.teamcode.commands.SetHoodAngleCommand;
import org.firstinspires.ftc.teamcode.commands.Shoot3Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;

import java.util.Collection;

@Configurable
@Autonomous(name="Blue Front", group = "auto")
public class BlueFront extends StateAutoOpMode {
    AllianceSide side = AllianceSide.BLUE;

    public static double shootMove = -49;
    public static double shootAngle = 43;
    public static double parkAngle = 135;
    public static double parkMove = 18;

    @Override
    public void initialize() {
        setAllianceSide(side);
        super.initialize();
    }

    @Override
    public void commands() {
        CommandList commands = new CommandList(
                new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem()),
                new SetFlywheelVelocityCommand(robot.getShooterSystem(), Constants.autoShootCloseVelocity),
                new SetHoodAngleCommand(Constants.shootCloseAngle, robot.getShooterSystem()),

                new MovePIDEncoderCommand(shootMove, Constants.linearSpeed, robot.getDrivetrain()),
                new RotatePIDCommand(shootAngle, Constants.angularSpeed, robot.getDrivetrain(), robot.getIMU()),

                new PreviousShootCommand(robot.getShooterSystem()),
                new PreviousShootCommand(robot.getShooterSystem()),
                new PreviousShootCommand(robot.getShooterSystem()),

                new RotatePIDCommand(parkAngle, Constants.angularSpeed, robot.getDrivetrain(), robot.getIMU()),
                new MovePIDEncoderCommand(parkMove, Constants.linearSpeed, robot.getDrivetrain())
        );
        commandScheduler.addAll(commands);

    }
}
