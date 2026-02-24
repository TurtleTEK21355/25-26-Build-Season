package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class CommandTemplate extends Command {
    // class variables

    public CommandTemplate() {
        // set class variables (construct command)
    }

    @Override
    public void init() {
        //init code
    }

    @Override
    public void loop() {
        //loop code
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        //string.addData("label", var);
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        // replace false with completion condition
        return (false);

    }

}