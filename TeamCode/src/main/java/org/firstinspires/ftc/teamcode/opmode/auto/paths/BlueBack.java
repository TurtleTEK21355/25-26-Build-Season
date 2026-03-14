package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="Blue Back", group = "auto")
public class BlueBack extends StateAutoOpMode {
    AllianceSide side = AllianceSide.BLUE;


    @Override
    public void initialize() {
        setAllianceSide(side);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new MovePIDEncoderCommand(24, Constants.linearSpeed, robot.getDrivetrain()));
    }
}
