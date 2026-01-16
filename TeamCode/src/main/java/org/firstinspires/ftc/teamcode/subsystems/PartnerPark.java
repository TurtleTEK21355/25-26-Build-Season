package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.TelemetryPasser;

public class PartnerPark {
    private DcMotorEx rightViperSlide;
    private DcMotorEx leftViperSlide;
    private final double HOLD_POWER = 0.1;
    private final double UP_POWER = 1;
    private final double DOWN_POWER = -1;
    public enum State {
        UP,
        DOWN,
        STAY
    }
    public PartnerPark(DcMotorEx rightViperSlide,DcMotorEx leftViperSlide){
        this.rightViperSlide = rightViperSlide;
        this.leftViperSlide = leftViperSlide;
        this.rightViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftViperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightViperSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftViperSlide.setDirection(DcMotorSimple.Direction.FORWARD);

    }
    public void control(State state) {
        switch(state) {
            case UP:
                if (rightViperSlide.getCurrentPosition() < 110) {
                    rightViperSlide.setPower(UP_POWER);
                    leftViperSlide.setPower(UP_POWER);
                }
                else {
                    rightViperSlide.setPower(HOLD_POWER);
                    leftViperSlide.setPower(HOLD_POWER);
                }
            case DOWN:
                if (rightViperSlide.getCurrentPosition() > 0) {
                    rightViperSlide.setPower(DOWN_POWER);
                    leftViperSlide.setPower(DOWN_POWER);
                }
                else {
                    rightViperSlide.setPower(HOLD_POWER);
                    leftViperSlide.setPower(HOLD_POWER);
                }
            case STAY:
                rightViperSlide.setPower(HOLD_POWER);
                leftViperSlide.setPower(HOLD_POWER);
        }
    }

    public void positionTelemetry() {
        TelemetryPasser.telemetry.addData("Right VS Position", rightViperSlide.getCurrentPosition());
        TelemetryPasser.telemetry.addData("Left VS Position", leftViperSlide.getCurrentPosition());
    }

}
