package org.firstinspires.ftc.teamcode.commands;

import android.net.sip.SipSession;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;

public class RotateToGoalCommand extends Command {
    StateRobot robot;
    Pose2D target;
    PIDControllerHeading hPID;
    private final double ROTATION_PID_SPEED = 0.75;
    public String dataKey = "RotateToGoalCommand";

    public RotateToGoalCommand(StateRobot robot) {
        this.robot = robot;
        target = robot.getAllianceSide().getGoalPosition();
    }

    @Override
    public void init() {
        hPID = new PIDControllerHeading(robot.getDrivetrain().getThetaPIDConstants(), robot.getOtosSensor().getPosition().positionsToFCAngle(target)*(90.0/Math.PI), robot.getDrivetrain().getTolerance().h, ROTATION_PID_SPEED);

    }

    @Override
    public void loop() {
        Pose2D position = robot.getOtosSensor().getPosition();
        robot.getDrivetrain().fcControl(0, 0, hPID.calculate(position.h), position);
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();

        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (hPID.atTarget(robot.getOtosSensor().getPosition().h));
    }

}