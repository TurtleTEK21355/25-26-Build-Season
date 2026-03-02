package org.firstinspires.ftc.teamcode.physicaldata;

public enum CarouselPosition {
    SHOOT_SLOT_0(0.02),
    SHOOT_SLOT_1(0.34),
    SHOOT_SLOT_2(0.67),
    INTAKE_SLOT_0(0.17),
    INTAKE_SLOT_1(0.5),
    INTAKE_SLOT_2(0.83);

    private final double position;

    CarouselPosition(double position) {
        this.position = position;
    }

    public double getPosition() {
        return position;
    }
}
