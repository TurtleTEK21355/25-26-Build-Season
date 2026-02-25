package org.firstinspires.ftc.teamcode.lib.math;

public class ShootMath {

    //gravity might be negative.
    public static double velocity(double length) {

        double height = 27;
        double gravity = 386.09;
        double theta = Math.atan(2*height / length)+5;
        double counter = 0;
        double fixedTheta = 936;

        if(theta > 45 || theta < 20) {
            fixedTheta = ShootMath.fixTheta(length, theta, counter);
        }

        if (fixedTheta!=936) {
            theta = fixedTheta;
        }

        double velocity = Math.sqrt(2*gravity*height)/Math.sin(theta);

        return velocity;

    }

    public static double fixTheta(double length, double theta, double counter) {
        double height = 27;

        if (counter > 10) {
            return 45;
        }
        else if (theta > 45) {
            theta = Math.atan(2*height / length)-5;
            counter += 1;
            fixTheta(length, theta, counter);
        }
        else if (theta < 20) {
            theta = Math.atan(2*height / length)+5;
            counter += 1;
            fixTheta(length, theta, counter);
        }
        return theta;

    }
}
