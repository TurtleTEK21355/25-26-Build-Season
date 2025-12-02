package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class MovePIDCommand extends Command {
    Pose2D position;
    Pose2D target;
    double speed;
    Drivetrain drivetrain;
    PIDControllerSpeedLimit yPID;
    PIDControllerSpeedLimit xPID;
    PIDControllerHeading hPID;


    public MovePIDCommand(Pose2D target, double speed, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.target = target;
        this.speed = speed;
        yPID = new PIDControllerSpeedLimit(drivetrain.getPIDConstants(), target.y, drivetrain.getTolerance().y, speed);
        xPID = new PIDControllerSpeedLimit(drivetrain.getPIDConstants(), target.x, drivetrain.getTolerance().x, speed);
        hPID = new PIDControllerHeading(drivetrain.getThetaPIDConstants(), target.h, drivetrain.getTolerance().h, speed);
        position = drivetrain.getPosition();
    }

    @Override
    public void loop() {
        position = drivetrain.getPosition();
        position.x += drivetrain.getOffsetX();
        position.y += drivetrain.getOffsetY();
        drivetrain.fcControl(yPID.calculate(position.y), xPID.calculate(position.x), hPID.calculate(position.h));
        drivetrain.PIDTelemetry(position, target, xPID.atTarget(position.x), yPID.atTarget(position.y), hPID.atTarget(position.h));
    }

    @Override
    public boolean isCompleted() {
        return (yPID.atTarget(position.y) && xPID.atTarget(position.x) && hPID.atTarget(position.h));
    }

}
