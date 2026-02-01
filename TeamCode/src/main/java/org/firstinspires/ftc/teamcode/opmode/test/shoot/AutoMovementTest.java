package org.firstinspires.ftc.teamcode.opmode.test.shoot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.commands.shared.MovePIDCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpModeLinear;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="AutoMovementTest", group="test")
public class AutoMovementTest extends ShootAutoOpModeLinear {
    private final AllianceSide SIDE = AllianceSide.BLUE;
    private final Pose2D STARTING_POSITION = new Pose2D(0, 0, 0);

    @Override
    public void initialize() {
        setAllianceSide(SIDE);
        setStartingPosition(STARTING_POSITION);
        super.initialize();

    }

    @Override
    public void commands() {
        addCommand(new MovePIDCommand(new Pose2D(10, 0, 0), 0.6, drivetrain));
    }
}
