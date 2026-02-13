package org.firstinspires.ftc.teamcode.lib.math;

public class ShootMath {
    double height = 31;
    double gravity = 386.09;

    //gravity might be negative.
    public double velocity(double length) {
        double theta = Math.atan(2*height / length);

        double velocity = Math.sqrt(2*gravity*height)/Math.sin(theta);

        return velocity;
    }
}
