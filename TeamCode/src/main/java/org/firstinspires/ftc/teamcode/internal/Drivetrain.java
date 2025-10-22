package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;
import java.util.List;
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
    List<Double> aprilPositions;

    private Pose2D tolerance = new Pose2D(2, 2, 10);


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

        double yPos = otosSensor.getPosition().y;
        double xPos = otosSensor.getPosition().x;
        double hPos = otosSensor.getPosition().h;

        while (!yPID.atTarget(yPos) || !xPID.atTarget(xPos) || !hPID.atTarget(hPos)){
            yPos = otosSensor.getPosition().y;
            xPos = otosSensor.getPosition().x;
            hPos = otosSensor.getPosition().h;

            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), -hPID.calculate(hPos));

            TelemetryPasser.telemetry.addData("atTargetx", xPID.atTarget(xPos));
            TelemetryPasser.telemetry.addData("atTargety", yPID.atTarget(yPos));
            TelemetryPasser.telemetry.addData("atTargeth", hPID.atTarget(hPos));
            TelemetryPasser.telemetry.addData("hPosition", hPos);
            powerTelemetry();

        }

        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();

        while (holdTimer.milliseconds() <= holdTime){
            yPos = otosSensor.getPosition().y;
            xPos = otosSensor.getPosition().x;
            hPos = otosSensor.getPosition().h;

            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), -hPID.calculate(hPos));

            TelemetryPasser.telemetry.addData("atTargetx", xPID.atTarget(xPos));
            TelemetryPasser.telemetry.addData("atTargety", yPID.atTarget(yPos));
            TelemetryPasser.telemetry.addData("atTargeth", hPID.atTarget(hPos));
            TelemetryPasser.telemetry.addData("hPosition", hPos);
            TelemetryPasser.telemetry.addData("timer", holdTimer);
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
        double yPos = otosSensor.getPosition().y;
        double xPos = otosSensor.getPosition().x;
        double hPos = otosSensor.getPosition().h;

        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(kp, ki, kd, targetY, tolerance.y, speed);
        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(kp, ki, kd, targetX, tolerance.x, speed);
        PIDControllerSpeedLimit hPID = new PIDControllerSpeedLimit(kpTheta, kiTheta, kdTheta, targetH, tolerance.h, speed);

        while (!yPID.atTarget(yPos) || !xPID.atTarget(xPos) || !hPID.atTarget(hPos)){
            yPos = otosSensor.getPosition().y;
            xPos = otosSensor.getPosition().x;
            hPos = otosSensor.getPosition().h;

            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), -hPID.calculate(hPos));

            powerTelemetry();

        }

    }
    public void movePIDAprilTag(double targetY, double targetX, double targetH, double speed, int holdTime){
        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(kp, ki, kd, targetY, tolerance.y, speed);
        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(kp, ki, kd, targetX, tolerance.x, speed);
        PIDControllerSpeedLimit hPID = new PIDControllerSpeedLimit(kpTheta, kiTheta, kdTheta, targetH, tolerance.h, speed);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        timer.startTime();
        aprilTagCamera.updateDetections();
        while ((aprilTagCamera.currentDetections.isEmpty()) && timer.seconds()<5) {
            aprilTagCamera.updateDetections();
        }
        timer.reset();


        while (!yPID.atTarget(aprilPositions.get(1)) || !xPID.atTarget(aprilPositions.get(0)) || !hPID.atTarget(aprilPositions.get(2))){
            aprilTagCamera.updateDetections();
            aprilPositions = aprilTagCamera.detectionPositions;

            fcControl(yPID.calculate(aprilPositions.get(1)), xPID.calculate(aprilPositions.get(0)), -hPID.calculate(aprilPositions.get(2)));

            TelemetryPasser.telemetry.addData("atTargetx", xPID.atTarget(aprilPositions.get(0)));
            TelemetryPasser.telemetry.addData("atTargety", yPID.atTarget(aprilPositions.get(1)));
            TelemetryPasser.telemetry.addData("atTargeth", hPID.atTarget(aprilPositions.get(2)));
            powerTelemetry();

        }

        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();

        while (holdTimer.milliseconds() <= holdTime){
            aprilTagCamera.updateDetections();
            aprilPositions = aprilTagCamera.detectionPositions;

            fcControl(yPID.calculate(aprilPositions.get(1)), xPID.calculate(aprilPositions.get(0)), -hPID.calculate(aprilPositions.get(2)));

            TelemetryPasser.telemetry.addData("atTargetx", xPID.atTarget(aprilPositions.get(0)));
            TelemetryPasser.telemetry.addData("atTargety", yPID.atTarget(aprilPositions.get(1)));
            TelemetryPasser.telemetry.addData("atTargeth", hPID.atTarget(aprilPositions.get(2)));
            TelemetryPasser.telemetry.addData("timer", holdTimer);
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
        TelemetryPasser.telemetry.addData("fl Power=", frontLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("fr Power=", frontRightMotor.getPower());
        TelemetryPasser.telemetry.addData("bl Power=", backLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("br Power=", backRightMotor.getPower());
        TelemetryPasser.telemetry.update();
    }
}
