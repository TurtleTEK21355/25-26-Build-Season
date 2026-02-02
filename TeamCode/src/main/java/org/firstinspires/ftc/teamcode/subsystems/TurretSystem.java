package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TurretSystem {
    private FlyWheel flyWheel;
    private Servo hoodServo;
    private Servo artifactPusher;
    private final double HOODANGLETOPOSITION = 1; //placeholder value

    /**
     *
     * @param flyWheel
     * @param hoodServo
     * @param artifactPusher
     */
    public TurretSystem(FlyWheel flyWheel, Servo hoodServo, Servo artifactPusher) {
        this.flyWheel = flyWheel;
        this.hoodServo = hoodServo;
        this.artifactPusher = artifactPusher;
    }

    void setFlyWheelVelocity(double velocity) {
        flyWheel.setVelocity(Range.clip(velocity, -1500, 1500));
    }

    double getVelocity() {
        return flyWheel.getVelocity();
    }

    void setHoodAngle(double angle) {
        hoodServo.setPosition(angle*HOODANGLETOPOSITION);
    }
}
