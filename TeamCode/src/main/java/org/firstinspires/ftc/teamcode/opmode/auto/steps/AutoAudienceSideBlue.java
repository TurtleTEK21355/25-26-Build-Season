package org.firstinspires.ftc.teamcode.opmode.auto.steps;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Configurable
@Autonomous(name="AutoAudienceSideBlue", group = "auto")
public class AutoAudienceSideBlue extends AutoAudienceSide {
    
    public static double ab_START_H = 0.0;
    public static double ab_SHOOT_H = 33.0;
    public static double ab_INTAKE_H = 0.0;// not used

    public static double ab_START_Y = -60.0;

    public static double ab_SHOOT_X = 0.0; //not used.
    public static double ab_SHOOT_Y = -50.0;
    public static double ab_ROW_2_Y = -12.0;
    public static double ab_ROW_3_Y = -35.0;
    public static double ab_INTAKE_1_X = 0.0;// not used
    public static double ab_INTAKE_2_X = 0.0;//not used
    public static double ab_INTAKE_3_X = 0.0;//not used

    @Override
    protected void configureSide() {

        START_H = ab_START_H;
        SHOOT_H = ab_SHOOT_H;
        INTAKE_H = ab_INTAKE_H;

        START_Y = ab_START_Y;

        SHOOT_X = ab_SHOOT_X;
        SHOOT_Y = ab_SHOOT_Y;
        ROW_2_Y = ab_ROW_2_Y;
        ROW_3_Y = ab_ROW_3_Y;
        INTAKE_1_X = ab_INTAKE_1_X;
        INTAKE_2_X = ab_INTAKE_2_X;
        INTAKE_3_X = ab_INTAKE_3_X;
    }
}
