package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;

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