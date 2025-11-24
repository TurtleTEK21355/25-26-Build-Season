package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

public class PartnerPark {
    private DcMotor rightViperSlide;
    private DcMotor leftViperSlide;
    public PartnerPark(DcMotor rightViperSlide,DcMotor leftViperSlide){
        this.rightViperSlide = rightViperSlide;
        this.leftViperSlide = leftViperSlide;
        this.rightViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightViperSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftViperSlide.setDirection(DcMotorSimple.Direction.FORWARD);

    }
    public void control(boolean up, boolean down) {
        if (up) {
            rightViperSlide.setPower(1);
            leftViperSlide.setPower(1);
            while (rightViperSlide.getCurrentPosition() < 110 && leftViperSlide.getCurrentPosition() < 110) {
                TelemetryPasser.telemetry.addData("Viper Slide Pos", leftViperSlide.getCurrentPosition());
            }
            rightViperSlide.setPower(0);
            leftViperSlide.setPower(0);
        } else if (down){
            rightViperSlide.setPower(-1);
            leftViperSlide.setPower(-1);
            while (rightViperSlide.getCurrentPosition() > 5 && leftViperSlide.getCurrentPosition() > 5);
            rightViperSlide.setPower(0);
            leftViperSlide.setPower(0);
        } else {
            rightViperSlide.setPower(0);
            leftViperSlide.setPower(0);
        }
    }
}
