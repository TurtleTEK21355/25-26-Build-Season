package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;

public class Hopper {
    private CRServo rightServo;
    private CRServo leftServo;
    private Servo ballGate;
    private Ada2167BreakBeam breakBeamSensor;


    public Hopper(CRServo rightServo, Servo ballGate, Ada2167BreakBeam breakBeamSensor) {
        this.rightServo = rightServo;
        this.rightServo.setDirection(DcMotorSimple.Direction.FORWARD);
        this.ballGate = ballGate;
        this.breakBeamSensor = breakBeamSensor;
    }

    void setPower(double power) {
        rightServo.setPower(Range.clip(power, -1.0, 1.0));
    }

    void openGate() {
        ballGate.setPosition(0);
    }

    void closeGate() {
        ballGate.setPosition(1);
    }
    boolean ballReady() {
        return breakBeamSensor.inProximity();
    }
}
