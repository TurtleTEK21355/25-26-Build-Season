package org.firstinspires.ftc.teamcode.internal;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

public class MATH {
    double GRAVITY = 772.18; //Inches per second squared
    double height;
    //TODO DO THIS CONST LATER

    public double calculate(int THETA, double length) {
         return Math.sqrt((-GRAVITY*length*length)/(2*cos(THETA)*cos(THETA)*(height - length * tan(THETA))));
    }


}
