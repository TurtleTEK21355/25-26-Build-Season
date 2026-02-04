package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Shooter Test", group = "test")
public class ShooterTest extends LinearOpMode {
    final double MAXHOODPOSITION = 0.5;


    @Override
    public void runOpMode() {
        DcMotor motor = hardwareMap.get(DcMotor.class, "motor");
        Servo servo = hardwareMap.get(Servo.class, "servo");
        servo.setDirection(Servo.Direction.REVERSE);
        motor.setDirection(DcMotor.Direction.REVERSE);

        servo.setPosition(0);
        waitForStart();
        while (opModeIsActive()) {
            motor.setPower(gamepad1.right_trigger);
            servo.setPosition(gamepad1.left_trigger*MAXHOODPOSITION);
            telemetry.addData("Servo Position", servo.getPosition());
            telemetry.update();
        }
    }
}