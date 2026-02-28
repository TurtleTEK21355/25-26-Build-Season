package org.firstinspires.ftc.teamcode.physicaldata;

public enum CarouselPosition {
    CSSHOOT(0.01),
    CSLEFT(0.66),
    CSRIGHT(0.33),
    INTAKESHOOT(0.17),
    INTAKERIGHT(0.5),
    INTAKELEFT(0.83);

    private final double absolutePosition;

    CarouselPosition(double absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public double getAbsolutePosition() {
        return absolutePosition;
    }
}
