package org.firstinspires.ftc.teamcode.opmode.auto.steps;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Configurable
@Disabled
@Autonomous(name="AutoRefSideBlue", group = "auto")
public class AutoRefSideBlue extends AutoRefSide {
    // Field positions for BLUE side
    public static double rb_START_H = 0.0;
    public static double rb_SHOOT_H = 45.0;
    public static double rb_INTAKE_H = 90.0;
    
    public static double rb_START_Y = 60.0;
    
    public static double rb_SHOOT_X = 12.0;
    public static double rb_SHOOT_Y = 11.0;
    
    public static double rb_ROW_2_Y = -12.0;
    public static double rb_ROW_3_Y = -35.0;
    
    public static double rb_INTAKE_1_X = 44.0;
    public static double rb_INTAKE_2_X = 48.0;
    public static double rb_INTAKE_3_X = 52.0;
    @Override
    protected void configureSide() {

        START_H = rb_START_H;
        SHOOT_H = rb_SHOOT_H;
        INTAKE_H = rb_INTAKE_H;

        START_Y = rb_START_Y;

        SHOOT_X = rb_SHOOT_X;
        SHOOT_Y = rb_SHOOT_Y;
        ROW_2_Y = rb_ROW_2_Y;
        ROW_3_Y = rb_ROW_3_Y;
        INTAKE_1_X = rb_INTAKE_1_X;
        INTAKE_2_X = rb_INTAKE_2_X;
        INTAKE_3_X = rb_INTAKE_3_X;
       
    }
}
