package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.commands.PreviousShootCommand;
import org.firstinspires.ftc.teamcode.commands.RotatePIDCommand;
import org.firstinspires.ftc.teamcode.commands.SetCarouselPositionCommand;
import org.firstinspires.ftc.teamcode.commands.SetFlywheelVelocityCommand;
import org.firstinspires.ftc.teamcode.commands.SetHoodAngleCommand;
import org.firstinspires.ftc.teamcode.commands.Shoot3Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;

@Configurable
@Autonomous(name="Red Front", group = "auto")
public class RedFront extends StateAutoOpMode {
    AllianceSide side = AllianceSide.RED;

    public static double shootMove = -60;
    public static double shootAngle = 0;
    public static double parkAngle = -90;
    public static double parkMove = 18;

    public static int startCommand = 0;
    public static int endCommand = 100;

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

                new PreviousShootCommand(robot.getShooterSystem()),
                new PreviousShootCommand(robot.getShooterSystem()),
                new PreviousShootCommand(robot.getShooterSystem()),

                new RotatePIDCommand(parkAngle, Constants.angularSpeed, robot.getDrivetrain(), robot.getIMU()),
                new MovePIDEncoderCommand(parkMove, Constants.linearSpeed, robot.getDrivetrain())
        );
        for (int i = startCommand; i <= endCommand; i++) {
            if (i > commands.size()) {
                break;
            }
            commandScheduler.add(commands.get(i));
        }
    }
}
