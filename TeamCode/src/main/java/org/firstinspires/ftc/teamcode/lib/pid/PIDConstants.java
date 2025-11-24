package org.firstinspires.ftc.teamcode.lib.pid;

public class PIDConstants {
    private final double kp;
    private final double ki;
    private final double kd;

    public PIDConstants(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

    public double getKp() {
        return kp;
    }

    public double getKi() {
        return ki;
    }

    public double getKd() {
        return kd;
    }

}
