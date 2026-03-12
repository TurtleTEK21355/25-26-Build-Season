package org.firstinspires.ftc.teamcode.opmode.auto;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.commands.RotatePIDCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Configurable
@Autonomous(name = "Encoder Command Test", group = "test")
public class EncoderCommandTest extends StateAutoOpMode {
    Pose2D startingPosition = new Pose2D(0,0,0); // Replace 0s with starting position
    AllianceSide side = AllianceSide.BLUE; // Replace BLUE with RED if required

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingHeading(startingPosition);
        super.initialize();
    }

    @Override
    public void commands() {
        int yForwardAmount = 24;
        addCommand(new MovePIDEncoderCommand(yForwardAmount, Constants.linearSpeed, robot.getDrivetrain()));
        addCommand(new RotatePIDCommand(90, Constants.angularSpeed, robot.getDrivetrain(), robot.getIMU()));
        addCommand(new MovePIDEncoderCommand(yForwardAmount, Constants.linearSpeed, robot.getDrivetrain()));
        addCommand(new RotatePIDCommand(180, Constants.angularSpeed, robot.getDrivetrain(), robot.getIMU()));
        addCommand(new MovePIDEncoderCommand(yForwardAmount, Constants.linearSpeed, robot.getDrivetrain()));
        addCommand(new RotatePIDCommand(-90, Constants.angularSpeed, robot.getDrivetrain(), robot.getIMU()));
        addCommand(new MovePIDEncoderCommand(yForwardAmount, Constants.linearSpeed, robot.getDrivetrain()));
        addCommand(new RotatePIDCommand(0, Constants.angularSpeed, robot.getDrivetrain(), robot.getIMU()));
    }
}
