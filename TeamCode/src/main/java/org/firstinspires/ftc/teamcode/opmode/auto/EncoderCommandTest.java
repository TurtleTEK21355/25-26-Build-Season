package org.firstinspires.ftc.teamcode.opmode.auto;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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

    public static double SPEED = 0.5;

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingPosition(startingPosition);
        super.initialize();
    }

    @Override
    public void commands() {
        int yForwardAmount = 24;
        int rotate90DegreesCounterClockwise = 90;
        addCommand(new MovePIDEncoderCommand(yForwardAmount, SPEED, robot.getDrivetrain()));
        addCommand(new RotatePIDCommand(rotate90DegreesCounterClockwise, SPEED, robot.getDrivetrain(), robot.getIMU()));
        addCommand(new MovePIDEncoderCommand(yForwardAmount, SPEED, robot.getDrivetrain()));
        addCommand(new RotatePIDCommand(rotate90DegreesCounterClockwise, SPEED, robot.getDrivetrain(), robot.getIMU()));
        addCommand(new MovePIDEncoderCommand(yForwardAmount, SPEED, robot.getDrivetrain()));
        addCommand(new RotatePIDCommand(rotate90DegreesCounterClockwise, SPEED, robot.getDrivetrain(), robot.getIMU()));
        addCommand(new MovePIDEncoderCommand(yForwardAmount, SPEED, robot.getDrivetrain()));
        addCommand(new RotatePIDCommand(rotate90DegreesCounterClockwise, SPEED, robot.getDrivetrain(), robot.getIMU()));
    }
}
