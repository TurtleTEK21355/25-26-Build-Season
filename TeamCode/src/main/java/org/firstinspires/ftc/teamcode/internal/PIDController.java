package org.firstinspires.ftc.teamcode.internal;

public class PIDController {
    double kp;
    double ki;
    double kd;
    double target;
    double error;
    double tolerance;

    double previousError = 0;
    double integral = 0;


    PIDController(double kp, double ki, double kd, double target, double tolerance){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.target = target;
        this.tolerance = tolerance;
    }

    public double calculate(double current){
        error = target - current;
        double proportional = error;
        integral += error;
        double derivative = error - previousError;
        previousError = error;
        return proportional * kp + integral * ki + derivative * kd;

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
