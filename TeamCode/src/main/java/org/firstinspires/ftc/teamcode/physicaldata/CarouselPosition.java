package org.firstinspires.ftc.teamcode.physicaldata;

import org.firstinspires.ftc.teamcode.Constants;

public enum CarouselPosition {
    SHOOT_SLOT_0(Constants.shootSlot0),
    SHOOT_SLOT_1(Constants.shootSlot1),
    SHOOT_SLOT_2(Constants.shootSlot2),
    INTAKE_SLOT_0(Constants.intakeSlot0),
    INTAKE_SLOT_1(Constants.intakeSlot1),
    INTAKE_SLOT_2(Constants.intakeSlot2);

    private final double position;

    CarouselPosition(double position) {
        this.position = position;
    }

    public double getPosition() {
        return position;
    }
}
