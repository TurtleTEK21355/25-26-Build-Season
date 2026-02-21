package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class ArtifactLift {
    private DcMotorEx lift;
    private int TOP_LIMIT = 540;
    private int BOTTOM_LIMIT = 0;

    public ArtifactLift(DcMotorEx lift) {
        this.lift = lift;
        this.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.lift.setTargetPositionTolerance(10);
        this.lift.setTargetPosition(0);
        this.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.lift.setPower(0.8);
    }

    public void setLiftPosition(int position) {
        lift.setTargetPosition(position);
    }
    public double getLiftPosition() {
        return lift.getCurrentPosition();
    }
    public void setLiftUp() {
        setLiftPosition(TOP_LIMIT);
    }
    public void setLiftDown() {
        setLiftPosition(BOTTOM_LIMIT);
    }

}
