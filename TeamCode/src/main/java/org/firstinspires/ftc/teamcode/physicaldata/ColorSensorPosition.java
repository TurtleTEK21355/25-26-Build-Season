package org.firstinspires.ftc.teamcode.physicaldata;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

public enum ColorSensorPosition {
    SHOOT(0),
    LEFT((1.0)/3),
    RIGHT((2.0)/3),
    INTAKESHOOT((1.0)/6),
    INTAKELEFT(0.5),
    INTAKERIGHT((5.0)/6);

    private final double absolutePosition;

    ColorSensorPosition(double absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public double getAbsolutePosition() {
        return absolutePosition;
    }
}
