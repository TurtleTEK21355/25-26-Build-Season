package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class ArtifactLift {
    private DcMotorEx lift;
    private double TOP_LIMIT = 1;
    private double BOTTOM_LIMIT = -1;

    public ArtifactLift(DcMotorEx lift) {
        this.lift = lift;
    }

    public void setLiftPosition(int position) {
        lift.setTargetPosition(position);
    }
    public double getLiftPosition() {
        return lift.getCurrentPosition();
    }
    public void setLiftUp() {
        setLiftPosition(540);
    }
    public void setLiftDown() {
        setLiftPosition(0);
    }

}
