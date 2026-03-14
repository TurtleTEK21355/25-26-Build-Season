package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoAudienceSideBlue", group = "auto")
public class AutoAudienceSideBlue extends AutoAudienceSide {

    @Override
    protected void configureSide() {

        START_H = 0.0;
        SHOOT_H = 33.0;
        INTAKE_H = 0.0;// not used

        START_Y = -60.0;

        SHOOT_X = 0.0; //not used.
        SHOOT_Y = -50.0;
        ROW_2_Y = -12.0;
        ROW_3_Y = -35.0;
        INTAKE_1_X = 0.0;// not used
        INTAKE_2_X = 0.0;//not used
        INTAKE_3_X = 0.0;//not used
    }
}
