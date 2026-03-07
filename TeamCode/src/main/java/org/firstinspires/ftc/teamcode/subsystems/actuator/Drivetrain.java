package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerSpeedLimit;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.DrivetrainMode;

public class Drivetrain {
    private DcMotorEx frontLeftMotor;
    private DcMotorEx frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    private final double ROTATION_PID_SPEED = 0.75;

    public Drivetrain(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotor backLeft, DcMotor backRight){
        this.frontLeftMotor = frontLeft;
        this.frontRightMotor = frontRight;
        this.backLeftMotor = backLeft;
        this.backRightMotor = backRight;
        this.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setWheelDirection(DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD);

    }

    public void fcControl(double y, double x, double h, double heading) {
        double r = Math.hypot(y, x);
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(heading);

        double correctedY = r * Math.sin(correctedTheta);
        double correctedX = r * Math.cos(correctedTheta);

        control(correctedY, correctedX, h);
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
        y = Math.pow(y, Constants.drivetrainExponentIndex);
        x = Math.pow(x, Constants.drivetrainExponentIndex);
        h = Math.pow(h, Constants.drivetrainExponentIndex);



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

    public void setMode(DrivetrainMode drivetrainMode){
        switch (drivetrainMode) {
            case TARGET:
                frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontLeftMotor.setTargetPosition(0);
                frontRightMotor.setTargetPosition(0);
                frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                break;
            case FREE:
                frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                break;
        }
    }

    public void setTarget(int position){
        frontLeftMotor.setPower(0.4);
        frontRightMotor.setPower(0.4);
        frontLeftMotor.setTargetPosition(position);
        frontRightMotor.setTargetPosition(position);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setPower(frontLeftMotor.getPower());
        backRightMotor.setPower(frontLeftMotor.getPower());
        frontRightMotor.setPower(frontLeftMotor.getPower());

    }

    public void setTarget(int position, double speed){
        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        frontLeftMotor.setTargetPosition(position);
        frontRightMotor.setTargetPosition(position);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setPower(frontLeftMotor.getPower());
        backRightMotor.setPower(frontLeftMotor.getPower());
        frontRightMotor.setPower(frontLeftMotor.getPower());
    }

    public boolean atPosition(){
        if (frontRightMotor.getMode() == DcMotor.RunMode.RUN_TO_POSITION && frontLeftMotor.getMode() == DcMotor.RunMode.RUN_TO_POSITION) {
            int averagePosition = (frontLeftMotor.getCurrentPosition() + frontLeftMotor.getCurrentPosition())/2;
            return averagePosition > frontRightMotor.getTargetPosition() - 20 && frontRightMotor.getTargetPosition() + 20 > averagePosition;
        } else {
            return true;
        }
    }


}
