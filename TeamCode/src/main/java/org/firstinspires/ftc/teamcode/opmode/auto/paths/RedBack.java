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
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;

@Configurable
@Autonomous(name="Red Back", group = "auto")
public class RedBack extends StateAutoOpMode {
    AllianceSide side = AllianceSide.RED;

    public static double shootMove = 10;
    public static double shootAngle = -20;
    public static double parkAngle = -45;
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
                new SetFlywheelVelocityCommand(robot.getShooterSystem(), Constants.autoShootFarVelocity),
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
