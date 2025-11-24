package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MovePIDHoldTimeCommand extends MovePIDCommand{
    ElapsedTime holdTimer = new ElapsedTime();
    private final double holdTime;

    public MovePIDHoldTimeCommand(Pose2D target, double holdTime, double speed, Drivetrain drivetrain) {
        super(target, speed, drivetrain);
        this.holdTime = holdTime;
    }

    @Override
    public void loop() {
        super.loop();
        if (super.isCompleted()) {
            holdTimer.startTime();
        }
    }

    @Override
    public boolean isCompleted() {
        return (super.isCompleted() && (holdTimer.milliseconds() >= holdTime));

    }

}
