package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;

@Autonomous(name="Auto Back Blue", group="Autonomous")
public class AutoIntakeTest extends ShootAutoOpMode {
    private final AllianceSide SIDE = AllianceSide.BLUE;

    @Override
    protected void setup() {
        setAllianceSide(SIDE);

    }

    @Override
    protected void commands() {
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-30, 12, 90),1500, speed, drivetrain));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-54, 12, 90),1500, 0.2, drivetrain));
        addCommand(new StopIntakeCommand(shooterSystem));

    }

}
