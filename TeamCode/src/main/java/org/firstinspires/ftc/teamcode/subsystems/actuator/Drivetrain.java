package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class Drivetrain {
    private final DcMotor frontLeftMotor;
    private final DcMotor frontRightMotor;
    private final DcMotor backLeftMotor;
    private final DcMotor backRightMotor;


    public Drivetrain(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight){
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

    public void fcControl(double y, double x, double h, AllianceSide side, double heading) {
        y = Math.pow(y, Constants.drivetrainExponentIndex);
        x = Math.pow(x, Constants.drivetrainExponentIndex);
        h = Math.pow(h, Constants.drivetrainExponentIndex);

        //this is a little confusing, but this is basically a null checker for side
        //because of the way that the stateRobot class works, if side is null then forward will be
        //0 degrees so the direction it starts in

        int forwardDirection = 0;
        if (side != null) {
            forwardDirection = side.getForwardDirection();
        }

        double r = Math.hypot(y, x);
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(heading + forwardDirection);

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

    public void resetEncoderPosition() {
//        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public double getEncoderPosition() {
//        return (frontRightMotor.getCurrentPosition() + frontLeftMotor.getCurrentPosition()) / 2.0;
        return frontRightMotor.getCurrentPosition();
    }


    public void setTarget(int position) {
//        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
//        frontLeftMotor.setTargetPosition(position);
        frontRightMotor.setTargetPosition(position);
//        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        frontLeftMotor.setPower(0);

    }

    public void setTarget(int position, double speed) {
//        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
//        frontLeftMotor.setTargetPosition(position);
        frontRightMotor.setTargetPosition(position);
//        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void setNonTargetMotors(double speed){
        frontRightMotor.setPower(speed);
        backLeftMotor.setPower(frontLeftMotor.getPower());
//        if (frontRightMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
        frontLeftMotor.setPower(frontLeftMotor.getPower());
        backRightMotor.setPower(frontLeftMotor.getPower());
//        } else {
//            backRightMotor.setPower(frontRightMotor.getPower());
//        }
    }

}
