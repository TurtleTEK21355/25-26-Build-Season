package org.firstinspires.ftc.teamcode.physicaldata;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

public enum ColorSensorPosition {
    SHOOT(0),
    LEFT((2.0)/3),
    RIGHT((1.0)/3),
    INTAKESHOOT((1.0)/6),
    INTAKERIGHT(0.5),
    INTAKELEFT((5.0)/6);

    private final double absolutePosition;

    ColorSensorPosition(double absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public double getAbsolutePosition() {
        return absolutePosition;
    }
}
