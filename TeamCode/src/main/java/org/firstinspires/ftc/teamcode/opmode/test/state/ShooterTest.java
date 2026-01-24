package org.firstinspires.ftc.teamcode.opmode.test.state;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Shooter Test", group = "test")
public class ShooterTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "motor");
        Servo servo = hardwareMap.get(Servo.class, "servo");

        waitForStart();
        while (opModeIsActive()) {
            motor.setPower(gamepad1.right_trigger);
            servo.setPosition(gamepad1.left_stick_y);
        }
    }
}