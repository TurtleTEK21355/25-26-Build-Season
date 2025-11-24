package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Drivetrain;

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
        TelemetryPasser.telemetry.addData("holdTime", holdTime - holdTimer.milliseconds());
    }

    @Override
    public boolean isCompleted() {
        return (holdTimer.milliseconds() >= holdTime);

    }

}
