package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="Leave", group = "auto")
public class Leave extends StateAutoOpMode {

    @Override
    public void commands() {
        addCommand(new MovePIDEncoderCommand(24, Constants.linearSpeed, robot.getDrivetrain()));
    }

}