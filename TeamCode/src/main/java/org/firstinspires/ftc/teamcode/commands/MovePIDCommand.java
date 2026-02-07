package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

public class MovePIDCommand extends Command {
    Pose2D position;
    Pose2D target;
    double speed;
    private StateRobot robot;
    PIDControllerSpeedLimit yPID;
    PIDControllerSpeedLimit xPID;
    PIDControllerHeading hPID;


    public MovePIDCommand(Pose2D target, double speed, StateRobot robot) {
        this.robot = robot;
        this.target = target;
        this.speed = speed;
        yPID = new PIDControllerSpeedLimit(robot.getPIDConstants(), target.y, robot.getTolerance().y, speed);
        xPID = new PIDControllerSpeedLimit(robot.getPIDConstants(), target.x, robot.getTolerance().x, speed);
        hPID = new PIDControllerHeading(robot.getThetaPIDConstants(), target.h, robot.getTolerance().h, speed);
    }

    @Override
    public void loop() {
        robot.updatePosition();
        robot.drivetrainFCControl(yPID.calculate(robot.getY()), xPID.calculate(robot.getX()), hPID.calculate(robot.getH()));

    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();

        string.addData("yPID at Position ", yPID.atTarget(position.y));
        string.addData("xPID at Position ", xPID.atTarget(position.x));
        string.addData("hPID at Position ", hPID.atTarget(position.h));
        string.addData("Robot Position ", robot.getPosition());

        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (yPID.atTarget(position.y) && xPID.atTarget(position.x) && hPID.atTarget(position.h));

    }

}