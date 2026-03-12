package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.HardwareName;

@TeleOp(name = "Carousel Servo Test", group = "test")
public class CarouselServoTest extends OpMode {
    private Servo carouselServo;

    @Override
    public void init() {
        carouselServo = hardwareMap.get(Servo.class, HardwareName.CAROUSEL_SERVO.getName());
    }

    @Override
    public void loop() {
        if (gamepad1.right_bumper) {
            carouselServo.setPosition(1);
        }
        else if (gamepad1.left_bumper) {
            carouselServo.setPosition(0);
        }
    }
}
