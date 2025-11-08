package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;

public class Hopper {
    private CRServo servo;
    private Servo ballGate;
    private Ada2167BreakBeam breakBeamSensor;


    public Hopper(CRServo servo, Servo ballGate, Ada2167BreakBeam breakBeamSensor) {
        this.servo = servo;
        this.servo.setDirection(DcMotorSimple.Direction.FORWARD);
        this.ballGate = ballGate;
        this.breakBeamSensor = breakBeamSensor;
    }

    void setPower(double power) {
        servo.setPower(Range.clip(power, -1.0, 1.0));
        TelemetryPasser.telemetry.addData("power",
                servo.getPower());
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