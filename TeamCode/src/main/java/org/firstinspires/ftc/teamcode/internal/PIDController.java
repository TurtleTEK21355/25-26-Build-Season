package org.firstinspires.ftc.teamcode.internal;

public class PIDController {

    PIDConstants pidConstants;
    double target;
    double error;
    double tolerance;
    double previousError = 0;
    double proportional;
    double integral = 0;
    double derivative;


    PIDController(PIDConstants pidConstants, double target, double tolerance){
        this.pidConstants = pidConstants;
        this.target = target;
        this.tolerance = tolerance;
    }

    public double calculate(double current){
        error = target - current;
        proportional = error;
        integral += error;
        derivative = error - previousError;
        previousError = error;
        return proportional * pidConstants.getKp() + integral * pidConstants.getKi() + derivative * pidConstants.getKd();

    }

    /**
     * tells if the absolute distance from target is greater than the tolerance
     * @param current the current position
     */
    public boolean atTarget(double current){
        double distance = target - current;
        return (Math.abs(distance) <= tolerance);

    }

}
