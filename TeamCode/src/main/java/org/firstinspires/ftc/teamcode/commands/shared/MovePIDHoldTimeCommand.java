package org.firstinspires.ftc.teamcode.commands.shared;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.subsystems.shared.actuator.Drivetrain;

public class MovePIDHoldTimeCommand extends MovePIDCommand{
    private ElapsedTime holdTimer = new ElapsedTime();
    private final int holdTime;
    private boolean holdTimerStartLock = false;


    public MovePIDHoldTimeCommand(Pose2D target, int holdTime, double speed, Drivetrain drivetrain) {
        super(target, speed, drivetrain);
        this.holdTime = holdTime;
    }

    @Override
    public void loop() {
        super.loop();
        if (super.isCompleted() && !holdTimerStartLock) {
            holdTimer.reset();
            holdTimerStartLock = true;
        }

        TelemetryPasser.telemetry.addData("at Position", super.isCompleted());
        TelemetryPasser.telemetry.addData("holdTimer", holdTimer.milliseconds());
        TelemetryPasser.telemetry.addData("holdTimerStartLock", holdTimerStartLock);

    }

    @Override
    public boolean isCompleted() {
        return (super.isCompleted() && holdTimer.milliseconds() >= holdTime);

    }

}
