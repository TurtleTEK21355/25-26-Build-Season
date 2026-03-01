package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Hood;

public class SetHoodCommand extends Command {
    Hood hood;
    public String dataKey = "SetHoodCommand";

    private double targetAngle; //not really an angle right now because the conversion from angle to hood doesnt do anything

    public SetHoodCommand(double targetAngle, Hood hood) {
        this.hood = hood;
        this.targetAngle = targetAngle;
    }

    @Override
    public void init() {
        hood.setToAngle(targetAngle);

    }

    @Override
    public void loop() {

    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        //string.addData("label", var);
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (true);

    }

}