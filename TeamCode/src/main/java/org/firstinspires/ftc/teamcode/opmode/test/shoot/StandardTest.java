package org.firstinspires.ftc.teamcode.opmode.test.shoot;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShootHardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.shared.sensor.OTOSSensor;

@TeleOp(name = "Standard Test", group = "test")
public class StandardTest extends LinearOpMode {
    ShootHardwareNames hardwareNames = new ShootHardwareNames();
    @Override
    public void runOpMode() {
        OTOSSensor otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(ShootHardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(0, 0, 0, DistanceUnit.INCH, AngleUnit.DEGREES, 1.0, 1.0);
        DcMotor lf = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.FRONT_LEFT_MOTOR));
        DcMotor lb = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.BACK_LEFT_MOTOR));
        DcMotor rf = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.FRONT_RIGHT_MOTOR));
        DcMotor rb = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.BACK_RIGHT_MOTOR));
        Servo ballGate = hardwareMap.get(Servo.class, hardwareNames.get(ShootHardwareNames.Name.SHOOTER_GATE));
        DcMotor flyWheel = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.SHOOTER_FLYWHEEL));
        DcMotor intake = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.INTAKE_MOTOR));
        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        ballGate.setDirection(Servo.Direction.REVERSE);

        double stick;
        ballGate.setPosition((double)1/3); // sets to open position
        waitForStart();
        while (opModeIsActive()) {
            stick = -gamepad1.left_stick_y;
            lf.setPower(stick);
            rf.setPower(stick);
            lb.setPower(stick);
            rb.setPower(stick);
            if(gamepad2.a) {                ballGate.setPosition(0); // sets to open position
            } else {
                ballGate.setPosition((double) 1 / 3); //sets to closed position
            }
            flyWheel.setPower(gamepad1.right_trigger);
            intake.setPower(-gamepad1.right_stick_y);
        }
    }
}