package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class RotatePIDCommand extends Command {
    private double position;
    private double target;
    double speed;
    private Drivetrain drivetrain;
    private IMU imu;
    PIDControllerHeading hPID;
    public String dataKey = "RotatePIDCommand";



    public RotatePIDCommand(double target, double speed, Drivetrain drivetrain, IMU imu) {
        this.drivetrain = drivetrain;
        this.imu = imu;
        this.target = target;
        this.speed = speed;
        hPID = new PIDControllerHeading(Constants.getAngularPIDConstants(), target+Constants.cameraAngleOffset, Constants.getPIDTolerance().h, speed);
    }

    @Override
    public void loop() {
        position = imu.getRobotYawPitchRollAngles().getYaw();
        drivetrain.control(0,0, hPID.calculate(position));

    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();

        string.addData("hPID at Position", hPID.atTarget(position));
        string.addData("Robot Heading", position);

        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (hPID.atTarget(position));

    }

}
