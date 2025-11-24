package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;

@TeleOp(name = "Standard Test", group="Linear OpModes")
public class StandardTest extends LinearOpMode {
    HardwareNames hardwareNames = new HardwareNames();
    @Override
    public void runOpMode() {
        DcMotor lf = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR));
        DcMotor lb = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR));
        DcMotor rf = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR));
        DcMotor rb = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR));
        Servo ballGate = hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.SHOOTER_GATE));
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        ballGate.setDirection(Servo.Direction.REVERSE);
        DcMotor flyWheel = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.SHOOTER_FLYWHEEL));
        DcMotor intake = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.INTAKE_MOTOR));
        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        double stick;
        ballGate.setPosition((double)1/3); // sets to open position
        waitForStart();
        while (opModeIsActive()) {
            stick = -gamepad1.left_stick_y;
            lf.setPower(stick);
            rf.setPower(stick);
            lb.setPower(stick);
            rb.setPower(stick);
            if(gamepad1.a) {
                ballGate.setPosition(0); // sets to open position
            } else {
                ballGate.setPosition((double) 1 / 3); //sets to closed position
            }
            flyWheel.setPower(gamepad1.right_trigger);
            intake.setPower(-gamepad1.right_stick_y);
        }
    }
}