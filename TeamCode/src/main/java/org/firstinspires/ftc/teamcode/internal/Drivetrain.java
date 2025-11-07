package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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
    private Pose2D tolerance = new Pose2D(2, 2, 10);
    Pose2D position = new Pose2D(0,0,0);
    Pose2D offset = new Pose2D(0,0,0);


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

    public void configureDrivetrain(AprilTagCamera aprilTagCamera, OtosSensor otosSensor, double kp, double ki, double kd, double kpTheta, double kiTheta, double kdTheta, double offsetX, double offsetY, double offsetH) {
        this.otosSensor = otosSensor.sensor;
        this.aprilTagCamera = aprilTagCamera;

        this.kp = kp;
        this.ki = ki;
        this.kd = kd;

        this.kpTheta = kpTheta;
        this.kiTheta = kiTheta;
        this.kdTheta = kdTheta;
        offset = new Pose2D(offsetX,offsetY,offsetH);
        tolerance.x = 2;
        tolerance.y = 2;
        tolerance.h = 10;
    }
    public void configureDrivetrain(AprilTagCamera aprilTagCamera, OtosSensor otosSensor, double kp, double ki, double kd, double kpTheta, double kiTheta, double kdTheta, double offsetX, double offsetY, double offsetH, double mToleranceX, double mToleranceY, double mToleranceH) {
        this.otosSensor = otosSensor.sensor;
        this.aprilTagCamera = aprilTagCamera;

        this.kp = kp;
        this.ki = ki;
        this.kd = kd;

        this.kpTheta = kpTheta;
        this.kiTheta = kiTheta;
        this.kdTheta = kdTheta;
        offset = new Pose2D(offsetX,offsetY,offsetH);
        tolerance.x = mToleranceX;
        tolerance.y = mToleranceY;
        tolerance.h = mToleranceH;
    }
    public void configureTolerance(double mToleranceX, double mToleranceY, double mToleranceH) {
        tolerance.x = mToleranceX;
        tolerance.y = mToleranceY;
        tolerance.h = mToleranceH;
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

        /*
        * applies offset by rotating the origin and then applying x/y offset
        * uses following equations:
        * x' = xcos(theta)+ysin(theta)-yoffset
        * y' = -xsin(theta)+ycos(theta)-xoffset
        * h' = h+hoffset
        */
        double yPos = (-(otosSensor.getPosition().x)*Math.sin(offset.h))+(otosSensor.getPosition().y*Math.cos(offset.h))+offset.y;
        double xPos = otosSensor.getPosition().x*Math.cos(offset.h)+(otosSensor.getPosition().y*Math.sin(offset.h))+offset.x;
        double hPos = otosSensor.getPosition().h+offset.h;

        // continues updating speed using PID until position targets are reached
        while (!yPID.atTarget(yPos) || !xPID.atTarget(xPos) || !hPID.atTarget(hPos)){
            yPos = (-(otosSensor.getPosition().x)*Math.sin(offset.h))+(otosSensor.getPosition().y*Math.cos(offset.h))+offset.y;
            xPos = otosSensor.getPosition().x*Math.cos(offset.h)+(otosSensor.getPosition().y*Math.sin(offset.h))+offset.x;
            hPos = otosSensor.getPosition().h+offset.h;
            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), -hPID.calculate(hPos));

            TelemetryPasser.telemetry.addData("xPosition", xPos);
            TelemetryPasser.telemetry.addData("yPosition", yPos);
            TelemetryPasser.telemetry.addData("hPosition", hPos);
            TelemetryPasser.telemetry.addLine();
            TelemetryPasser.telemetry.addData("Targetx", targetX);
            TelemetryPasser.telemetry.addData("Targety", targetY);
            TelemetryPasser.telemetry.addData("Targeth", targetH);
            TelemetryPasser.telemetry.addLine();
            TelemetryPasser.telemetry.addData("atTargetx", xPID.atTarget(xPos));
            TelemetryPasser.telemetry.addData("atTargety", yPID.atTarget(yPos));
            TelemetryPasser.telemetry.addData("atTargeth", hPID.atTarget(hPos));
            TelemetryPasser.telemetry.addLine();
            powerTelemetry();
        }

        // holds for a specified time (while still correcting position) for more accurate movement
        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();

        while (holdTimer.milliseconds() <= holdTime){
            yPos = (-(otosSensor.getPosition().x)*Math.sin(offset.h))+(otosSensor.getPosition().y*Math.cos(offset.h))+offset.y;
            xPos = otosSensor.getPosition().x*Math.cos(offset.h)+(otosSensor.getPosition().y*Math.sin(offset.h))+offset.x;
            hPos = otosSensor.getPosition().h+offset.h;
            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), -hPID.calculate(hPos));

            TelemetryPasser.telemetry.addData("xPosition", xPos);
            TelemetryPasser.telemetry.addData("yPosition", yPos);
            TelemetryPasser.telemetry.addData("hPosition", hPos);
            TelemetryPasser.telemetry.addLine();
            TelemetryPasser.telemetry.addData("Targetx", targetX);
            TelemetryPasser.telemetry.addData("Targety", targetY);
            TelemetryPasser.telemetry.addData("Targeth", targetH);
            TelemetryPasser.telemetry.addLine();
            TelemetryPasser.telemetry.addData("atTargetx", xPID.atTarget(xPos));
            TelemetryPasser.telemetry.addData("atTargety", yPID.atTarget(yPos));
            TelemetryPasser.telemetry.addData("atTargeth", hPID.atTarget(hPos));
            TelemetryPasser.telemetry.addLine();
            powerTelemetry();
        }

        //ensures no motors are being sent power after the robot has reached positions and fulfilled hold time.
        control(0,0,0);

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


    public void fcControl(double y, double x, double h) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(otosSensor.getPosition().h);

        double correctedY = r * Math.sin(correctedTheta);
        double correctedX = r * Math.cos(correctedTheta);

        control(correctedY, correctedX, -h);

    }

    /* After the robot moves to where it thinks is (-1.5, 1.5) at -45 degrees,
     * it will correct its odometry based on the position of AprilTag ID 20
     * and move to the correct location.
     */
    public void correctViaAprilTagTest() {
        movePID(48, -48, 45, 0.25, 1000);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        timer.startTime();
        // Robot may take up to 5 seconds to detect an AprilTag
        while ((!aprilTagCamera.isDetected()) && timer.seconds()<5) {
            aprilTagCamera.updateDetections();
            TelemetryPasser.telemetry.addData("Is Detection?", "No");
            TelemetryPasser.telemetry.update();
        }
        timer.reset();

        // uses AprilTag detections to correct odometry
        // offset to be changed when robot is built to account for camera position relative to odometry sensor
        position = aprilTagCamera.getDetections();
        offset.x += position.x*Math.cos(-135)+position.y*Math.sin(-135)-60;
        offset.y += -position.x*Math.sin(-135)+position.y*Math.cos(-135)+60;
        offset.h += 45-position.h;
        movePID(5, -5, 45, 0.25, 1000);
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

     // 1. Sends power of each motor to telemetry
     // 2. Updates telemetry
    public void powerTelemetry(){
        TelemetryPasser.telemetry.addData("fl Power=", frontLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("fr Power=", frontRightMotor.getPower());
        TelemetryPasser.telemetry.addData("bl Power=", backLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("br Power=", backRightMotor.getPower());
        TelemetryPasser.telemetry.update();
    }
}
