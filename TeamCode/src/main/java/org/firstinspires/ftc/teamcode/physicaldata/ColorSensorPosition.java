package org.firstinspires.ftc.teamcode.physicaldata;

public enum ColorSensorPosition {
    SHOOT(1.0/3),
    LEFT(2.0/3),
    RIGHT(1.0);

    private final double relativePosition;

    ColorSensorPosition(double relativePosition) {
        this.relativePosition = relativePosition;
    }

    public double getRelativePosition() {
        return relativePosition;
    }
}
