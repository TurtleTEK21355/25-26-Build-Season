package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Drivetrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private double PIDKillX = 0.1;
    private double PIDKIllY = 0.1;
    private double kp = 0.9;
    private double ki = 0.0;
    private double kd = 0.0;

    public Drivetrain(DcMotor frontLeft,DcMotor frontRight, DcMotor backLeft, DcMotor backRight){
        this.frontLeftMotor = frontLeft;
        this.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.frontRightMotor = frontRight;
        this.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.backLeftMotor = backLeft;
        this.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backRightMotor = backRight;
        this.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void movePID(double targetX, double targetY, double speed, SparkFunOTOS otosSensor){
        double prevErrorX = 0, prevErrorY = 0;
        double integralX = 0, integralY = 0;
        boolean PIDLoopActive = true;

        while (PIDLoopActive){
            double errorX = targetX - otosSensor.getPosition().x;
            double errorY = targetY - otosSensor.getPosition().y;
            if (Math.abs(errorX) < PIDKillX && Math.abs(errorY) < PIDKIllY){
                PIDLoopActive = false;
            }

            integralX = integralX + errorX;
            integralY = integralY + errorY;

            double derivativeX = errorX - prevErrorX;
            double derivativeY = errorY - prevErrorY;

            double powerX = Math.min((kp * errorY) + (ki * integralX) + (kd * derivativeX), Math.abs(speed));
            double powerY =
        }

    }

}
