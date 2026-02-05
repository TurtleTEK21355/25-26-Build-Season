package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TurretSystem {
    private FlyWheel flyWheel;
    private Servo hoodServo;
    private final double HOODANGLETOPOSITION = 1; //placeholder value
    private double targetFlywheelVelocity;

    public TurretSystem(FlyWheel flyWheel, Servo hoodServo) {
        this.flyWheel = flyWheel;
        this.hoodServo = hoodServo;
    }

    public void setFlywheelVelocity(double velocity) {
        targetFlywheelVelocity = velocity;
        flyWheel.setVelocity(Range.clip(velocity, -1500, 1500));
    }

    public double getFlywheelVelocity() {
        return flyWheel.getVelocity();
    }
    public void setFlyWheelPower(double power) {
        flyWheel.setPower(power);
    }
    public double getFlywheelTargetVelocity() {
        return targetFlywheelVelocity;
    }

    public void setHoodAngle(double angle) {
        hoodServo.setPosition(angle*HOODANGLETOPOSITION);
    }
}
