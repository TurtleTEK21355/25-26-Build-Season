package org.firstinspires.ftc.teamcode.internal;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

public class ShootCommand extends Command{

    @Override
    public void loop() {

    }

    @Override
    public boolean isCompleted() {
        return false;
    }

//    public void autoShoot(double range) {
//        double timer = 0;
//        double power = (Math.sqrt((-GRAVITY*Math.pow(range, 2))/(2*Math.pow(cos(THETA), 2)*(HEIGHT - range * tan(THETA)))))/maxSpeed;
//        flyWheel.setPower(power);
//        while (flyWheel.getPower() < power-0.075);
//        hopper.openGate();
//        generalTimer.startTime();
//        hopper.setPower(1);
//        while(generalTimer.milliseconds()<1250);
//        generalTimer.reset();
//        hopper.setPower(0);
//        flyWheel.setPower(0);
//        hopper.closeGate();
//
//    }

}
