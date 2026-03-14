package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.Constants;

public class ArtifactLift {
    private DcMotorEx lift;
    private TouchSensor topLimit;
    private TouchSensor bottomLimit;
    private final int TOP_LIMIT = 550;
    private final int BOTTOM_LIMIT = 0;

    public ArtifactLift(DcMotorEx lift) {
        this.lift = lift;
        this.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.lift.setTargetPositionTolerance(Constants.artifactLiftUpperTolerance);
        this.lift.setTargetPosition(0);
        this.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public ArtifactLift(DcMotorEx lift, TouchSensor topLimit, TouchSensor bottomLimit) {
        this.lift = lift;
        this.topLimit = topLimit;
        this.bottomLimit = bottomLimit;
        this.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setLiftMode(DcMotor.RunMode runMode) {
        lift.setMode(runMode);
    }

    public void setLiftPower(double power) {
        lift.setPower(power);
    }

    public void setLiftPosition(int position) {
        lift.setTargetPosition(position);
    }
    public double getLiftPosition() {
        return lift.getCurrentPosition();
    }

    public void setLiftTargetUp() {
        setLiftPosition(TOP_LIMIT);
    }
    public boolean getLiftUp(){
        return getLiftPosition() >= TOP_LIMIT - Constants.artifactLiftUpperTolerance;
    }

    public void setLiftTargetDown() {
        setLiftPosition(BOTTOM_LIMIT);
    }
    public boolean getLiftDown() {
        return getLiftPosition() <= BOTTOM_LIMIT + Constants.artifactLiftLowerTolerance;
    }

    public boolean isTouchingTopLimit() {
        return topLimit.isPressed();
    }
    public boolean isTouchingBottomLimit() {
        return bottomLimit.isPressed();
    }

}
