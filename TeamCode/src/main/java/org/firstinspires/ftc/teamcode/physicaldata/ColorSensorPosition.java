package org.firstinspires.ftc.teamcode.physicaldata;

public enum ColorSensorPosition {
    SHOOT(0),
    LEFT(0.4),
    RIGHT(0.8);

    private final double relativePosition;

    ColorSensorPosition(double relativePosition) {
        this.relativePosition = relativePosition;
    }

    public double getRelativePosition() {
        return relativePosition;
    }
}
