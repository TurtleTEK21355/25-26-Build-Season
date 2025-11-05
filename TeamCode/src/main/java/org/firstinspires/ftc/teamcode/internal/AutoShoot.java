package org.firstinspires.ftc.teamcode.internal;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class AutoShoot {

    AprilTagCamera aprilTagCamera;
    public AutoShoot(WebcamName webcam){
        aprilTagCamera = new AprilTagCamera(webcam);
    }

    //Measure Distance
    double distance = 1;
    double requiredPower = MATH.calculate(distance);
    //Set Power According to Formula
    motor.setPower(requiredPower);
}
