package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import java.util.Timer;
import java.util.TimerTask;

public class Drivetrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private SparkFunOTOS otosSensor;
    private AprilTagCamera aprilTagCamera;
    private double kp;
    private double ki;
    private double kd;
    private double kpTheta;
    private double kiTheta;
    private double kdTheta;
    private Pose2D tolerance = new Pose2D(1, 1, 10);
    private boolean PIDLoopActive = true;


    public Drivetrain(DcMotor frontLeft,DcMotor frontRight, DcMotor backLeft, DcMotor backRight){
        this.frontLeftMotor = frontLeft;
        this.frontRightMotor = frontRight;
        this.backLeftMotor = backLeft;
        this.backRightMotor = backRight;
        this.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void configureDrivetrain(AprilTagCamera aprilTagCamera, OtosSensor otosSensor, double kp, double ki, double kd, double kpTheta, double kiTheta, double kdTheta){
        this.otosSensor = otosSensor.sensor;
        this.aprilTagCamera = aprilTagCamera;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kpTheta = kpTheta;
        this.kiTheta = kiTheta;
        this.kdTheta = kdTheta;
    }

    public void configureDrivetrain(OtosSensor otosSensor) {
        this.otosSensor = otosSensor.sensor;
    }

    public void movePID(double targetX, double targetY, double targetH, double speed, int holdTime){
        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(kp, ki, kd, targetX, tolerance.x, speed);
        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(kp, ki, kd, targetY, tolerance.y, speed);
        PIDControllerSpeedLimit hPID = new PIDControllerSpeedLimit(kp, ki, kd, targetH, tolerance.h, speed);
        PIDLoopActive = true;
        boolean timerLock = false;
        Timer holdTimer = new Timer();
        TimerTask TurnOffPIDLoop = new TimerTask() {
            @Override
            public void run() {
                PIDLoopActive = false;
                holdTimer.cancel();
            }
        };

        while (PIDLoopActive){
            double xPos = otosSensor.getPosition().x;
            double yPos = otosSensor.getPosition().y;
            double hPos = otosSensor.getPosition().h;

            if (xPID.atTarget() && yPID.atTarget() && hPID.atTarget()){
                holdTimer.schedule(TurnOffPIDLoop, holdTime);
                timerLock = true;
            }

            fcControl(new Pose2D(xPID.calculate(xPos), yPID.calculate(yPos), -hPID.calculate(hPos)));

        }

    }

    public void fcControl(Pose2D power) {
        double r = Math.sqrt(Math.pow(power.x, 2) + Math.pow(power.y, 2));
        double theta = Math.atan2(power.y, power.x);

        double correctedTheta = theta - Math.toRadians(otosSensor.getPosition().h);

        double correctedY = r * Math.sin(correctedTheta);
        double correctedX = r * Math.cos(correctedTheta);

        frontRightMotor.setPower(Range.clip(correctedY - correctedX - power.h, -1, 1));
        frontLeftMotor.setPower(Range.clip(correctedY - correctedX + power.h, -1, 1));
        backRightMotor.setPower(Range.clip(correctedY + correctedX - power.h, -1, 1));
        backLeftMotor.setPower(Range.clip(correctedY + correctedX + power.h, -1, 1));
        TelemetryPasser.telemetry.addData("flPower=", frontLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("frPower=", frontRightMotor.getPower());
        TelemetryPasser.telemetry.addData("blPower=", backLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("brPower=", backRightMotor.getPower());

    }
    
    public void control(double y, double x, double h) {
        frontRightMotor.setPower(Range.clip(y - x - h, -1, 1));
        frontLeftMotor.setPower(Range.clip(y - x + h, -1, 1));
        backRightMotor.setPower(Range.clip(y + x - h, -1, 1));
        backLeftMotor.setPower(Range.clip(y + x + h, -1, 1));
        TelemetryPasser.telemetry.addData("flPower=", frontLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("frPower=", frontRightMotor.getPower());
        TelemetryPasser.telemetry.addData("blPower=", backLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("brPower=", backRightMotor.getPower());

    }

}
