package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;

public class PartnerPark {
    private DcMotor viperSlide;
    private final double HOLD_UP_POWER = 0.15;
    private final double UP_POWER = 1;
    private final double HOLD_DOWN_POWER = -0.1;
    private final double DOWN_POWER = -1;
    private final int MAX = 3000;
    private final int THRESHOLD = 100;
    private final int MIN = -30;

    public PartnerPark(DcMotor viperSlide){
        this.viperSlide = viperSlide;
        this.viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.viperSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void manualControl(double power) {
        viperSlide.setPower(power);
    }

    public void up() {
        if (viperSlide.getCurrentPosition() < MAX) {
            viperSlide.setPower(UP_POWER);
        }
        else {
            viperSlide.setPower(HOLD_UP_POWER);
        }
    }

    public void down() {
        if (viperSlide.getCurrentPosition() > MIN) {
            viperSlide.setPower(DOWN_POWER);
        }
        else {
            viperSlide.setPower(0);
        }
    }

    public void stay() {
        if (viperSlide.getCurrentPosition() > THRESHOLD) {
            viperSlide.setPower(HOLD_UP_POWER);
        }
        else {
            viperSlide.setPower(HOLD_DOWN_POWER);
        }
    }

    public String positionTelemetry() {
        TelemetryString string = new TelemetryString();
        string.addData("viper slide pos:", viperSlide.getCurrentPosition());
        return string.toString();
    }

}
