package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

public class Drivetrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private double PIDKillX = 0.75;
    private double PIDKillY = 0.75;
    private double PIDKillH = 10;
    private double kp = 0.9;
    private double ki = 0.0;
    private double kd = 0.0;
    public enum Component {X, Y, H}

    public Drivetrain(DcMotor frontLeft,DcMotor frontRight, DcMotor backLeft, DcMotor backRight){
        this.frontLeftMotor = frontLeft;
        this.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.frontRightMotor = frontRight;
        this.frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.backLeftMotor = backLeft;
        this.backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backRightMotor = backRight;
        this.backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void movePID(double targetX, double targetY, double targetH, double speed, SparkFunOTOS otosSensor){
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
            TelemetryPasser.telemetry.update();
            if (Math.abs(error.x) < PIDKillX && Math.abs(error.y) < PIDKillY && Math.abs(error.h) < PIDKillH){
                break;
            }

            integral = integral.add(error);

            Vector2 derivative = new Vector2(error.x - prevError.x, error.y - prevError.y, error.h - prevError.h);

            Vector2 power = new Vector2(errorThing(error, Component.X, (kp * error.x) + (ki * integral.x) + (kd * derivative.x), Math.abs(speed)),
                                        errorThing(error, Component.Y, (kp * error.y) + (ki * integral.y) + (kd * derivative.y), Math.abs(speed)),
                                        errorThing(error, Component.H, (kp * error.h) + (ki * integral.h) + (kd * derivative.h), Math.abs(speed)));

            prevError = new Vector2(error);

            fcControl(power.y, power.x, power.h, otosSensor);
        }

    }

    public void fcControl(double y, double x, double h, SparkFunOTOS otosSensor) {
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double theta = Math.atan2(y, x);

        double correctedTheta = theta - Math.toRadians(otosSensor.getPosition().h);

        double correctedY = r * Math.sin(correctedTheta);
        double correctedX = r * Math.cos(correctedTheta);

        frontRightMotor.setPower(Range.clip(correctedY - correctedX - correctedTheta, -1, 1));
        frontLeftMotor.setPower(Range.clip(correctedY - correctedX + correctedTheta, -1, 1));
        backRightMotor.setPower(Range.clip(correctedY + correctedX - correctedTheta, -1, 1));
        backLeftMotor.setPower(Range.clip(correctedY + correctedX + correctedTheta, -1, 1));

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
                    return Math.max(input, speed);
                }
                else {
                    return Math.min(input, speed);
                }
            case Y:
                if (error.y < 0) {
                    return Math.max(input, speed);
                }
                else {
                    return Math.min(input, speed);
                }
            case H:
                if (error.h < 0) {
                    return Math.max(input, speed);
                }
                else {
                    return Math.min(input, speed);
                }
            default:
                return 0;
        }
    }
}
