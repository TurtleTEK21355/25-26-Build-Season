package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;

public class MovePIDEncoderCommand extends Command {
    Drivetrain drivetrain;
    PIDControllerSpeedLimit yPID;
    private double yPosition;

    public String dataKey = "MovePIDEncoderCommand";



    /**
     * This command is encoder-based and only moves on the robot-centric y-axis
     * @param yTargetInches amount Y to move
     * @param speed Maximum PID speed
     */
    public MovePIDEncoderCommand(double yTargetInches, double speed, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        yPID = new PIDControllerSpeedLimit(Constants.getEncoderLinearPIDConstants(), yTargetInches*Constants.inchesToEncoderDrivetrain, Constants.encoderPIDTolerance, speed);
    }

    public MovePIDEncoderCommand(double yStartInches, double yEndInches, double speed, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        yPID = new PIDControllerSpeedLimit(Constants.getEncoderLinearPIDConstants(), (yEndInches-yStartInches)*Constants.inchesToEncoderDrivetrain, Constants.encoderPIDTolerance, speed);
    }

    @Override
    public void init() {
        drivetrain.resetEncoderPosition();
    }

    @Override
    public void loop() {
         yPosition = drivetrain.getEncoderPosition();
         double yCalc = yPID.calculate(yPosition);
         drivetrain.control(yCalc, 0, 0);
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();

        string.addData("yCalc: ", yPID.calculate(yPosition));
        string.addData("Encoder Value: ", yPosition);
        string.addData("Field Position in Inches (Relative): ", yPosition/Constants.inchesToEncoderDrivetrain);

        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        if (yPID.atTarget(drivetrain.getEncoderPosition())) {
            drivetrain.control(0,0,0);
            return true;
        }
        return false;
    }

}