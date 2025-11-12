package org.firstinspires.ftc.teamcode.internal;

public class PIDControllerHeading extends PIDControllerSpeedLimit{

    PIDControllerHeading(double kp, double ki, double kd, double target, double tolerance, double speed) {
        super(kp, ki, kd, target, tolerance, speed);
    }

    @Override
    public double calculate(double current) {
        if (target > 0) {
            if (target - current > 180) {
                target = target - 360;
            }
        } else {
            if (target - current < -180) {
                target = target + 360;
            }
        }

        return super.calculate(current);
    }
}
