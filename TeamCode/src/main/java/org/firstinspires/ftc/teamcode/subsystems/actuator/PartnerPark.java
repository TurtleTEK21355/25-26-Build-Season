package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.TelemetryPasser;

public class PartnerPark {
    private DcMotorEx rightViperSlide;
    private DcMotorEx leftViperSlide;
    private final double HOLD_UP_POWER = 0.15;
    private final double UP_POWER = 1;
    private final double HOLD_DOWN_POWER = -0.1;
    private final double DOWN_POWER = -1;
    private final int MAX = 3000;
    private final int THRESHOLD = 100;
    private final int MIN = -30;

    public PartnerPark(DcMotorEx rightViperSlide,DcMotorEx leftViperSlide){
        this.rightViperSlide = rightViperSlide;
        this.leftViperSlide = leftViperSlide;
        this.rightViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightViperSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftViperSlide.setDirection(DcMotorSimple.Direction.FORWARD);


    }

    public void up() {
        if (rightViperSlide.getCurrentPosition() < MAX) {
            rightViperSlide.setPower(UP_POWER);
            leftViperSlide.setPower(UP_POWER);
        }
        else {
            rightViperSlide.setPower(HOLD_UP_POWER);
            leftViperSlide.setPower(HOLD_UP_POWER);
        }
    }

    public void down() {
        if (rightViperSlide.getCurrentPosition() > MIN) {
            rightViperSlide.setPower(DOWN_POWER);
            leftViperSlide.setPower(DOWN_POWER);
        }
        else {
            rightViperSlide.setPower(0);
            leftViperSlide.setPower(0);
        }
    }

    public void stay() {
        if (rightViperSlide.getCurrentPosition() > THRESHOLD) {
            rightViperSlide.setPower(HOLD_UP_POWER);
            leftViperSlide.setPower(HOLD_UP_POWER);
        }
        else {
            rightViperSlide.setPower(HOLD_DOWN_POWER);
            leftViperSlide.setPower(HOLD_DOWN_POWER);
        }
    }

    public void positionTelemetry() {
        TelemetryPasser.telemetry.addData("Right VS Position", rightViperSlide.getCurrentPosition());
        TelemetryPasser.telemetry.addData("Left VS Position", leftViperSlide.getCurrentPosition());
    }

}
