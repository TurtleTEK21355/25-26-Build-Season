package org.firstinspires.ftc.teamcode.opmode.test.odometrymovement;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;

@Autonomous(name="Auto Odometry Test XYRot", group="test")
public class AutoOdometryTestXYRot extends StateAutoOpMode {
    double startingHeading = 0;

    final double SPEED = 0.5;

    @Override
    public void initialize() {
        setStartingHeading(startingHeading);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(24, 24, 90), 1000, SPEED, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(0, 0, 0), 1000, SPEED, robot.getDrivetrain(), robot.getOtosSensor()));
    }
}

