package org.firstinspires.ftc.teamcode.opmode.shoot.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;

@Autonomous(name="Auto Back Blue", group="Autonomous")
public class AutoBackBlue extends ShootAutoOpMode {
    private final AllianceSide SIDE = AllianceSide.BLUE;
    private final Pose2D STARTING_POSITION = new Pose2D(-15, -61, 0);

    @Override
    public void initialize() {
        setAllianceSide(SIDE);
        setStartingPosition(STARTING_POSITION);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-10, 0, 0),1500, speed, drivetrain));

    }

}
