package org.firstinspires.ftc.teamcode.physicaldata;

public enum CarouselPosition {
    CSSHOOT(0.02),
    CSLEFT(0.67),
    CSRIGHT(0.34),
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
