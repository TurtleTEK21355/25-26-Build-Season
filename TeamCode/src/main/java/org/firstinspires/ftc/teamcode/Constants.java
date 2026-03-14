package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;

@Configurable
public class Constants {
    public static int artifactLiftUpperTolerance = 20;
    public static int artifactLiftLowerTolerance = 20;
    public static int artifactLiftTimeoutMilliseconds = 500;
    public static double artifactLiftPower = 0.8;

    public static int shootCloseVelocity = 1100;
    public static double shootCloseAngle = 40;

    public static int shootFarVelocity = 1400;
    public static double shootFarAngle = 35;

    public static int autoShootFarVelocity = 1350;
    public static int autoShootCloseVelocity = 1000;

    public static int carouselMoveOneTimer = 1200;

    public static double shootSlot0 = 0.32; // 0.35
    public static double shootSlot1 = 0.66; // 0.67
    public static double shootSlot2 = 1.0; // 1.0

    public static double intakeSlot0 = 0.0; // 0.0
    public static double intakeSlot1 = 0.59; // 0.62
    public static double intakeSlot2 = 0.92; //0.92

    public static double angularScalar = 0.997;

    public static double otosPhysicalOffsetInchesX = -1.57;
    public static double otosPhysicalOffsetInchesY = -1.42;
    public static double otosPhysicalOffsetDegreesH = 0;
    public static Pose2D getPhysicalOffset(){
        return new Pose2D(otosPhysicalOffsetInchesX, otosPhysicalOffsetInchesY, otosPhysicalOffsetDegreesH);
    }

    public static double inchesToEncoderDrivetrain = 41.8013539662;

    public static double drivetrainLinearKp = 0.08;
    public static double drivetrainLinearKi = 0;
    public static double drivetrainLinearKd = 0;
    public static PIDConstants getLinearPIDConstants() {
        return new PIDConstants(drivetrainLinearKp, drivetrainLinearKi, drivetrainLinearKd);
    }

    public static double drivetrainEncoderLinearKp = 0.08/inchesToEncoderDrivetrain;
    public static double drivetrainEncoderLinearKi = 0;
    public static double drivetrainEncoderLinearKd = 0;
    public static PIDConstants getEncoderLinearPIDConstants() {
        return new PIDConstants(drivetrainEncoderLinearKp, drivetrainEncoderLinearKi, drivetrainEncoderLinearKd);
    }

    public static double drivetrainAngularKp = 0.08;
    public static double drivetrainAngularKi = 0;
    public static double drivetrainAngularKd = 0;
    public static PIDConstants getAngularPIDConstants() {
        return new PIDConstants(drivetrainAngularKp, drivetrainAngularKi, drivetrainAngularKd);
    }

    public static double pidToleranceX = 2;
    public static double pidToleranceY = 2;
    public static double pidToleranceH = 0.2;
    public static Pose2D getPIDTolerance(){
        return new Pose2D(pidToleranceX, pidToleranceY, pidToleranceH);
    }
    public static int encoderPIDTolerance = 200;

    public static double linearSpeed = 0.5;
    public static double angularSpeed = 0.5;

    public static double cameraAngleOffsetClose = 5;
    public static double cameraAngleOffsetFar = 2.5;

    public static double drivetrainExponentIndex = 3; //change in configurables to change how speed ramps up (ex. quadratic, cubic, etc.)

}
