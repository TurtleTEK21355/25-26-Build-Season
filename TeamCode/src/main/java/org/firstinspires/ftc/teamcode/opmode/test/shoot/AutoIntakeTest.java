package org.firstinspires.ftc.teamcode.opmode.test.shoot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;

@Disabled
@Autonomous(name="Auto Intake Test", group = "test")
public class AutoIntakeTest extends ShootAutoOpMode {
    private final AllianceSide SIDE = AllianceSide.BLUE;

    @Override
    public void initialize() {
        setAllianceSide(SIDE);
        setStartingPosition(0,0,0);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-30, 12, 90),1500, speed, drivetrain));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-54, 12, 90),1500, 0.2, drivetrain));
        addCommand(new StopIntakeCommand(shooterSystem));

    }

}
