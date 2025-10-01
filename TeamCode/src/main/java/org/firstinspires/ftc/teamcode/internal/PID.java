package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import java.util.Timer;
import java.util.TimerTask;

public class PID {
    private SparkFunOTOS otosSensor;
    boolean PIDLoopActive = true;
    private double kp;
    private double ki;
    private double kd;
    private double kpTheta;
    private double kiTheta;
    private double kdTheta;
    private Pose2D PIDKill;
    public enum Pose2DComponent {X, Y, H}

    Pose2D prevError = new Pose2D(0,0,0);
    Pose2D integral = new Pose2D(0,0,0);

    boolean timerLock = false;
    Timer holdTimer = new Timer();
    TimerTask TurnOffPIDLoop = new TimerTask() {
        @Override
        public void run(){
            PIDLoopActive = false;
            holdTimer.cancel();
        }
    };



    public PID(SparkFunOTOS otosSensor, double kp, double ki, double kd, double kpTheta, double kiTheta, double kdTheta, Pose2D PIDKill){
        this.otosSensor = otosSensor;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kpTheta = kpTheta;
        this.kiTheta = kiTheta;
        this.kdTheta = kdTheta;
        this.PIDKill = PIDKill;
    }

    public Pose2D movePID(double targetX, double targetY, double targetH, double speed, int holdTime){

        if (PIDLoopActive){
            Pose2D error = new Pose2D(targetX - otosSensor.getPosition().x, targetY - otosSensor.getPosition().y, targetH - otosSensor.getPosition().h);

            if (Math.abs(error.x) < PIDKill.x && Math.abs(error.y) < PIDKill.y && Math.abs(error.h) < PIDKill.h && !timerLock){
                holdTimer.schedule(TurnOffPIDLoop, holdTime);
                timerLock = true;
            }

            integral = integral.add(error);

            Pose2D derivative = new Pose2D(error.x - prevError.x, error.y - prevError.y, error.h - prevError.h);

            Pose2D power = new Pose2D(errorThing(error, Pose2DComponent.X, (kp * error.x) + (ki * integral.x) + (kd * derivative.x), speed),
                    errorThing(error, Pose2DComponent.Y, (kp * error.y) + (ki * integral.y) + (kd * derivative.y), speed),
                    -errorThing(error, Pose2DComponent.H, (kpTheta * error.h) + (kiTheta * integral.h) + (kdTheta * derivative.h), speed));

            prevError = new Pose2D(error);

            TelemetryPasser.telemetry.addData("X", otosSensor.getPosition().x);
            TelemetryPasser.telemetry.addData("Y", otosSensor.getPosition().y);
            TelemetryPasser.telemetry.addData("H", otosSensor.getPosition().h);

            TelemetryPasser.telemetry.addData("errorX", error.x);
            TelemetryPasser.telemetry.addData("errorY", error.y);
            TelemetryPasser.telemetry.addData("errorH", error.h);

            TelemetryPasser.telemetry.addData("PowerX", power.x);
            TelemetryPasser.telemetry.addData("PowerY", power.y);
            TelemetryPasser.telemetry.addData("PowerH", power.h);

            TelemetryPasser.telemetry.update();

            return power;
        } else {
            return new Pose2D(0,0,0);
        }
    }

    /**
     * takes in error and input and outputs correspondinginglingly
     * @param error the error vector
     * @param input the input to be outputted
     * @param component the component which is x, y, or h
     * @param speed the speed that's put into the movePID method
     * @return
     */
    public double errorThing(Pose2D error, Pose2DComponent component, double input, double speed){
        switch(component){
            case X:
                if (error.x < 0) {
                    return Math.max(input, -speed);
                }
                else {
                    return Math.min(input, speed);
                }
            case Y:
                if (error.y < 0) {
                    return Math.max(input, -speed);
                }
                else {
                    return Math.min(input, speed);
                }
            case H:
                if (error.h < 0) {
                    return Math.max(input, -speed);
                }
                else {
                    return Math.min(input, speed);
                }
            default:
                return 0;
        }
    }

}
