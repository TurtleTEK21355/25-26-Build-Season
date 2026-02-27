package org.firstinspires.ftc.teamcode.lib.math;

public class ShootMath {

    //gravity might be negative.
    public static double velocity(double length) {

        double height = 27;
        double gravity = 386.09;
        double theta = Math.atan(2*height / length);
        double counter = 0;

        if(theta > 45 || theta < 20) {
            double fixedTheta = ShootMath.fixTheta(length, theta, counter);
            theta = fixedTheta;
        }
        
        double velocityCounter = 0;
        double fixedVelocity = Math.sqrt(2*gravity*height)/Math.sin(theta);
        if (fixedVelocity > 1700 || theta > 45 || theta < 20) {
            fixedVelocity = fixVelocity(theta, velocityCounter);
        }

        return fixedVelocity;

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
    public static double fixVelocity(double theta, double velocityCounter) {
        double height = 27;
        double gravity = 386.09;
        if (velocityCounter > 10) {
            return 1500;
        }
        double fixedVelocity = Math.sqrt(2*gravity*height)/Math.sin(theta);;
        if (fixedVelocity > 1700) {
            theta = theta - 5;
        }
        else if (theta < 20) {
            theta = theta + 5;
            velocityCounter += 1;
            fixVelocity(theta, velocityCounter);
        }
        return theta;

    }
}
