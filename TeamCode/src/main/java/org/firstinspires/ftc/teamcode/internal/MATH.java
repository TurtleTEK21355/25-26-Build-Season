package org.firstinspires.ftc.teamcode.internal;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

public class MATH {
    double GRAVITY = 772.18; //Inches per second squared
    double HEIGHT = 40; //inches tall

    public double calculate(int THETA, double length) {
         return Math.sqrt((-GRAVITY*Math.pow(length, 2))/(2*Math.pow(cos(THETA), 2)*(HEIGHT - length * tan(THETA))));
    }


}
