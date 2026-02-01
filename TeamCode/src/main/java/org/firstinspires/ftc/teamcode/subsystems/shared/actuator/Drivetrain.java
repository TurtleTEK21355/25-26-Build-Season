package org.firstinspires.ftc.teamcode.subsystems.shared.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.OTOSSensor;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.subsystems.shared.sensor.OTOSSensor;

public class Drivetrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private OTOSSensor otosSensor;
    private PIDConstants pidConstants;
    private PIDConstants thetaPIDConstants;
    private final Pose2D TOLERANCE = new Pose2D(2, 2, 2.5);

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

    public void configurePIDConstants(PIDConstants pidConstants, PIDConstants thetaPIDConstants) {
        this.pidConstants = pidConstants;
        this.thetaPIDConstants = thetaPIDConstants;

    }

    public void fcControl(double y, double x, double h, int forwardDirection) {
        double r = Math.hypot(y, x);
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(otosSensor.getPosition().h + forwardDirection);

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

    // Sets the robot rotation using PID
    public void ShootRotationalPID(AllianceSide side) {
        Pose2D position = getPosition();
        double target = getRotationToGoal(side, position);
        PIDControllerHeading hPID = new PIDControllerHeading(getThetaPIDConstants(), target, getTolerance().h, 0.5);
        while(!hPID.atTarget(position.h)) {
            position = getPosition();
            fcControl(0, 0, hPID.calculate(position.h), 0);
            TelemetryPasser.telemetry.addData("Shoot Rotation: ", hPID.atTarget(position.h));
        }
    }
    public double getRotationToGoal(AllianceSide side, Pose2D position) {
        Pose2D distance = side.getGoalPosition().subtract(position);
        return Math.toDegrees(distance.getTheta());
    }

    // Sends power of each motor to telemetry
    public void powerTelemetry(){
        TelemetryPasser.telemetry.addLine()
        .addData("fl Power: ", frontLeftMotor.getPower())
        .addData("fr Power: ", frontRightMotor.getPower())
        .addData("bl Power: ", backLeftMotor.getPower())
        .addData("br Power: ", backRightMotor.getPower());

    }

    public void PIDTelemetry(Pose2D pos, Pose2D target, boolean xAtTarget, boolean yAtTarget, boolean hAtTarget){
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

    public Pose2D getPosition() {
        return otosSensor.getPosition();
    }

    public PIDConstants getPIDConstants() {
        return pidConstants;
    }

    public PIDConstants getThetaPIDConstants() {
        return thetaPIDConstants;
    }

    public Pose2D getTolerance() {
        return TOLERANCE;
    }

}
