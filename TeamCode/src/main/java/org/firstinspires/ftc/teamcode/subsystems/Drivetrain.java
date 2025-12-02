package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class Drivetrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private OTOSSensor otosSensor;
    private AprilTagCamera aprilTagCamera;
    private PIDConstants pidConstants;
    private PIDConstants thetaPIDConstants;
    private final Pose2D tolerance = new Pose2D(2, 2, 10);
    private Pose2D position;
    private Pose2D offset;
    double yPosTelemetry;
    double xPosTelemetry;
    double hPosTelemetry;
    double yTargetTelemetry;
    double xTargetTelemetry;
    double hTargetTelemetry;
    boolean yAtTargetTelemetry;
    boolean xAtTargetTelemetry;
    boolean hAtTargetTelemetry;

    public Drivetrain(DcMotor frontLeft,DcMotor frontRight, DcMotor backLeft, DcMotor backRight){
        this.frontLeftMotor = frontLeft;
        this.frontRightMotor = frontRight;
        this.backLeftMotor = backLeft;
        this.backRightMotor = backRight;
        this.frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setWheelDirection(DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD);


    }

    public Drivetrain(DcMotor frontLeft,DcMotor frontRight, DcMotor backLeft, DcMotor backRight, OTOSSensor otosSensor){
        this.frontLeftMotor = frontLeft;
        this.frontRightMotor = frontRight;
        this.backLeftMotor = backLeft;
        this.backRightMotor = backRight;
        this.otosSensor = otosSensor;
        this.frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setWheelDirection(DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD);

    }

    public Drivetrain(DcMotor frontLeft,DcMotor frontRight, DcMotor backLeft, DcMotor backRight, OTOSSensor otosSensor, AprilTagCamera aprilTagCamera){
        this.frontLeftMotor = frontLeft;
        this.frontRightMotor = frontRight;
        this.backLeftMotor = backLeft;
        this.backRightMotor = backRight;
        this.otosSensor = otosSensor;
        this.aprilTagCamera = aprilTagCamera;
        this.frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setWheelDirection(DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD);


    }

    public void configurePIDConstants(PIDConstants pidConstants, PIDConstants thetaPIDConstants, double offsetX, double offsetY, double offsetH) {
        this.pidConstants = pidConstants;
        this.thetaPIDConstants = thetaPIDConstants;

        offset = new Pose2D(offsetX,offsetY,offsetH);
    }
    public double getOffsetX() {
        return offset.x;
    }
    public double getOffsetY() {
        return offset.y;
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
        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(pidConstants, targetY, tolerance.y, speed);
        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(pidConstants, targetX, tolerance.x, speed);
        PIDControllerHeading hPID = new PIDControllerHeading(thetaPIDConstants, targetH, tolerance.h, speed);

        /*
        * applies offset by rotating the origin and then applying x/y offset
        * uses following equations:
        * x' = xcos(theta)+ysin(theta)-yoffset
        * y' = -xsin(theta)+ycos(theta)-xoffset
        * h' = h+hoffset
        */
        Pose2D realPos = otosSensor.getPosition();
        double yPos = (-(realPos.x)*Math.sin(offset.h))+(realPos.y*Math.cos(offset.h))+offset.y;
        double xPos = realPos.x*Math.cos(offset.h)+(realPos.y*Math.sin(offset.h))+offset.x;
        double hPos = realPos.h+offset.h;

        // continues updating speed using PID until position targets are reached
        while (!yPID.atTarget(yPos) || !xPID.atTarget(xPos) || !hPID.atTarget(hPos)){
            realPos = otosSensor.getPosition();
            yPos = (-(realPos.x)*Math.sin(offset.h))+(realPos.y*Math.cos(offset.h))+offset.y;
            xPos = realPos.x*Math.cos(offset.h)+(realPos.y*Math.sin(offset.h))+offset.x;
            hPos = realPos.h+offset.h;

            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), hPID.calculate(hPos));

            xPosTelemetry = xPos;
            yPosTelemetry = yPos;
            hPosTelemetry = hPos;

            xTargetTelemetry = targetX;
            yTargetTelemetry = targetY;
            hTargetTelemetry = targetH;

            xAtTargetTelemetry = xPID.atTarget(xPos);
            yAtTargetTelemetry = yPID.atTarget(yPos);
            hAtTargetTelemetry = hPID.atTarget(hPos);

            powerTelemetry();
            TelemetryPasser.telemetry.update();

        }

        // holds for a specified time (while still correcting position) for more accurate movement
        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();

        while (holdTimer.milliseconds() <= holdTime){
            realPos = otosSensor.getPosition();
            yPos = (-(realPos.x)*Math.sin(offset.h))+(realPos.y*Math.cos(offset.h))+offset.y;
            xPos = realPos.x*Math.cos(offset.h)+(realPos.y*Math.sin(offset.h))+offset.x;
            hPos = realPos.h+offset.h;
            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), hPID.calculate(hPos));

            xPosTelemetry = xPos;
            yPosTelemetry = yPos;
            hPosTelemetry = hPos;

            xTargetTelemetry = targetX;
            yTargetTelemetry = targetY;
            hTargetTelemetry = targetH;

            xAtTargetTelemetry = xPID.atTarget(xPos);
            yAtTargetTelemetry = yPID.atTarget(yPos);
            hAtTargetTelemetry = hPID.atTarget(hPos);

            powerTelemetry();
            TelemetryPasser.telemetry.update();
        }

        //ensures no motors are being sent power after the robot has reached positions and fulfilled hold time.
        control(0,0,0);

    }

    public void movePID(double targetY, double targetX, double targetH, double speed, int holdTime, double mToleranceX, double mToleranceY, double mToleranceH){
        Pose2D manualTolerance = new Pose2D(mToleranceX, mToleranceY, mToleranceH);
        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(pidConstants, targetY, manualTolerance.y, speed);
        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(pidConstants, targetX, manualTolerance.x, speed);
        PIDControllerHeading hPID = new PIDControllerHeading(thetaPIDConstants, targetH, manualTolerance.h, speed);

        /*
         * applies offset by rotating the origin and then applying x/y offset
         * uses following equations:
         * x' = x*cos(theta)+y*sin(theta)-y_offset
         * y' = -x*sin(theta)+y*cos(theta)-x_offset
         * h' = h+h_offset
         */
        Pose2D realPos = otosSensor.getPosition();
        double yPos = (-(realPos.x)*Math.sin(offset.h))+(realPos.y*Math.cos(offset.h))+offset.y;
        double xPos = realPos.x*Math.cos(offset.h)+(realPos.y*Math.sin(offset.h))+offset.x;
        double hPos = realPos.h+offset.h;

        // continues updating speed using PID until position targets are reached
        while (!yPID.atTarget(yPos) || !xPID.atTarget(xPos) || !hPID.atTarget(hPos)){
            realPos = otosSensor.getPosition();
            yPos = (-(realPos.x)*Math.sin(offset.h))+(realPos.y*Math.cos(offset.h))+offset.y;
            xPos = realPos.x*Math.cos(offset.h)+(realPos.y*Math.sin(offset.h))+offset.x;
            hPos = realPos.h+offset.h;
            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), hPID.calculate(hPos));

            xPosTelemetry = xPos;
            yPosTelemetry = yPos;
            hPosTelemetry = hPos;

            xTargetTelemetry = targetX;
            yTargetTelemetry = targetY;
            hTargetTelemetry = targetH;

            xAtTargetTelemetry = xPID.atTarget(xPos);
            yAtTargetTelemetry = yPID.atTarget(yPos);
            hAtTargetTelemetry = hPID.atTarget(hPos);

            powerTelemetry();
            TelemetryPasser.telemetry.update();

        }

        // holds for a specified time (while still correcting position) for more accurate movement
        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();

        while (holdTimer.milliseconds() <= holdTime){
            realPos = otosSensor.getPosition();
            yPos = (-(realPos.x)*Math.sin(offset.h))+(realPos.y*Math.cos(offset.h))+offset.y;
            xPos = realPos.x*Math.cos(offset.h)+(realPos.y*Math.sin(offset.h))+offset.x;
            hPos = realPos.h+offset.h;
            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), hPID.calculate(hPos));


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

        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(pidConstants, targetY, tolerance.y, speed);
        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(pidConstants, targetX, tolerance.x, speed);
        PIDControllerHeading hPID = new PIDControllerHeading(thetaPIDConstants, targetH, tolerance.h, speed);

        while (!yPID.atTarget(yPos) || !xPID.atTarget(xPos) || !hPID.atTarget(hPos)){
            yPos = otosSensor.getPosition().y;
            xPos = otosSensor.getPosition().x;
            hPos = otosSensor.getPosition().h;

            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), hPID.calculate(hPos));

            powerTelemetry();

        }

    }
    public void fcControl(double y, double x, double h) {
        double r = Math.hypot(y, x);
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
        frontLeftMotor.setPower(Range.clip(y + x + h, -1, 1));
        backRightMotor.setPower(Range.clip(y + x - h, -1, 1));
        backLeftMotor.setPower(Range.clip(y - x + h, -1, 1));
    }

    public void joystickMovement(double ly, double lx, double rx, double ry) {
        if (rx != 0 || ry != 0) {
            double magnitude = Math.sqrt((ry*ry)+(rx*rx));
            TelemetryPasser.telemetry.addData("magnitude", magnitude);
            TelemetryPasser.telemetry.update();
            double fr = (ry + rx) / 2;
            double br = (ry + rx) / 2;
            double fl = (ry - rx) / 2;
            double bl = (ry - rx) / 2;
            double min = Math.min(Math.min(fr, br), Math.min(fl, bl));
            frontRightMotor.setPower((fr/min)*magnitude);
            backRightMotor.setPower((br/min)*magnitude);
            frontLeftMotor.setPower((fl/min)*magnitude);
            backLeftMotor.setPower((bl/min)*magnitude);
        } else {
            //double magnitude = Math.sqrt((ly*ly)+(lx*lx));
            double magnitude = 1;
            TelemetryPasser.telemetry.addData("magnitude", magnitude);
            TelemetryPasser.telemetry.update();
            double fr = (ly - lx) / 2;
            double br = (ly + lx) / 2;
            double fl = (ly + lx) / 2;
            double bl = (ly - lx) / 2;
            double min = Math.min(Math.min(fr, br), Math.min(fl, bl));
            min = Math.abs(min);
            frontRightMotor.setPower((fr/min)*magnitude);
            backRightMotor.setPower((br/min)*magnitude);
            frontLeftMotor.setPower((fl/min)*magnitude);
            backLeftMotor.setPower((bl/min)*magnitude);

        }
    }

     // Sends power of each motor to telemetry
    public void powerTelemetry(){
        TelemetryPasser.telemetry.addData("fl Power=", frontLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("fr Power=", frontRightMotor.getPower());
        TelemetryPasser.telemetry.addData("bl Power=", backLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("br Power=", backRightMotor.getPower());
    }

    public void PIDTelemetry(Pose2D pos, Pose2D target, boolean xAtTarget, boolean yAtTarget, boolean hAtTarget){
        TelemetryPasser.telemetry.addData("xPosition", pos.x);
        TelemetryPasser.telemetry.addData("yPosition", pos.y);
        TelemetryPasser.telemetry.addData("hPosition", pos.h);
        TelemetryPasser.telemetry.addLine();
        TelemetryPasser.telemetry.addData("Targetx", target.x);
        TelemetryPasser.telemetry.addData("Targety", target.y);
        TelemetryPasser.telemetry.addData("Targeth", target.h);
        TelemetryPasser.telemetry.addLine();
        TelemetryPasser.telemetry.addData("atTargetx", xAtTarget);
        TelemetryPasser.telemetry.addData("atTargety", yAtTarget);
        TelemetryPasser.telemetry.addData("atTargeth", hAtTarget);
        TelemetryPasser.telemetry.addLine();
    }

    public void setWheelDirection(DcMotorSimple.Direction lf, DcMotorSimple.Direction rf, DcMotorSimple.Direction lb, DcMotorSimple.Direction rb) {
        this.frontLeftMotor.setDirection(lf);
        this.frontRightMotor.setDirection(rf);
        this.backLeftMotor.setDirection(lb);
        this.backRightMotor.setDirection(rb);
    }

    public Pose2D getPosition() {
        return otosSensor.getPosition();
    }

    public double getRange() {
        Pose2D position = otosSensor.getPosition();
        return Math.sqrt(Math.pow(position.x-offset.x-67.215, 2)+Math.pow(position.y-offset.y+74.871, 2));
    }

    public PIDConstants getPIDConstants() {
        return pidConstants;
    }

    public PIDConstants getThetaPIDConstants() {
        return thetaPIDConstants;
    }

    public Pose2D getTolerance() {
        return tolerance;
    }
}
