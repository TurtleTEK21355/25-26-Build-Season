package org.firstinspires.ftc.teamcode.lib.pid;

public class PIDControllerHeading extends PIDControllerSpeedLimit{

    public PIDControllerHeading(PIDConstants pidConstants, double target, double tolerance, double speed) {
        super(pidConstants, target, tolerance, speed);
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

        return -super.calculate(current);
    }
}
