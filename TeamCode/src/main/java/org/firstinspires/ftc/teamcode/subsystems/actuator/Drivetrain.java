package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class Drivetrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    private PIDConstants pidConstants = new PIDConstants(0.1, 0, 0);
    private PIDConstants thetaPIDConstants = new PIDConstants(0.03, 0, 0);
    private final Pose2D PID_TOLERANCE = new Pose2D(2, 2, 2.5);
    private final double ROTATION_PID_SPEED = 0.75;

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

    public void fcControl(double y, double x, double h, Pose2D position) {
        double r = Math.hypot(y, x);
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(position.h);

        double correctedY = r * Math.sin(correctedTheta);
        double correctedX = r * Math.cos(correctedTheta);

        control(correctedY, correctedX, h);
    }

    public void fcControl(double y, double x, double h, AllianceSide side, Pose2D position) {
        //this is a little confusing, but this is basically a null checker for side and position
        //because of the way that the stateRobot class works, if side is null then forward will be
        //0 degrees so the direction it starts in, and if position is null then it will pass in
        //position as 0 0 0 basically turning off field centric.
        int forwardDirection = 0;
        if (side != null) {
            forwardDirection = side.getForwardDirection();
        }
        if (position == null) {
            position = new Pose2D(0, 0, 0);
        }

        double r = Math.hypot(y, x);
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(position.h + forwardDirection);

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

    public double getRotationToGoal(AllianceSide side, Pose2D position) {
        Pose2D distance = side.getGoalPosition().subtract(position);
        return Math.toDegrees(distance.getTheta());
    }
    public boolean rotateToGoal(Pose2D position, AllianceSide side, boolean telemetry){
        double angle = position.positionsToFCAngle(side.getGoalPosition());
        PIDControllerHeading hPID = new PIDControllerHeading(getThetaPIDConstants(), angle, getTolerance().h, ROTATION_PID_SPEED);
        if (!hPID.atTarget(position.h)) {
            fcControl(0, 0, hPID.calculate(position.h), position);
            if (telemetry) {
                TelemetryPasser.telemetry.addData("Rotation Target: ", angle);
                TelemetryPasser.telemetry.addData("Rotation Distance Left: ", angle - position.h);
            }
        }
        return hPID.atTarget(position.h);
    }

    // Sends power of each motor to telemetry
    public void powerTelemetry() {
        TelemetryPasser.telemetry.addLine()
        .addData("fl Power: ", frontLeftMotor.getPower())
        .addData("fr Power: ", frontRightMotor.getPower())
        .addData("bl Power: ", backLeftMotor.getPower())
        .addData("br Power: ", backRightMotor.getPower());

    }

    public void PIDTelemetry(Pose2D pos, Pose2D target, boolean xAtTarget, boolean yAtTarget, boolean hAtTarget) {
        TelemetryPasser.telemetry.addLine()
            .addData("X Position: ", pos.x)
            .addData("Y Position: ", pos.y)
            .addData("Heading: ", pos.h);

        TelemetryPasser.telemetry.addLine()
        .addData("Target X: ", target.x)
        .addData("Target Y: ", target.y)
        .addData("Target H: ", target.h);

        TelemetryPasser.telemetry.addLine()
        .addData("At Target X: ", xAtTarget)
        .addData("At Target Y: ", yAtTarget)
        .addData("At Target H: ", hAtTarget);

    }

    public void setWheelDirection(DcMotorSimple.Direction lf, DcMotorSimple.Direction rf, DcMotorSimple.Direction lb, DcMotorSimple.Direction rb) {
        this.frontLeftMotor.setDirection(lf);
        this.frontRightMotor.setDirection(rf);
        this.backLeftMotor.setDirection(lb);
        this.backRightMotor.setDirection(rb);

    }

    public void configurePIDConstants(PIDConstants pidConstants, PIDConstants thetaPIDConstants) {
        this.pidConstants = pidConstants;
        this.thetaPIDConstants = thetaPIDConstants;

    }
    public PIDConstants getPIDConstants() {
        return pidConstants;
    }
    public PIDConstants getThetaPIDConstants() {
        return thetaPIDConstants;
    }
    public Pose2D getTolerance() {
        return PID_TOLERANCE;
    }

}
