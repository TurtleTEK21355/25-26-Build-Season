package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoJudgeSideBlue", group = "auto")
public class AutoJudgeSideBlue extends AutoJudgeSide {

    @Override
    protected void configureSide() {

        // Field positions for BLUE side
        START_H = 0.0;
        SHOOT_H = 45.0;
        INTAKE_H = 90.0;

        START_Y = 60.0;

        SHOOT_X = 12.0;
        SHOOT_Y = 11.0;
        
        ROW_2_Y = -12.0;
        ROW_3_Y = -35.0;

        INTAKE_1_X = 44.0;
        INTAKE_2_X = 48.0;
        INTAKE_3_X = 52.0;
    }
}
