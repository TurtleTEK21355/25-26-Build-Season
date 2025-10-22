package org.firstinspires.ftc.teamcode.internal;

public class PIDControllerHeading extends PIDControllerSpeedLimit{

    PIDControllerHeading(double kp, double ki, double kd, double target, double tolerance, double speed) {
        super(kp, ki, kd, target, tolerance, speed);
    }

    private double realTarget = target % 360;

    @Override
    public double calculate(double current) {

    }
}
