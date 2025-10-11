package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
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

    /**
     * controls the drivetrain to move and rotate to specific points on the field. If the robot is within the tolerance area, it will stop... probably
     * @param targetY the y target
     * @param targetX the x target
     * @param targetH heading(rotation) target
     * @param speed the max speed pid makes it go slower the closer it gets
     * @param holdTime the time to keep it(in milliseconds) at the target for greater accuracy, set to 0 if you want no holdtime
     */
    public void movePID(double targetY, double targetX, double targetH, double speed, int holdTime){
        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(kp, ki, kd, targetY, tolerance.y, speed);
        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(kp, ki, kd, targetX, tolerance.x, speed);
        PIDControllerSpeedLimit hPID = new PIDControllerSpeedLimit(kpTheta, kiTheta, kdTheta, targetH, tolerance.h, speed);

        while (!(yPID.atTarget() && xPID.atTarget() && hPID.atTarget())){
            double yPos = otosSensor.getPosition().y;
            double xPos = otosSensor.getPosition().x;
            double hPos = otosSensor.getPosition().h;

            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), -hPID.calculate(hPos));

            powerTelemetry();

        }

        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.reset();

        while (elapsedTime.milliseconds() < holdTime){
            double yPos = otosSensor.getPosition().y;
            double xPos = otosSensor.getPosition().x;
            double hPos = otosSensor.getPosition().h;

            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), -hPID.calculate(hPos));

            powerTelemetry();

        }

    }

    /**
     * controls the drivetrain to move and rotate to specific points on the field. If the robot is within the tolerance area, it will stop... probably
     * @param targetY the y target
     * @param targetX the x target
     * @param targetH heading(rotation) target
     * @param speed the max speed pid makes it go slower the closer it gets
     */
    public void movePID(double targetY, double targetX, double targetH, double speed){
        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(kp, ki, kd, targetY, tolerance.y, speed);
        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(kp, ki, kd, targetX, tolerance.x, speed);
        PIDControllerSpeedLimit hPID = new PIDControllerSpeedLimit(kpTheta, kiTheta, kdTheta, targetH, tolerance.h, speed);

        while (!(yPID.atTarget() && xPID.atTarget() && hPID.atTarget())){
            double yPos = otosSensor.getPosition().y;
            double xPos = otosSensor.getPosition().x;
            double hPos = otosSensor.getPosition().h;

            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), -hPID.calculate(hPos));

            powerTelemetry();

        }

    }

    public void fcControl(double y, double x, double h) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(otosSensor.getPosition().h);

        double correctedY = r * Math.sin(correctedTheta);
        double correctedX = r * Math.cos(correctedTheta);

        control(correctedY, correctedX, h);

    }

    /**
     * Y IS FORWARDS AND BACKWARDS
     * @param y +forwards and -backwards
     * @param x strafe -left and +right
     * @param h turn +right and -left
     */
    public void control(double y, double x, double h) {
        frontRightMotor.setPower(Range.clip(y - x - h, -1, 1));
        frontLeftMotor.setPower(Range.clip(y - x + h, -1, 1));
        backRightMotor.setPower(Range.clip(y + x - h, -1, 1));
        backLeftMotor.setPower(Range.clip(y + x + h, -1, 1));

    }

    public void powerTelemetry(){
        TelemetryPasser.telemetry.addData("flPower=", frontLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("frPower=", frontRightMotor.getPower());
        TelemetryPasser.telemetry.addData("blPower=", backLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("brPower=", backRightMotor.getPower());
        TelemetryPasser.telemetry.update();
    }
}
