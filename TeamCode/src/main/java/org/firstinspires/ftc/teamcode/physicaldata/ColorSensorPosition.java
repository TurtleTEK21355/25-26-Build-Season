package org.firstinspires.ftc.teamcode.physicaldata;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

public enum ColorSensorPosition {
    SHOOT(0),
    LEFT(0.32),
    RIGHT(0.65);

    private final double absolutePosition;

    ColorSensorPosition(double relativePosition) {
        this.absolutePosition = relativePosition;
    }

    public double getAbsolutePosition() {
        return absolutePosition;
    }
}
