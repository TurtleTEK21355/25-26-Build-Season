package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.DrivetrainMode;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;

public class MoveViaEncoderCommand extends Command {
    Drivetrain drivetrain;
    int position;
    double speed;

    public MoveViaEncoderCommand(Drivetrain drivetrain, int position, double speed) {
        this.drivetrain = drivetrain;
        this.position = position;
        this.speed = speed;
    }

    @Override
    public void init() {
        drivetrain.setMode(DrivetrainMode.TARGET);
        drivetrain.setTarget(position, speed);
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        //string.addData("label", var);
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (drivetrain.atPosition());
    }

}