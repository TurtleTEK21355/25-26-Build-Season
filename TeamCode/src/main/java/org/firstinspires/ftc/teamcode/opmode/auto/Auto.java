package org.firstinspires.ftc.teamcode.opmode.auto;

import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.commands.GetMotifCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.commands.RotatePIDCommand;
import org.firstinspires.ftc.teamcode.commands.SetCarouselPositionCommand;
import org.firstinspires.ftc.teamcode.commands.SetIntakePowerCommand;
import org.firstinspires.ftc.teamcode.commands.ShootAllArtifactsCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;

@Configurable
public class Auto extends StateAutoOpMode {

    public static double SPEED = 0.5;

    // X positions
    public static double SHOOT_X = 12.0;
    public static double INTAKE_1_X = 44.0;
    public static double INTAKE_2_X = 48.0;
    public static double INTAKE_3_X = 52.0;

    // Y positions
    public static double START_Y = 60.0;
    public static double SHOOT_Y = 11.0;
    public static double ROW_2_Y = -12.0;
    public static double ROW_3_Y = -35.0;

    // Headings
    public static double START_H = 0.0;
    public static double SHOOT_H = 45.0;
    public static double INTAKE_H = 90.0;

    // Intake power
    public static double INTAKE_ON_POWER = 1.0;
    public static double INTAKE_OFF_POWER = 0.0;

    public static Motif currentMotif = Motif.PPG;

    private final AutoStep[] steps = AutoStep.values();

    @Override
    public void commands() {
        for (AutoStep step : steps) {
            addCommand(buildCommandForStep(step));
        }
    }

    private Command buildCommandForStep(AutoStep step) {
        switch (step) {
            case MOVE_TO_POS_1:
                return new MovePIDEncoderCommand(START_Y, SHOOT_Y, SPEED, robot.getDrivetrain());
            case DETECT_MOTIF_2:
                return new GetMotifCommand(robot.getLimelight());
            case ROTATE_ROBOT_3:
                return new RotatePIDCommand(START_H, SHOOT_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_4:
                return new ShootAllArtifactsCommand(robot.getShooterSystem(), currentMotif);
            case ROTATE_CAROUSEL_5:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_0, robot.getShooterSystem());
            case ROTATE_ROBOT_6:
                return new RotatePIDCommand(START_H, INTAKE_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case SET_INTAKE_POWER_7:
                return new SetIntakePowerCommand(INTAKE_ON_POWER, robot.getShooterSystem());
            case MOVE_TO_POS_8:
                return new MovePIDEncoderCommand(SHOOT_X, INTAKE_1_X, SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_9:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_1, robot.getShooterSystem());
            case MOVE_TO_POS_10:
                return new MovePIDEncoderCommand(INTAKE_1_X, INTAKE_2_X, SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_11:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem());
            case MOVE_TO_POS_12:
                return new MovePIDEncoderCommand(INTAKE_2_X, INTAKE_3_X, SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_13:
                return new MovePIDEncoderCommand(INTAKE_3_X, SHOOT_X, SPEED, robot.getDrivetrain());
            case SET_INTAKE_POWER_14:
                return new SetIntakePowerCommand(INTAKE_OFF_POWER, robot.getShooterSystem());
            case ROTATE_ROBOT_15:
                return new RotatePIDCommand(INTAKE_H, START_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_16:
                return new ShootAllArtifactsCommand(robot.getShooterSystem(), currentMotif);
            case ROTATE_CAROUSEL_17:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_0, robot.getShooterSystem());
            case ROTATE_ROBOT_18:
                return new RotatePIDCommand(SHOOT_H, START_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case MOVE_TO_POS_19:
                return new MovePIDEncoderCommand(SHOOT_Y, ROW_2_Y, SPEED, robot.getDrivetrain());
            case ROTATE_ROBOT_20:
                return new RotatePIDCommand(START_H, INTAKE_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case SET_INTAKE_POWER_21:
                return new SetIntakePowerCommand(INTAKE_ON_POWER, robot.getShooterSystem());
            case MOVE_TO_POS_22:
                return new MovePIDEncoderCommand(SHOOT_X, INTAKE_1_X, SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_23:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_1, robot.getShooterSystem());
            case MOVE_TO_POS_24:
                return new MovePIDEncoderCommand(INTAKE_1_X, INTAKE_2_X, SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_25:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem());
            case MOVE_TO_POS_26:
                return new MovePIDEncoderCommand(INTAKE_2_X, INTAKE_3_X, SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_27:
                return new MovePIDEncoderCommand(INTAKE_3_X, SHOOT_X, SPEED, robot.getDrivetrain());
            case SET_INTAKE_POWER_28:
                return new SetIntakePowerCommand(INTAKE_OFF_POWER, robot.getShooterSystem());
            case ROTATE_ROBOT_29:
                return new RotatePIDCommand(INTAKE_H, START_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case MOVE_TO_POS_30:
                return new MovePIDEncoderCommand(ROW_2_Y, SHOOT_Y, SPEED, robot.getDrivetrain());
            case ROTATE_ROBOT_31:
                return new RotatePIDCommand(START_H, SHOOT_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_32:
                return new ShootAllArtifactsCommand(robot.getShooterSystem(), currentMotif);
            case ROTATE_CAROUSEL_33:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_0, robot.getShooterSystem());
            case ROTATE_ROBOT_34:
                return new RotatePIDCommand(SHOOT_H, START_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case MOVE_TO_POS_35:
                return new MovePIDEncoderCommand(SHOOT_Y, ROW_3_Y, SPEED, robot.getDrivetrain());
            case ROTATE_ROBOT_36:
                return new RotatePIDCommand(SHOOT_H, INTAKE_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case SET_INTAKE_POWER_37:
                return new SetIntakePowerCommand(INTAKE_ON_POWER, robot.getShooterSystem());
            case MOVE_TO_POS_38:
                return new MovePIDEncoderCommand(SHOOT_X, INTAKE_1_X, SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_39:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_1, robot.getShooterSystem());
            case MOVE_TO_POS_40:
                return new MovePIDEncoderCommand(INTAKE_1_X, INTAKE_2_X, SPEED, robot.getDrivetrain());
            case ROTATE_CAROUSEL_41:
                return new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem());
            case MOVE_TO_POS_42:
                return new MovePIDEncoderCommand(INTAKE_2_X, INTAKE_3_X, SPEED, robot.getDrivetrain());
            case MOVE_TO_POS_43:
                return new MovePIDEncoderCommand(INTAKE_3_X, SHOOT_X, SPEED, robot.getDrivetrain());
            case SET_INTAKE_POWER_44:
                return new SetIntakePowerCommand(INTAKE_OFF_POWER, robot.getShooterSystem());
            case ROTATE_ROBOT_45:
                return new RotatePIDCommand(INTAKE_H, START_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case MOVE_TO_POS_46:
                return new MovePIDEncoderCommand(ROW_3_Y, SHOOT_Y, SPEED, robot.getDrivetrain());
            case ROTATE_ROBOT_47:
                return new RotatePIDCommand(START_H, SHOOT_H, SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_48:
                return new ShootAllArtifactsCommand(robot.getShooterSystem(), currentMotif);
            default:
                return new TimerCommand(0);
        }
    }
}
