package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;

public class GateSystem {
    private Servo ballGate;
    private Ada2167BreakBeam breakBeamSensor;


    public GateSystem(Servo ballGate, Ada2167BreakBeam breakBeamSensor) {
        this.ballGate = ballGate;
        this.ballGate.setDirection(Servo.Direction.REVERSE);
        this.breakBeamSensor = breakBeamSensor;
    }
    void closeGate() {
        ballGate.setPosition((double)1/3);
        TelemetryPasser.telemetry.addData("expected position", (double)1/3);
        TelemetryPasser.telemetry.addData("position", ballGate.getPosition());
    }

    void openGate() {
        ballGate.setPosition(0);
        TelemetryPasser.telemetry.addData("expected position", 0);
        TelemetryPasser.telemetry.addData("position", ballGate.getPosition());
    }
    boolean ballReady() {
        return breakBeamSensor.inProximity();
    }
}