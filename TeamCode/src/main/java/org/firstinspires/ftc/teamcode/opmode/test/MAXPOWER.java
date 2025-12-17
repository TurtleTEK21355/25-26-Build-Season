package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;

@TeleOp(name = "Standard Test", group="Linear OpModes")
public class MAXPOWER extends LinearOpMode {
    HardwareNames hardwareNames = new HardwareNames();
    @Override
    public void runOpMode() {
        Servo ballGate = hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.SHOOTER_GATE));
        DcMotorEx flyWheel = hardwareMap.get(DcMotorEx.class, hardwareNames.get(HardwareNames.Name.SHOOTER_FLYWHEEL));
        DcMotor intake = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.INTAKE_MOTOR));
        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        ballGate.setDirection(Servo.Direction.REVERSE);
        ballGate.setPosition((double)1/3); // sets to open position
        waitForStart();
        while (opModeIsActive()) {
            if(gamepad2.a) {                ballGate.setPosition(0); // sets to open position
            } else {
                ballGate.setPosition((double) 1 / 3); //sets to closed position
            }
            flyWheel.setVelocity(1500);
            intake.setPower(-gamepad1.right_stick_y);
        }
    }
}