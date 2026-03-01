package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ArtifactLift {
    private DcMotorEx lift;
    private TouchSensor topLimit;
    private TouchSensor bottomLimit;
    private final int TOP_LIMIT = 500;
    private final int BOTTOM_LIMIT = 0;

    public ArtifactLift(DcMotorEx lift) {
        this.lift = lift;
        this.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.lift.setTargetPositionTolerance(10);
        this.lift.setTargetPosition(0);
        this.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.lift.setPower(0.8);
    }
    public ArtifactLift(DcMotorEx lift, TouchSensor topLimit, TouchSensor bottomLimit) {
        this.lift = lift;
        this.topLimit = topLimit;
        this.bottomLimit = bottomLimit;
        this.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setLiftPosition(int position) {
        lift.setTargetPosition(position);
    }
    public double getLiftPosition() {
        return lift.getCurrentPosition();
    }
    public void setLiftUpNoLimit() {
        setLiftPosition(TOP_LIMIT);
    }
    public boolean getLiftUp(){
        return getLiftPosition() >= TOP_LIMIT - 10;
    }
    public void setLiftDownNoLimit() {
        setLiftPosition(BOTTOM_LIMIT);
    }
    public boolean getLiftDown() {
        return getLiftPosition() <= BOTTOM_LIMIT + 10;
    }

    public void setLiftUp(){
        if(!isTouchingTopLimit()) {
            lift.setPower(0.8);
        } else {
            lift.setPower(0);
        }
    }
    public void setLiftDown(){
        if(!isTouchingBottomLimit()) {
            lift.setPower(-0.8);
        } else {
            lift.setPower(0);
        }
    }
    public boolean isTouchingTopLimit() {
        return topLimit.isPressed();
    }
    public boolean isTouchingBottomLimit() {
        return bottomLimit.isPressed();
    }

}
