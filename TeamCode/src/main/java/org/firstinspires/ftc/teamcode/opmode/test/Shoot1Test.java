package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpModeLinear;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="Shoot 1 Test", group="Autonomous")
public class Shoot1Test extends ShootAutoOpModeLinear {
    private final AllianceSide SIDE = AllianceSide.BLUE;

    @Override
    public void initialize() {
        setAllianceSide(SIDE);
        setStartingPosition(SHOOT_POSITION);
        super.initialize();
    }

    @Override
    public void commands() {
        //shoot();
    }

}
