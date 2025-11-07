package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Hopper {
    private CRServo rightServo;
    private CRServo leftServo;
    //private ColorSensor colorSensor; different sensor will be used

    public Hopper(CRServo rightServo) {
        this.rightServo = rightServo;
        this.rightServo.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    void setPower(double power) {
        rightServo.setPower(Range.clip(power, -1.0, 1.0));
    }
    void ballReady(){
        /*
        return sensor;
         */
    }
}
