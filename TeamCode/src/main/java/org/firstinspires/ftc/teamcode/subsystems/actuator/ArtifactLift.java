package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class ArtifactLift {
    private Servo lift;
    private double TOP_LIMIT = 1;
    private double BOTTOM_LIMIT = -1;

    public ArtifactLift(Servo lift) {
        this.lift = lift;
    }

    public void setLiftPosition(double position) {
        lift.setPosition(Range.clip(position, BOTTOM_LIMIT, TOP_LIMIT));
    }
    public double getLiftPosition() {
        return lift.getPosition();
    }
    public void setLiftUp() {
        setLiftPosition(1);
    }
    public void setLiftDown() {
        setLiftPosition(0);
    }

}
