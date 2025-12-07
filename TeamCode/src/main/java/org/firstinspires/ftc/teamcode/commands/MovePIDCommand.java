package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
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
    }

    @Override
    public void loop() {
        position = drivetrain.getPosition();
        drivetrain.fcControl(yPID.calculate(position.y), xPID.calculate(position.x), hPID.calculate(position.h));
        drivetrain.PIDTelemetry(position, target, xPID.atTarget(position.x), yPID.atTarget(position.y), hPID.atTarget(position.h));

        TelemetryPasser.telemetry.addData("yPID at Position", yPID.atTarget(position.y));
        TelemetryPasser.telemetry.addData("xPID at Position", xPID.atTarget(position.x));
        TelemetryPasser.telemetry.addData("hPID at Position", hPID.atTarget(position.h));
        TelemetryPasser.telemetry.addData("dt pos", drivetrain.getPosition());

    }

    @Override
    public boolean isCompleted() {
        return (yPID.atTarget(position.y) && xPID.atTarget(position.x) && hPID.atTarget(position.h));

    }

}
