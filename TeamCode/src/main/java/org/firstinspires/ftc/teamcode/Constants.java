package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;

@Configurable
public class Constants {
    public static double angularScalar = 0.997;

    public static double otosPhysicalOffsetInchesX = -1.57;
    public static double otosPhysicalOffsetInchesY = -1.42;
    public static double otosPhysicalOffsetDegreesH = 0;

    public static Pose2D getPhysicalOffset(){
        return new Pose2D(otosPhysicalOffsetInchesX, otosPhysicalOffsetInchesY, otosPhysicalOffsetDegreesH);
    }

    public static double drivetrainLinearKp = 0.08;
    public static double drivetrainLinearKi = 0;
    public static double drivetrainLinearKd = 0;

    public static PIDConstants getLinearPIDConstants() {
        return new PIDConstants(drivetrainLinearKp, drivetrainLinearKi, drivetrainLinearKd);
    }

    public static double drivetrainAngularKp = 0.04;
    public static double drivetrainAngularKi = 0;
    public static double drivetrainAngularKd = 0;

    public static PIDConstants getAngularPIDConstants() {
        return new PIDConstants(drivetrainAngularKp, drivetrainAngularKi, drivetrainAngularKd);
    }

    public static double pidToleranceX = 2;
    public static double pidToleranceY = 2;
    public static double pidToleranceH = 2.5;

    public static Pose2D getPIDTolerance(){
        return new Pose2D(pidToleranceX, pidToleranceY, pidToleranceH);
    }

}
