package org.firstinspires.ftc.teamcode.physicaldata;

public enum ColorSensorPosition {
    SHOOT(0.25),
    LEFT(0.5),
    RIGHT(0.75);

    private final double relativePosition;

    ColorSensorPosition(double relativePosition) {
        this.relativePosition = relativePosition;
    }

    public double getRelativePosition() {
        return relativePosition;
    }
}
