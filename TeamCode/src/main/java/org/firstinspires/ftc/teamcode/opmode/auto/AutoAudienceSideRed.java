package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Configurable
@Autonomous(name="AutoAudienceSideRed", group = "auto")
public class AutoAudienceSideRed extends AutoAudienceSide {
    public static double ar_START_H = 0.0;
    public static double ar_SHOOT_H = -33.0;
    public static double ar_INTAKE_H = 0.0;// not used
    
    public static double ar_START_Y = -60.0;
    
    public static double ar_SHOOT_X = -12.0;
    public static double ar_SHOOT_Y = -50.0;
    
    public static double ar_ROW_2_Y = -12.0;
    public static double ar_ROW_3_Y = -35.0;
    
    public static double ar_INTAKE_1_X = 0.0;// not used
    public static double ar_INTAKE_2_X = 0.0;//not used
    public static double ar_INTAKE_3_X = 0.0;//not used
    
    @Override
    protected void configureSide() {

        START_H = ar_START_H;
        SHOOT_H = ar_SHOOT_H;
        INTAKE_H = ar_INTAKE_H;

        START_Y = ar_START_Y;

        SHOOT_X = ar_SHOOT_X;
        SHOOT_Y = ar_SHOOT_Y;
        ROW_2_Y = ar_ROW_2_Y;
        ROW_3_Y = ar_ROW_3_Y;
        INTAKE_1_X = ar_INTAKE_1_X;
        INTAKE_2_X = ar_INTAKE_2_X;
        INTAKE_3_X = ar_INTAKE_3_X;
    }
}
