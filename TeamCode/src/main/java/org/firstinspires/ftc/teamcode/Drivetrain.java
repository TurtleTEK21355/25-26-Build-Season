package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import java.util.Timer;
import java.util.TimerTask;

public class Drivetrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private SparkFunOTOS otosSensor;
    private double PIDKillX = 0.75;
    private double PIDKillY = 0.75;
    private double PIDKillH = 10;
    private double kp = 0.09;
    private double ki = 0.0;
    private double kd = 0.0;
    double kpTheta;
    double kiTheta;
    double kdTheta;
    public enum Component {X, Y, H}
    private boolean PIDLoopActive = true;

    public Drivetrain(DcMotor frontLeft,DcMotor frontRight, DcMotor backLeft, DcMotor backRight){
        this.frontLeftMotor = frontLeft;
        this.frontRightMotor = frontRight;
        this.backLeftMotor = backLeft;
        this.backRightMotor = backRight;
        this.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void configureDrivetrain(OtosSensor otosSensor, double kp, double ki, double kd, double kpTheta, double kiTheta, double kdTheta){
        this.otosSensor = otosSensor.sensor;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kpTheta = kpTheta;
        this.kiTheta = kiTheta;
        this.kdTheta = kdTheta;
    }

    public void configureDrivetrain(OtosSensor otosSensor) {
        this.otosSensor = otosSensor.sensor;
    }

    public void movePIDNoTheta(double targetX, double targetY, double speed){
        Vector2 prevError = new Vector2(0,0,0);
        Vector2 integral = new Vector2(0,0,0);
        boolean PIDLoopActive = true;

        while (PIDLoopActive){
            Vector2 error = new Vector2(targetX - otosSensor.getPosition().x, targetY - otosSensor.getPosition().y, 0);
            TelemetryPasser.telemetry.addData("X", otosSensor.getPosition().x);
            TelemetryPasser.telemetry.addData("Y", otosSensor.getPosition().y);

            TelemetryPasser.telemetry.addData("errorX", error.x);
            TelemetryPasser.telemetry.addData("errorY", error.y);

            if (Math.abs(error.x) < PIDKillX && Math.abs(error.y) < PIDKillY){
                PIDLoopActive = false;
            }

            integral = integral.add(error);

            Vector2 derivative = new Vector2(error.x - prevError.x, error.y - prevError.y, 0);

            Vector2 power = new Vector2(errorThing(error, Component.X, (kp * error.x) + (ki * integral.x) + (kd * derivative.x), Math.abs(speed)),
                                        errorThing(error, (Component.Y), (kp * error.y) + (ki * integral.y) + (kd * derivative.y), Math.abs(speed)),
                                        0);

            TelemetryPasser.telemetry.addData("PowerX", power.x);
            TelemetryPasser.telemetry.addData("PowerY", power.y);

            prevError = new Vector2(error);

            TelemetryPasser.telemetry.update();

            fcControl(power.y, power.x, 0);
        }

    }
    public void movePID(double targetX, double targetY, double targetH, double speed){
        Vector2 prevError = new Vector2(0,0,0);
        Vector2 integral = new Vector2(0,0,0);
        boolean PIDLoopActive = true;

        while (PIDLoopActive){
            Vector2 error = new Vector2(targetX - otosSensor.getPosition().x, targetY - otosSensor.getPosition().y, targetH - otosSensor.getPosition().h);
            TelemetryPasser.telemetry.addData("X", otosSensor.getPosition().x);
            TelemetryPasser.telemetry.addData("Y", otosSensor.getPosition().y);
            TelemetryPasser.telemetry.addData("H", otosSensor.getPosition().h);
            TelemetryPasser.telemetry.addData("errorX", error.x);
            TelemetryPasser.telemetry.addData("errorY", error.y);
            TelemetryPasser.telemetry.addData("errorH", error.h);

            if (Math.abs(error.x) < PIDKillX && Math.abs(error.y) < PIDKillY && Math.abs(error.h) < PIDKillH){
                PIDLoopActive = false;
            }

            integral = integral.add(error);

            Vector2 derivative = new Vector2(error.x - prevError.x, error.y - prevError.y, error.h - prevError.h);

            Vector2 power = new Vector2(errorThing(error, Component.X, (kp * error.x) + (ki * integral.x) + (kd * derivative.x), speed),
                                        errorThing(error, Component.Y, (kp * error.y) + (ki * integral.y) + (kd * derivative.y), speed),
                                        errorThing(error, Component.H, (kpTheta * error.h) + (kiTheta * integral.h) + (kdTheta * derivative.h), speed));

            prevError = new Vector2(error);

            TelemetryPasser.telemetry.addData("PowerX", power.x);
            TelemetryPasser.telemetry.addData("PowerY", power.y);
            TelemetryPasser.telemetry.addData("PowerH", power.h);

            fcControl(power.y, power.x, -power.h);
            TelemetryPasser.telemetry.update();
        }

    }

    public void fcControl(double y, double x, double h) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(otosSensor.getPosition().h);

        double correctedY = r * Math.sin(correctedTheta);
        double correctedX = r * Math.cos(correctedTheta);

        frontRightMotor.setPower(Range.clip(correctedY - correctedX - h, -1, 1));
        frontLeftMotor.setPower(Range.clip(correctedY - correctedX + h, -1, 1));
        backRightMotor.setPower(Range.clip(correctedY + correctedX - h, -1, 1));
        backLeftMotor.setPower(Range.clip(correctedY + correctedX + h, -1, 1));
        TelemetryPasser.telemetry.addData("flPower=", frontLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("frPower=", frontRightMotor.getPower());
        TelemetryPasser.telemetry.addData("blPower=", backLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("brPower=", backRightMotor.getPower());

    }
    public void control(double y, double x, double h) {
        frontRightMotor.setPower(Range.clip(y - x - h, -1, 1));
        frontLeftMotor.setPower(Range.clip(y - x + h, -1, 1));
        backRightMotor.setPower(Range.clip(y + x - h, -1, 1));
        backLeftMotor.setPower(Range.clip(y + x + h, -1, 1));
        TelemetryPasser.telemetry.addData("flPower=", frontLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("frPower=", frontRightMotor.getPower());
        TelemetryPasser.telemetry.addData("blPower=", backLeftMotor.getPower());
        TelemetryPasser.telemetry.addData("brPower=", backRightMotor.getPower());

    }

    /**
     * takes in error and input and outputs correspondinginglingly
     * @param error the error vector
     * @param input the input to be outputted
     * @param component the component which is x, y, or h
     * @param speed the speed that's put into the movePID method
     * @return
     */
    public double errorThing(Vector2 error, Component component, double input, double speed){
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
    public void movePID(double targetX, double targetY, double targetH, double speed, int holdTime){
        Vector2 prevError = new Vector2(0,0,0);
        Vector2 integral = new Vector2(0,0,0);
        PIDLoopActive = true;
        boolean timerLock = false;
        Timer holdTimer = new Timer();
        TimerTask TurnOffPIDLoop = new TimerTask() {
            @Override
            public void run(){
                PIDLoopActive = false;
                holdTimer.cancel();
            }
        };

        while (PIDLoopActive){
            Vector2 error = new Vector2(targetX - otosSensor.getPosition().x, targetY - otosSensor.getPosition().y, targetH - otosSensor.getPosition().h);
            TelemetryPasser.telemetry.addData("X", otosSensor.getPosition().x);
            TelemetryPasser.telemetry.addData("Y", otosSensor.getPosition().y);
            TelemetryPasser.telemetry.addData("H", otosSensor.getPosition().h);
            TelemetryPasser.telemetry.addData("errorX", error.x);
            TelemetryPasser.telemetry.addData("errorY", error.y);
            TelemetryPasser.telemetry.addData("errorH", error.h);

            if (Math.abs(error.x) < PIDKillX && Math.abs(error.y) < PIDKillY && Math.abs(error.h) < PIDKillH && !timerLock){
                holdTimer.schedule(TurnOffPIDLoop, holdTime);
                timerLock = true;
            }

            integral = integral.add(error);

            Vector2 derivative = new Vector2(error.x - prevError.x, error.y - prevError.y, error.h - prevError.h);

            Vector2 power = new Vector2(errorThing(error, Component.X, (kp * error.x) + (ki * integral.x) + (kd * derivative.x), speed),
                    errorThing(error, Component.Y, (kp * error.y) + (ki * integral.y) + (kd * derivative.y), speed),
                    errorThing(error, Component.H, (kpTheta * error.h) + (kiTheta * integral.h) + (kdTheta * derivative.h), speed));

            prevError = new Vector2(error);

            TelemetryPasser.telemetry.addData("PowerX", power.x);
            TelemetryPasser.telemetry.addData("PowerY", power.y);
            TelemetryPasser.telemetry.addData("PowerH", power.h);

            fcControl(power.y, power.x, -power.h);
            TelemetryPasser.telemetry.update();
        }

    }

}
