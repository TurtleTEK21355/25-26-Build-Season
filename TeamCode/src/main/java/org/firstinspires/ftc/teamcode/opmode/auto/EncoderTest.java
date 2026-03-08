package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.commands.MoveViaEncoderCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="Encoder Test", group = "auto")
public class EncoderTest extends StateAutoOpMode {
    Pose2D startingPosition = new Pose2D(0,0,0);
    AllianceSide side = AllianceSide.BLUE;

    final double SPEED = 0.5;

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingPosition(startingPosition);
        super.initialize();
    }

    @Override
    public void commands() {
//        addCommand(new MoveViaEncoderCommand(robot.getDrivetrain(), -60, SPEED));
        addCommand(new MovePIDCommand(-60, SPEED, robot.getDrivetrain()));
    }

}
