package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;

public class MovePIDEncoderCommand extends Command {
    Drivetrain drivetrain;
    PIDControllerSpeedLimit yPID;
    private double yPosition = 0;

    public String dataKey = "MovePIDEncoderCommand";



    /**
     * This command is encoder-based and only moves on the robot-centric y-axis
     * @param yTarget amount Y to move
     * @param speed Maximum PID speed
     */
    public MovePIDEncoderCommand(double yTarget, double speed, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        yPID = new PIDControllerSpeedLimit(Constants.getLinearPIDConstants(), yTarget*Constants.inchesToEncoderDrivetrain, Constants.getPIDTolerance().y, speed);
    }

    public MovePIDEncoderCommand(double yStart, double yEnd, double speed, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        yPID = new PIDControllerSpeedLimit(Constants.getLinearPIDConstants(), (yEnd-yStart)*Constants.inchesToEncoderDrivetrain, Constants.getPIDTolerance().y, speed);
    }

    @Override
    public void init() {
        drivetrain.resetEncoderPosition();
    }

    @Override
    public void loop() {
         yPosition = -drivetrain.getEncoderPosition();
         double yCalc = yPID.calculate(yPosition*Constants.inchesToEncoderDrivetrain);
         drivetrain.control(yCalc, 0, 0);
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();

        string.addData("Encoder Value: ", yPosition);
        string.addData("Field Position in Inches (Relative): ", yPosition/Constants.inchesToEncoderDrivetrain);

        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return yPID.atTarget(-drivetrain.getEncoderPosition());
    }

}