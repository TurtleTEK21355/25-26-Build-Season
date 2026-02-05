package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class MovePIDCommand extends Command {
    Pose2D position;
    Pose2D target;
    double speed;
    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    PIDControllerSpeedLimit yPID;
    PIDControllerSpeedLimit xPID;
    PIDControllerHeading hPID;


    public MovePIDCommand(Pose2D target, double speed, Drivetrain drivetrain, OTOSSensor otosSensor) {
        this.drivetrain = drivetrain;
        this.otosSensor = otosSensor;
        this.target = target;
        this.speed = speed;
        yPID = new PIDControllerSpeedLimit(drivetrain.getPIDConstants(), target.y, drivetrain.getTolerance().y, speed);
        xPID = new PIDControllerSpeedLimit(drivetrain.getPIDConstants(), target.x, drivetrain.getTolerance().x, speed);
        hPID = new PIDControllerHeading(drivetrain.getThetaPIDConstants(), target.h, drivetrain.getTolerance().h, speed);
    }

    @Override
    public void loop() {
        position = otosSensor.getPosition();
        drivetrain.fcControl(yPID.calculate(position.y), xPID.calculate(position.x), hPID.calculate(position.h), 0, position);
        drivetrain.PIDTelemetry(position, target, xPID.atTarget(position.x), yPID.atTarget(position.y), hPID.atTarget(position.h));

        TelemetryPasser.telemetry.addLine()
        .addData("yPID at Position", yPID.atTarget(position.y))
        .addData("xPID at Position", xPID.atTarget(position.x))
        .addData("hPID at Position", hPID.atTarget(position.h))
        .addData("dt pos", otosSensor.getPosition());

    }

    @Override
    public boolean isCompleted() {
        return (yPID.atTarget(position.y) && xPID.atTarget(position.x) && hPID.atTarget(position.h));

    }

}
