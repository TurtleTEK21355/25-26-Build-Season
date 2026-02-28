package org.firstinspires.ftc.teamcode.physicaldata;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

public enum ColorSensorPosition {
    SHOOT(0.01),
    LEFT(0.66),
    RIGHT(0.33),
    INTAKESHOOT(0.17),
    INTAKERIGHT(0.5),
    INTAKELEFT(0.83);

    private final double absolutePosition;

    ColorSensorPosition(double absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public double getAbsolutePosition() {
        return absolutePosition;
    }
}
