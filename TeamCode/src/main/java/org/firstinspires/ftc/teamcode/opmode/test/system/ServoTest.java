package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.HardwareName;

@TeleOp(name = "Servo Test", group = "test")
public class ServoTest extends OpMode {
    private Servo carouselServo;
    private Servo hoodServo;

    @Override
    public void init() {
        carouselServo = hardwareMap.get(Servo.class, HardwareName.CAROUSEL_SERVO.getName());
        hoodServo = hardwareMap.get(Servo.class, HardwareName.HOOD_SERVO.getName());
    }

    @Override
    public void loop() {
        if (gamepad1.rightBumperWasPressed()) {
            carouselServo.setPosition(carouselServo.getPosition()+0.1);
        }
        else if (gamepad1.leftBumperWasPressed()) {
            carouselServo.setPosition(carouselServo.getPosition()-0.1);
        }
        if (gamepad1.aWasPressed()) {
            hoodServo.setPosition(hoodServo.getPosition()+0.1);
        }
        else if (gamepad1.bWasPressed()) {
            hoodServo.setPosition(hoodServo.getPosition()-0.1);
        }
        telemetry.addData("hood servo pos", hoodServo.getPosition());
        telemetry.addData("carousel servo pos", carouselServo.getPosition());
    }
}
