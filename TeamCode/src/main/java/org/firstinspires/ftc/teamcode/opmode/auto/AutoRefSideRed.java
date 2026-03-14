package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoRefSideRed", group = "auto")
public class AutoRefSideRed extends AutoRefSide {
    public static double rr_START_H = 0.0;
    public static double rr_SHOOT_H = -45.0;
    public static double rr_INTAKE_H = -90.0;
    
    public static double rr_START_Y = 60.0;
    
    public static double rr_SHOOT_X = -12.0;
    public static double rr_SHOOT_Y = 11.0;
    
    public static double rr_ROW_2_Y = -12.0;
    public static double rr_ROW_3_Y = -35.0;
    
    public static double rr_INTAKE_1_X = -44.0;
    public static double rr_INTAKE_2_X = -48.0;
    public static double rr_INTAKE_3_X = -52.0;
    @Override
    protected void configureSide() {
        START_H = rr_START_H;
        SHOOT_H = rr_SHOOT_H;
        INTAKE_H = rr_INTAKE_H;

        START_Y = rr_START_Y;

        SHOOT_X = rr_SHOOT_X;
        SHOOT_Y = rr_SHOOT_Y;
        ROW_2_Y = rr_ROW_2_Y;
        ROW_3_Y = rr_ROW_3_Y;
        INTAKE_1_X = rr_INTAKE_1_X;
        INTAKE_2_X = rr_INTAKE_2_X;
        INTAKE_3_X = rr_INTAKE_3_X;
    }
}
