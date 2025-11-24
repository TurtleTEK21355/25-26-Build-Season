package org.firstinspires.ftc.teamcode.lib.pid;

public class PIDControllerSpeedLimit extends PIDController{

    private final double speed;

    public PIDControllerSpeedLimit(PIDConstants pidConstants, double target, double tolerance, double speed) {
        super(pidConstants, target, tolerance);
        this.speed = speed;

    }

    @Override
    public double calculate(double current) {
        if (error > 0) {
            return Math.min(super.calculate(current), speed);
        } else {
            return Math.max(super.calculate(current), -speed);
        }
    }
}
