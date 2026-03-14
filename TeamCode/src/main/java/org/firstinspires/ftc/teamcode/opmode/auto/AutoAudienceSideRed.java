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

        START_H = 0.0;
        SHOOT_H = -33.0;
        INTAKE_H = 0.0;// not used

        START_Y = -60.0;

        SHOOT_X = -12.0;
        SHOOT_Y = -50.0;
        
        ROW_2_Y = -12.0;
        ROW_3_Y = -35.0;

        INTAKE_1_X = 0.0;// not used
        INTAKE_2_X = 0.0;//not used
        INTAKE_3_X = 0.0;//not used
    }
}
