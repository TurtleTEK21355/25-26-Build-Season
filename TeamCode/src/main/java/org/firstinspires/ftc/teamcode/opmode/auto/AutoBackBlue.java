package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;

@Autonomous(name="Auto Back Blue", group="Autonomous")
public class AutoBackBlue extends ShootAutoOpMode {
    private final AllianceSide SIDE = AllianceSide.BLUE;

    @Override
    protected void setup() {
        setAllianceSide(SIDE);

    }

    @Override
    protected void commands() {
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-10, 0, 0),1500, speed, drivetrain));

    }

}
