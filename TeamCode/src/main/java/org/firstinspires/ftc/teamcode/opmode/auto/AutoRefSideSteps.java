package org.firstinspires.ftc.teamcode.opmode.auto;

public enum AutoRefSideSteps {
    MOVE_TO_POS_1,
    DETECT_MOTIF_2,
    ROTATE_ROBOT_3,
    SHOOT_ALL_4,
    ROTATE_CAROUSEL_5,
    ROTATE_ROBOT_6,
    SET_INTAKE_POWER_7,
    MOVE_TO_POS_8,
    ROTATE_CAROUSEL_9,
    MOVE_TO_POS_10,
    ROTATE_CAROUSEL_11,
    MOVE_TO_POS_12,
    MOVE_TO_POS_13,
    SET_INTAKE_POWER_14,
    ROTATE_ROBOT_15,
    SHOOT_ALL_16,
    ROTATE_CAROUSEL_17,
    ROTATE_ROBOT_18,
    MOVE_TO_POS_19,
    ROTATE_ROBOT_20,
    SET_INTAKE_POWER_21,
    MOVE_TO_POS_22,
    ROTATE_CAROUSEL_23,
    MOVE_TO_POS_24,
    ROTATE_CAROUSEL_25,
    MOVE_TO_POS_26,
    MOVE_TO_POS_27,
    SET_INTAKE_POWER_28,
    ROTATE_ROBOT_29,
    MOVE_TO_POS_30,
    ROTATE_ROBOT_31,
    SHOOT_ALL_32,
    ROTATE_CAROUSEL_33,
    ROTATE_ROBOT_34,
    MOVE_TO_POS_35,
    ROTATE_ROBOT_36,
    SET_INTAKE_POWER_37,
    MOVE_TO_POS_38,
    ROTATE_CAROUSEL_39,
    MOVE_TO_POS_40,
    ROTATE_CAROUSEL_41,
    MOVE_TO_POS_42,
    MOVE_TO_POS_43,
    SET_INTAKE_POWER_44,
    ROTATE_ROBOT_45,
    MOVE_TO_POS_46,
    ROTATE_ROBOT_47,
    SHOOT_ALL_48;

    private static Command buildCommandForStep(AutoRefSideSteps step, AutoRefSide opMode) {

        switch (step) {
            case MOVE_TO_POS_1:
                return new MovePIDEncoderCommand(opMode.getStartY(), opMode.getShootY(), opMode.SPEED, robot.getDrivetrain());
            case DETECT_MOTIF_2:
                return new GetMotifCommand(robot.getLimelight());
            case ROTATE_ROBOT_3:
                return new RotatePIDCommand(opMode.getStartHeading(), opMode.getShootHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_4:
                return new Shoot3Command(robot.getShooterSystem());
            case ROTATE_CAROUSEL_5:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_0, robot.getShooterSystem());
            case ROTATE_ROBOT_6:
                return new RotatePIDCommand(opMode.getStartHeading(), opMode.getIntakeHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SET_INTAKE_POWER_7:
                return new SetIntakePowerCommand(opMode.INTAKE_ON_POWER, robot.getShooterSystem());
            case MOVE_TO_POS_8:
                return new MovePIDEncoderCommand(opMode.getShootX(), opMode.getIntake1X(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_9:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_1, robot.getShooterSystem());
            case MOVE_TO_POS_10:
                return new MovePIDEncoderCommand(opMode.getIntake1X(), opMode.getIntake2X(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_11:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem());
            case MOVE_TO_POS_12:
                return new MovePIDEncoderCommand(opMode.getIntake2X(), opMode.getIntake3X(), opMode.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_13:
                return new MovePIDEncoderCommand(opMode.getIntake3X(), opMode.getShootX(), opMode.SPEED, robot.getDrivetrain());
            case SET_INTAKE_POWER_14:
                return new SetIntakePowerCommand(opMode.INTAKE_OFF_POWER, robot.getShooterSystem());
            case ROTATE_ROBOT_15:
                return new RotatePIDCommand(opMode.getIntakeHeading(), opMode.getStartHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_16:
                return new Shoot3Command(robot.getShooterSystem());
            case ROTATE_CAROUSEL_17:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_0, robot.getShooterSystem());
            case ROTATE_ROBOT_18:
                return new RotatePIDCommand(opMode.getShootHeading(), opMode.getStartHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case MOVE_TO_POS_19:
                return new MovePIDEncoderCommand(opMode.getShootY(), opMode.getRow2Y(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_ROBOT_20:
                return new RotatePIDCommand(opMode.getStartHeading(), opMode.getIntakeHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SET_INTAKE_POWER_21:
                return new SetIntakePowerCommand(opMode.INTAKE_ON_POWER, robot.getShooterSystem());
            case MOVE_TO_POS_22:
                return new MovePIDEncoderCommand(opMode.getShootX(), opMode.getIntake1X(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_23:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_1, robot.getShooterSystem());
            case MOVE_TO_POS_24:
                return new MovePIDEncoderCommand(opMode.getIntake1X(), opMode.getIntake2X(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_25:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem());
            case MOVE_TO_POS_26:
                return new MovePIDEncoderCommand(opMode.getIntake2X(), opMode.getIntake3X(), opMode.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_27:
                return new MovePIDEncoderCommand(opMode.getIntake3X(), opMode.getShootX(), opMode.SPEED, robot.getDrivetrain());
            case SET_INTAKE_POWER_28:
                return new SetIntakePowerCommand(opMode.INTAKE_OFF_POWER, robot.getShooterSystem());
            case ROTATE_ROBOT_29:
                return new RotatePIDCommand(opMode.getIntakeHeading(), opMode.getStartHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case MOVE_TO_POS_30:
                return new MovePIDEncoderCommand(opMode.getRow2Y(), opMode.getShootY(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_ROBOT_31:
                return new RotatePIDCommand(opMode.getStartHeading(), opMode.getShootHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_32:
                return new Shoot3Command(robot.getShooterSystem());
            case ROTATE_CAROUSEL_33:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_0, robot.getShooterSystem());
            case ROTATE_ROBOT_34:
                return new RotatePIDCommand(opMode.getShootHeading(), opMode.getStartHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case MOVE_TO_POS_35:
                return new MovePIDEncoderCommand(opMode.getShootY(), opMode.getRow3Y(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_ROBOT_36:
                return new RotatePIDCommand(opMode.getShootHeading(), opMode.getIntakeHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SET_INTAKE_POWER_37:
                return new SetIntakePowerCommand(opMode.INTAKE_ON_POWER, robot.getShooterSystem());
            case MOVE_TO_POS_38:
                return new MovePIDEncoderCommand(opMode.getShootX(), opMode.getIntake1X(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_39:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_1, robot.getShooterSystem());
            case MOVE_TO_POS_40:
                return new MovePIDEncoderCommand(opMode.getIntake1X(), opMode.getIntake2X(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_41:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem());
            case MOVE_TO_POS_42:
                return new MovePIDEncoderCommand(opMode.getIntake2X(), opMode.getIntake3X(), opMode.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_43:
                return new MovePIDEncoderCommand(opMode.getIntake3X(), opMode.getShootX(), opMode.SPEED, robot.getDrivetrain());
            case SET_INTAKE_POWER_44:
                return new SetIntakePowerCommand(opMode.INTAKE_OFF_POWER, robot.getShooterSystem());
            case ROTATE_ROBOT_45:
                return new RotatePIDCommand(opMode.getIntakeHeading(), opMode.getStartHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case MOVE_TO_POS_46:
                return new MovePIDEncoderCommand(opMode.getRow3Y(), opMode.getShootY(), opMode.SPEED, robot.getDrivetrain());
            case ROTATE_ROBOT_47:
                return new RotatePIDCommand(opMode.getStartHeading(), opMode.getShootHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_48:
                return new Shoot3Command(robot.getShooterSystem());
            default:
                return new TimerCommand(0);
        }
    }
}
