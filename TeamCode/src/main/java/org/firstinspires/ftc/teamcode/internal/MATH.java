package org.firstinspires.ftc.teamcode.internal;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

public class MATH {
    static double GRAVITY = 386.09; //Inches per second squared
    static double HEIGHT = 40; //inches tall

    static double THETA = 1.13446401; //Ramp Angle in Radians

    static double maxSpeed = 388.590;

    public static double calculate(double length) {
         return Math.sqrt((-GRAVITY*Math.pow(length, 2))/(2*Math.pow(cos(THETA), 2)*(HEIGHT - length * tan(THETA))));
    }

}
