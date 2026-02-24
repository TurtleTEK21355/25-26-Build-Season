package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.util.Range;

public class TurretSystem {
    private FlyWheel flyWheel;
    private Hood hood;
    private double targetFlywheelVelocity;

    public TurretSystem(FlyWheel flyWheel, Hood hood) {
        this.flyWheel = flyWheel;
        this.hood = hood;
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
        hood.setToAngle(angle);
    }
}
