package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.DrivetrainMode;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;

public class MoveViaEncoderCommand extends Command {
    Drivetrain drivetrain;
    int position;
    double speed;
    final double INCHES_TO_ENCODER = -41.8013539662;

    public MoveViaEncoderCommand(Drivetrain drivetrain, int position, double speed) {
        this.drivetrain = drivetrain;
        this.position = (int)Math.floor(position*INCHES_TO_ENCODER);
        this.speed = speed;
    }

    @Override
    public void init() {
        drivetrain.setTarget(position);
    }
    @Override
    public void loop(){
        drivetrain.setNonTargetMotors(speed);
        drivetrain.powerTelemetry();
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