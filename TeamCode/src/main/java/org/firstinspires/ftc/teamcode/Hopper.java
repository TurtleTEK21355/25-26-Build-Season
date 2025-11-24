package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

public class Hopper {
    private Servo ballGate;
    private Ada2167BreakBeam breakBeamSensor;


    public Hopper(Servo ballGate, Ada2167BreakBeam breakBeamSensor) {
        this.ballGate = ballGate;
        this.ballGate.setDirection(Servo.Direction.FORWARD);
        this.breakBeamSensor = breakBeamSensor;
    }

    void openGate() {
        ballGate.setPosition(0.25);
        TelemetryPasser.telemetry.addData("expected position", 0.25);
        TelemetryPasser.telemetry.addData("position", ballGate.getPosition());
    }

    void closeGate() {
        ballGate.setPosition(0);
        TelemetryPasser.telemetry.addData("expected position", 0);
        TelemetryPasser.telemetry.addData("position", ballGate.getPosition());
    }
    boolean ballReady() {
        return breakBeamSensor.inProximity();
    }
}