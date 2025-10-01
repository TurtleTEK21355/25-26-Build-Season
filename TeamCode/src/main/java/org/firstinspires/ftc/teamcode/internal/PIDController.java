package org.firstinspires.ftc.teamcode.internal;

public class PIDController {
    private double kp;
    private double ki;
    private double kd;
    private double target;
    private double error;
    private double previousError = 0;
    private double integral = 0;


    PIDController(double kp, double ki, double kd, double target){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.target = target;
    }

    public double doPID(double current){
        error = target - current;
        double proportial = error;
        integral += error;
        double derivative = error - previousError;
        previousError = error;
        return proportial * kp + integral * ki + derivative * kd;
    }

}
