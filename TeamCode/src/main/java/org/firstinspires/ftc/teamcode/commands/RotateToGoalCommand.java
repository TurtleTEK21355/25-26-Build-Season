package org.firstinspires.ftc.teamcode.commands;

import android.net.sip.SipSession;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class RotateToGoalCommand extends Command {
    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    Pose2D target;
    PIDControllerHeading hPID;

    public String dataKey = "RotateToGoalCommand";

    private final double ROTATION_PID_SPEED = 0.75;

    public RotateToGoalCommand(StateRobot robot) {
        this.drivetrain = robot.getDrivetrain();
        this.otosSensor = robot.getOtosSensor();
        target = robot.getAllianceSide().getGoalPosition();
    }

    @Override
    public void init() {
        hPID = new PIDControllerHeading(Constants.getAngularPIDConstants(), otosSensor.getPosition().positionsToFCAngle(target)*(90.0/Math.PI), Constants.getPIDTolerance().h, ROTATION_PID_SPEED);
    }

    @Override
    public void loop() {
        Pose2D position = otosSensor.getPosition();
        drivetrain.fcControl(0, 0, hPID.calculate(position.h), position);

    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        string.addData("distance to goal", otosSensor.getPosition().positionsToFCAngle(target)*(90.0/Math.PI) - otosSensor.getPosition().h);
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (hPID.atTarget(otosSensor.getPosition().h));
    }

}