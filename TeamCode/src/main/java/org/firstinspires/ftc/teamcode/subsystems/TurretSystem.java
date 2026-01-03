package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TurretSystem {
    private FlyWheel flyWheel;
    private Servo hoodServo;
    private final double HOODANGLETOPOSITION = 1; //placeholder value

    public TurretSystem(FlyWheel flyWheel, Servo hoodServo) {
        this.flyWheel = flyWheel;
        this.hoodServo = hoodServo;
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
