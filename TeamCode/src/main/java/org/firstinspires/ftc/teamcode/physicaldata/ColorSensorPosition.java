package org.firstinspires.ftc.teamcode.physicaldata;

public enum ColorSensorPosition {
    SHOOT(0),
    LEFT(-1),
    RIGHT(1);

    private final double relativePosition;

    ColorSensorPosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    public double getRelativePosition() {
        return relativePosition;
    }
}
