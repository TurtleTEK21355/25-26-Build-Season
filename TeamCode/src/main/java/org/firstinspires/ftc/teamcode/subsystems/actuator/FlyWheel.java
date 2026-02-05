package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

public class FlyWheel {
    private DcMotorEx flyWheelMotor;
    private final double MAX_SPEED = 1500;

    public FlyWheel(DcMotorEx flyWheelMotor) {
        this.flyWheelMotor = flyWheelMotor;
        flyWheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        flyWheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void setVelocity(double velocity) {
        flyWheelMotor.setVelocity(Range.clip(velocity, 0, MAX_SPEED));

    }

    public double getVelocity() {
        return flyWheelMotor.getVelocity();

    }

    public void setPower(double power) {
        flyWheelMotor.setPower(power);
    }

}
