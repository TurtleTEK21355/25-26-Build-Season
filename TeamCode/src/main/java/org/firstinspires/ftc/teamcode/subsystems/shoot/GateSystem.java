package org.firstinspires.ftc.teamcode.subsystems.shoot;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;

public class GateSystem {
    private Servo ballGate;
    private Ada2167BreakBeam breakBeamSensor;
    private final double CLOSED_POSITION = (double) 1 / 3;
    private final double OPEN_POSITION = 0;

    public GateSystem(Servo ballGate, Ada2167BreakBeam breakBeamSensor) {
        this.ballGate = ballGate;
        this.ballGate.setDirection(Servo.Direction.REVERSE);
        this.breakBeamSensor = breakBeamSensor;

    }

    void closeGate() {
        ballGate.setPosition(CLOSED_POSITION);
        TelemetryPasser.telemetry.addData("expected position", CLOSED_POSITION);
        TelemetryPasser.telemetry.addData("position", ballGate.getPosition());

    }

    void openGate() {
        ballGate.setPosition(OPEN_POSITION);
        TelemetryPasser.telemetry.addData("expected position", OPEN_POSITION);
        TelemetryPasser.telemetry.addData("position", ballGate.getPosition());

    }

    boolean ballReady() {
        return breakBeamSensor.inProximity();
    }

}