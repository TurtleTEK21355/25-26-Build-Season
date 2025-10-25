package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    double shooterSpeed;

    boolean shooterOff;

    boolean noButtonPressed = false;

    ElapsedTime timer;

    //TODO Delete Unused Methods
    public Shooter(DcMotor shooter) {
        this.leftMotor = shooter;
        this.leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        timer = new ElapsedTime();
        shooterOff = true;
    }

    public double getShooterSpeed() {
        return shooterSpeed;
    }

    public void increaseShooterSpeed(double increment) {
        shooterSpeed += increment;
    }

    public void decreaseShooterSpeed(double increment) {
        shooterSpeed -= increment;
    }

    public void runShooter() {
        //Check Current State (Starting up, Full Power, Ball Release, Off)
        if (shooterOff) {
            shooterSpeed = 0;
            leftMotor.setPower(shooterSpeed);
            return;
        }
        if (timer.time() < 1) {
           shooterSpeed =timer.time() * timer.time();
           leftMotor.setPower(shooterSpeed);
        }
        else if (timer.time() < 2) {
            shooterSpeed = 1;
            leftMotor.setPower(shooterSpeed);
        }
        else {
            TelemetryPasser.telemetry.addLine("Usable");
            TelemetryPasser.telemetry.update();
        }
    }

    public void startShooter() {
        if (shooterOff) {
            timer.reset();
            timer.time();
            shooterOff = false;
        }
    }

    public void stopShooter() {
        leftMotor.setPower(0);
        shooterOff = true;

    }

    //    public void twoShooter(DcMotor leftShooter, DcMotor rightShooter) {
//        this.leftMotor = leftShooter;
//        this.rightMotor = rightShooter;
//        this.leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        this.rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
//
//    }
}
