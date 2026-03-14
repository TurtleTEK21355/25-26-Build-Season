package org.firstinspires.ftc.teamcode.opmode.auto.steps;


import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.commands.RotatePIDCommand;
import org.firstinspires.ftc.teamcode.commands.Shoot3Command;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.commands.SetIntakePowerCommand;
import org.firstinspires.ftc.teamcode.commands.SetCarouselPositionCommand;
import org.firstinspires.ftc.teamcode.commands.GetMotifCommand;

import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;



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

    public static Command buildCommandForStep(AutoRefSideSteps step, AutoRefSide opMode, StateRobot robot) {

        switch (step) {
            case MOVE_TO_POS_1:
                return new MovePIDEncoderCommand(opMode.getStartY(), opMode.getShootY(), AutoRefSide.SPEED, robot.getDrivetrain());
            case DETECT_MOTIF_2:
                return new GetMotifCommand(robot.getLimelight());
            case SHOOT_ALL_4:
            case SHOOT_ALL_48:
            case SHOOT_ALL_32:
            case SHOOT_ALL_16:
                return new Shoot3Command(robot.getShooterSystem());
            case ROTATE_CAROUSEL_5:
            case ROTATE_CAROUSEL_33:
            case ROTATE_CAROUSEL_17:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_0, robot.getShooterSystem());
            case ROTATE_CAROUSEL_9:
            case ROTATE_CAROUSEL_39:
            case ROTATE_CAROUSEL_23:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_1, robot.getShooterSystem());
            case ROTATE_CAROUSEL_11:
            case ROTATE_CAROUSEL_41:
            case ROTATE_CAROUSEL_25:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem());
            case ROTATE_ROBOT_3:
            case ROTATE_ROBOT_47:
            case ROTATE_ROBOT_31:
                return new RotatePIDCommand(opMode.getStartHeading(), opMode.getShootHeading(), AutoRefSide.SPEED, robot.getDrivetrain(), robot.getIMU());
            case ROTATE_ROBOT_6:
            case ROTATE_ROBOT_36:
                return new RotatePIDCommand(opMode.getShootHeading(), opMode.getIntakeHeading(), AutoRefSide.SPEED, robot.getDrivetrain(), robot.getIMU());
            case ROTATE_ROBOT_15:
                return new RotatePIDCommand(opMode.getIntakeHeading(), opMode.getShootHeading(), AutoRefSide.SPEED, robot.getDrivetrain(), robot.getIMU());
            case ROTATE_ROBOT_18:
            case ROTATE_ROBOT_34:
                return new RotatePIDCommand(opMode.getShootHeading(), opMode.getStartHeading(), AutoRefSide.SPEED, robot.getDrivetrain(), robot.getIMU());
            case ROTATE_ROBOT_20:
                return new RotatePIDCommand(opMode.getStartHeading(), opMode.getIntakeHeading(), AutoRefSide.SPEED, robot.getDrivetrain(), robot.getIMU());
            case ROTATE_ROBOT_29:
            case ROTATE_ROBOT_45:
                return new RotatePIDCommand(opMode.getIntakeHeading(), opMode.getStartHeading(), AutoRefSide.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SET_INTAKE_POWER_7:
            case SET_INTAKE_POWER_37:
            case SET_INTAKE_POWER_21:
                return new SetIntakePowerCommand(AutoRefSide.INTAKE_ON_POWER, robot.getShooterSystem());
            case MOVE_TO_POS_8:
            case MOVE_TO_POS_38:
            case MOVE_TO_POS_22:
                return new MovePIDEncoderCommand(opMode.getShootX(), opMode.getIntake1X(), AutoRefSide.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_10:
            case MOVE_TO_POS_40:
            case MOVE_TO_POS_24:
                return new MovePIDEncoderCommand(opMode.getIntake1X(), opMode.getIntake2X(), AutoRefSide.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_12:
            case MOVE_TO_POS_42:
            case MOVE_TO_POS_26:
                return new MovePIDEncoderCommand(opMode.getIntake2X(), opMode.getIntake3X(), AutoRefSide.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_13:
            case MOVE_TO_POS_43:
            case MOVE_TO_POS_27:
                return new MovePIDEncoderCommand(opMode.getIntake3X(), opMode.getShootX(), AutoRefSide.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_19:
                return new MovePIDEncoderCommand(opMode.getShootY(), opMode.getRow2Y(), AutoRefSide.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_30:
                return new MovePIDEncoderCommand(opMode.getRow2Y(), opMode.getShootY(), AutoRefSide.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_35:
                return new MovePIDEncoderCommand(opMode.getShootY(), opMode.getRow3Y(), AutoRefSide.SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_46:
                return new MovePIDEncoderCommand(opMode.getRow3Y(), opMode.getShootY(), AutoRefSide.SPEED, robot.getDrivetrain());
            case SET_INTAKE_POWER_14:
            case SET_INTAKE_POWER_44:
            case SET_INTAKE_POWER_28:
                return new SetIntakePowerCommand(AutoRefSide.INTAKE_OFF_POWER, robot.getShooterSystem());
            default:
                return new TimerCommand(0);
        }
    }
}
