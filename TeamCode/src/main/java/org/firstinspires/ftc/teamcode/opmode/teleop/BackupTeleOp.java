package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;

@TeleOp(name = "Backup TeleOp", group="Linear OpModes")
public class BackupTeleOp extends LinearOpMode {
    HardwareNames hardwareNames = new HardwareNames();
    @Override
    public void runOpMode() {
        OTOSSensor otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(HardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(0, 0, 0, DistanceUnit.INCH, AngleUnit.DEGREES, 1.0, 1.0);
        DcMotor lf = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR));
        DcMotor lb = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR));
        DcMotor rf = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR));
        DcMotor rb = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR));
        Servo ballGate = hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.SHOOTER_GATE));
        DcMotorEx flyWheel = hardwareMap.get(DcMotorEx.class, hardwareNames.get(HardwareNames.Name.SHOOTER_FLYWHEEL));
        DcMotor intake = hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.INTAKE_MOTOR));
        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        ballGate.setDirection(Servo.Direction.REVERSE);

        double ly;
        double lx;
        double ry;
        double rx;
        ballGate.setPosition((double)1/3); // sets to open position
        waitForStart();
        while (opModeIsActive()) {
            rx = gamepad1.right_stick_x;
            ry = gamepad1.right_stick_y;
            lx = gamepad1.left_stick_x;
            ly = -gamepad1.left_stick_y;
            if (rx != 0 || ry != 0) {
                double magnitude = Math.sqrt((ry*ry)+(rx*rx));
                TelemetryPasser.telemetry.addData("magnitude", magnitude);
                TelemetryPasser.telemetry.update();
                double fr = (ry + rx) / 2;
                double br = (ry + rx) / 2;
                double fl = (ry - rx) / 2;
                double bl = (ry - rx) / 2;
                double min = Math.min(Math.min(fr, br), Math.min(fl, bl));
                rf.setPower((fr/min)*magnitude);
                rb.setPower((br/min)*magnitude);
                lb.setPower((fl/min)*magnitude);
                lb.setPower((bl/min)*magnitude);
            } else {
                double magnitude = Math.sqrt((ly*ly)+(lx*lx));
                TelemetryPasser.telemetry.addData("magnitude", magnitude);
                TelemetryPasser.telemetry.update();
                double fr = (ly - lx) / 2;
                double br = (ly + lx) / 2;
                double fl = (ly + lx) / 2;
                double bl = (ly - lx) / 2;
                double min = Math.min(Math.min(fr, br), Math.min(fl, bl));
                min = Math.abs(min);
                rf.setPower((fr/min)*magnitude);
                rb.setPower((br/min)*magnitude);
                lf.setPower((fl/min)*magnitude);
                lb.setPower((bl/min)*magnitude);

            }
            if(gamepad2.a) {
                ballGate.setPosition(0); // sets to open position
            } else {
                ballGate.setPosition((double) 1 / 3); //sets to closed position
            }
            if (gamepad2.right_bumper) {
                flyWheel.setVelocity(1150);
            } else {
                flyWheel.setVelocity(600);
            }
            if(gamepad2.left_bumper) {
                intake.setPower(0.8);
            } else if(gamepad2.left_trigger > 0.1) {
                intake.setPower(-0.8);
            } else {
                intake.setPower(0);
            }
        }
    }
}