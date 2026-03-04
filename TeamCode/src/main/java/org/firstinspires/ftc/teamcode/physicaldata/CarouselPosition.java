package org.firstinspires.ftc.teamcode.physicaldata;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;

public enum CarouselPosition {
    UNSET(Double.NaN, false, false),
    SHOOT_SLOT_0(Constants.shootSlot0,false, true), //slot 0 is the slot closest to servo position 0
    SHOOT_SLOT_1(Constants.shootSlot1,false, true),
    SHOOT_SLOT_2(Constants.shootSlot2,false, true),
    INTAKE_SLOT_0(Constants.intakeSlot0,true, false),
    INTAKE_SLOT_1(Constants.intakeSlot1,true, false),
    INTAKE_SLOT_2(Constants.intakeSlot2,true, false);

    private final double servoPosition;
    private final boolean canIntake;
    private final boolean canShoot;

    CarouselPosition(double servoPosition, boolean canIntake, boolean canShoot) {
        this.servoPosition = servoPosition;
        this.canIntake = canIntake;
        this.canShoot = canShoot;
    }

    public double getServoPosition() {
        return servoPosition;
    }

    public boolean getCanShoot() {
        return canShoot;
    }

    public boolean getCanIntake() {
        return canIntake;
    }

    public static CarouselPosition nextShootPosition(CarouselPosition position) {
        switch(position) {
            case UNSET:
            case INTAKE_SLOT_0:
                return SHOOT_SLOT_0; //if you are unset, go to slot 0
            case SHOOT_SLOT_0:
            case INTAKE_SLOT_1:
                return SHOOT_SLOT_1;
            case SHOOT_SLOT_1:
            case SHOOT_SLOT_2:
            case INTAKE_SLOT_2:
                return SHOOT_SLOT_2;
            default:
                return UNSET;
        }
    }

    public static CarouselPosition nextIntakePosition(CarouselPosition position) {
        switch(position) {
            case UNSET:
            case SHOOT_SLOT_0:
                return INTAKE_SLOT_0;
            case INTAKE_SLOT_0:
            case SHOOT_SLOT_1:
                return INTAKE_SLOT_1;
            case INTAKE_SLOT_1:
            case INTAKE_SLOT_2:
            case SHOOT_SLOT_2:
                return INTAKE_SLOT_2;
            default:
                return UNSET;
        }
    }

    public static CarouselPosition previousIntakePosition(CarouselPosition position) {
        switch(position) {
            case UNSET:
            case SHOOT_SLOT_0:
            case INTAKE_SLOT_0:
            case INTAKE_SLOT_1:
                return INTAKE_SLOT_0;
            case INTAKE_SLOT_2:
            case SHOOT_SLOT_1:
                return INTAKE_SLOT_1;
            case SHOOT_SLOT_2:
                return INTAKE_SLOT_2;
            default:
                return UNSET;
        }
    }
}
