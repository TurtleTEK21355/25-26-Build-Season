package org.firstinspires.ftc.teamcode.commands.action;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class MovePIDHoldTimeCommand extends MovePIDCommand{
    private ElapsedTime holdTimer = new ElapsedTime();
    private final int holdTime;
    private boolean holdTimerStartLock = false;
    private boolean telemetry;


    public MovePIDHoldTimeCommand(Pose2D target, int holdTime, double speed, Drivetrain drivetrain, boolean telemetry) {
        super(target, speed, drivetrain);
        this.holdTime = holdTime;
        this.telemetry = telemetry;
    }

    @Override
    public void loop() {
        super.loop();
        if (super.isCompleted() && !holdTimerStartLock) {
            holdTimer.reset();
            holdTimerStartLock = true;
        }

        if (telemetry) {
            TelemetryPasser.telemetry.addData("Position: ", drivetrain.getPosition());
            TelemetryPasser.telemetry.addData("at Position", super.isCompleted());
            TelemetryPasser.telemetry.addData("holdTimer", holdTimer.milliseconds());
            TelemetryPasser.telemetry.addData("holdTimerStartLock", holdTimerStartLock);
        }
    }

    @Override
    public boolean isCompleted() {
        if (super.isCompleted() && holdTimer.milliseconds() >= holdTime) {
            drivetrain.control(0, 0, 0);
            return true;
        }
        else {
            return false;
        }
    }

}
