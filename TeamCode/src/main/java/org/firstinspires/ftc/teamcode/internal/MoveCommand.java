package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MoveCommand extends Command{
    double targetY;
    double targetX;
    double targetH;
    double speed;
    Drivetrain drivetrain;
    PIDControllerSpeedLimit yPID;
    PIDControllerSpeedLimit xPID;
    PIDControllerHeading hPID;

    public MoveCommand(Drivetrain drivetrain, double targetY, double targetX, double targetH, double speed) {
        this.drivetrain = drivetrain;
        this.targetY = targetY;
        this.targetX = targetX;
        this.targetH = targetH;
        this.speed = speed;
//        yPID = new PIDControllerSpeedLimit(kp, ki, kd, targetY, tolerance.y, speed);
//        xPID = new PIDControllerSpeedLimit(kp, ki, kd, targetX, tolerance.x, speed);
//        hPID = new PIDControllerHeading(kpTheta, kiTheta, kdTheta, targetH, tolerance.h, speed);
    }

    @Override
    public void loop() {

    }

    @Override
    public void is_completed() {

    }
//    public void movePID(double targetY, double targetX, double targetH, double speed){
//        double yPos = otosSensor.getPosition().y;
//        double xPos = otosSensor.getPosition().x;
//        double hPos = otosSensor.getPosition().h;
//
//        PIDControllerSpeedLimit yPID = new PIDControllerSpeedLimit(kp, ki, kd, targetY, tolerance.y, speed);
//        PIDControllerSpeedLimit xPID = new PIDControllerSpeedLimit(kp, ki, kd, targetX, tolerance.x, speed);
//        PIDControllerHeading hPID = new PIDControllerHeading(kpTheta, kiTheta, kdTheta, targetH, tolerance.h, speed);
//
//        while (!yPID.atTarget(yPos) || !xPID.atTarget(xPos) || !hPID.atTarget(hPos)){
//            yPos = otosSensor.getPosition().y;
//            xPos = otosSensor.getPosition().x;
//            hPos = otosSensor.getPosition().h;
//
//            fcControl(yPID.calculate(yPos), xPID.calculate(xPos), hPID.calculate(hPos));
//
//            powerTelemetry();
//
//        }
//
//    }
}
