package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.DrivetrainMode;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class MovePIDEncoderCommand extends Command {
    private double yPosition = 0;
    private double yTarget;
    double speed;
    private Drivetrain drivetrain;
    PIDControllerSpeedLimit yPID;
    final double INCHES_TO_ENCODER = 41.8013539662;

    public String dataKey = "MovePIDEncoderCommand";



    /**
     * This command is encoder-based and only moves on the robot-centric y-axis
     * @param targetY amount Y to move
     * @param speed Maximum PID speed
     * @param drivetrain
     */
    public MovePIDEncoderCommand(double targetY, double speed, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.yTarget = targetY*INCHES_TO_ENCODER;
        this.speed = speed;
        yPID = new PIDControllerSpeedLimit(Constants.getLinearPIDConstants(), this.yTarget, Constants.getPIDTolerance().y, speed);
    }
    @Override
    public void init(){
        if (drivetrain.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            drivetrain.setMode(DrivetrainMode.PID);
        }
    }

    @Override
    public void loop() {
         yPosition = drivetrain.encoderPosition();
         double yCalc = yPID.calculate(yPosition);
         drivetrain.fcControl(yCalc, 0, 0, new Pose2D(0, yPosition, 0));
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();

        string.addData("Encoder Value: ", yPosition);
        string.addData("Field Position in Inches (Relative): ", yPosition/INCHES_TO_ENCODER);

        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return yPID.atTarget(drivetrain.encoderPosition());
    }

}