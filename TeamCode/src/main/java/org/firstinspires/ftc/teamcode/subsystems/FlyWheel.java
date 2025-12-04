package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TelemetryPasser;

public class FlyWheel {
    private DcMotorEx flyWheelMotor;

    public FlyWheel(DcMotorEx flyWheelMotor) {
        this.flyWheelMotor = flyWheelMotor;
        flyWheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        flyWheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    void setVelocity(double velocity) {
        flyWheelMotor.setVelocity(Range.clip(velocity, -1500, 1500));
    }
    double getVelocity() {
        TelemetryPasser.telemetry.addData("FlyWheel Velocity in ticks/s", flyWheelMotor.getVelocity());
        return flyWheelMotor.getVelocity();
    }
}
