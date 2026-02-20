package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class MovePIDHoldTimeCommand extends MovePIDCommand{
    private ElapsedTime holdTimer = new ElapsedTime();
    private final int holdTime;
    private boolean holdTimerStartLock = false;


    public MovePIDHoldTimeCommand(Pose2D target, int holdTime, double speed, Drivetrain drivetrain, OTOSSensor otosSensor) {
        super(target, speed, drivetrain, otosSensor);
        this.holdTime = holdTime;
    }

    @Override
    public void loop() {
        super.loop();
        if (super.isCompleted() && !holdTimerStartLock) {
            holdTimer.reset();
            holdTimerStartLock = true;
        }

    }

    @Override
    public boolean isCompleted() {
        return super.isCompleted() && holdTimerStartLock && holdTimer.milliseconds() >= holdTime;
    }

}