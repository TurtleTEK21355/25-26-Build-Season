package org.firstinspires.ftc.teamcode.opmode.test.odometrymovement;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;

@Autonomous(name="Auto Odometry Test XY", group="test")
public class AutoOdometryTestXY extends StateAutoOpMode {
    Pose2D startingPosition = new Pose2D(0,0,0);

    final double SPEED = 0.5;

    @Override
    public void initialize() {
        setStartingPosition(startingPosition);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(24, 24, 0), 1000, SPEED, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(0, 0, 0), 1000, SPEED, robot.getDrivetrain(), robot.getOtosSensor()));
    }
}
