package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.DrivetrainMode;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class MovePIDCommand extends Command {
    private Pose2D position;
    double speed;
    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    PIDControllerSpeedLimit yPID;
    PIDControllerSpeedLimit xPID;
    PIDControllerHeading hPID;

    public String dataKey = "MovePIDCommand";


    /**
     * Regular general-purpose move PID
     * @param target Target position for movement
     * @param speed Maximum PID speed
     * @param drivetrain
     * @param otosSensor
     */
    public MovePIDCommand(Pose2D target, double speed, Drivetrain drivetrain, OTOSSensor otosSensor) {
        this.drivetrain = drivetrain;
        this.otosSensor = otosSensor;
        this.speed = speed;
        yPID = new PIDControllerSpeedLimit(Constants.getLinearPIDConstants(), target.y, Constants.getPIDTolerance().y, speed);
        xPID = new PIDControllerSpeedLimit(Constants.getLinearPIDConstants(), target.x, Constants.getPIDTolerance().x, speed);
        hPID = new PIDControllerHeading(Constants.getAngularPIDConstants(), target.h, Constants.getPIDTolerance().h, speed);
    }

    @Override
    public void loop() {
        position = otosSensor.getPosition();
        double xCalc = xPID.calculate(position.x);
        double hCalc = hPID.calculate(position.h);
        double yCalc = yPID.calculate(position.y);
        drivetrain.fcControl(yCalc, xCalc, hCalc, position);

    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();

        string.addData("yPID at Position ", yPID.atTarget(position.y));
        string.addData("xPID at Position ", xPID.atTarget(position.x));
        string.addData("hPID at Position ", hPID.atTarget(position.h));
        string.addData("Robot Position ", position);

        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (yPID.atTarget(position.y) && xPID.atTarget(position.x) && hPID.atTarget(position.h));
    }

}