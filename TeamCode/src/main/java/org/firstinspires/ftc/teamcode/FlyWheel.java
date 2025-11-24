package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

public class FlyWheel {
    private DcMotorEx flyWheelMotor;
    TelemetryPasser telemetry;

    public FlyWheel(DcMotorEx flyWheelMotor) {
        this.flyWheelMotor = flyWheelMotor;
        flyWheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        flyWheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    void setPower(double power) {
        flyWheelMotor.setPower(Range.clip(power, -1, 1));
        TelemetryPasser.telemetry.addData("FlyWheel Velocity", flyWheelMotor.getVelocity());
    }
    double getPower() {
        return flyWheelMotor.getPower();
    }
}
