package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

public class Hopper {
    private CRServo servo;
    private Servo ballGate;
    private Ada2167BreakBeam breakBeamSensor;


    public Hopper(CRServo servo, Servo ballGate, Ada2167BreakBeam breakBeamSensor) {
        this.servo = servo;
        this.servo.setDirection(DcMotorSimple.Direction.REVERSE);
        this.ballGate = ballGate;
        this.ballGate.setDirection(Servo.Direction.REVERSE);
        this.breakBeamSensor = breakBeamSensor;
    }

    void setPower(double power) {
        servo.setPower(Range.clip(power, -1.0, 1.0));
        TelemetryPasser.telemetry.addData("power",
                servo.getPower());
    }

    void openGate() {
        ballGate.setPosition(0.25);
    }

    void closeGate() {
        ballGate.setPosition(0);
    }
    boolean ballReady() {
        return breakBeamSensor.inProximity();
    }
}