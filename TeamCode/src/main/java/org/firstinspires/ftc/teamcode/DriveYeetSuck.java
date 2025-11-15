package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.Shooter;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@TeleOp(name = "UseThisOnePLEASE", group = "")
public class DriveYeetSuck extends LinearOpMode {
    Drivetrain drivetrain;
    Shooter shooter;
    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryPasser.telemetry = telemetry;
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "lf"),
                hardwareMap.get(DcMotor.class, "rf"),
                hardwareMap.get(DcMotor.class, "lb"),
                hardwareMap.get(DcMotor.class, "rb"));
        DcMotor intakeAndOuttake = hardwareMap.get(DcMotor.class, "intake2");

        //DcMotor shooter = hardwareMap.get(DcMotor.class, "shooterUsingButton");
       // shooter.setDirection(DcMotorSimple.Direction.REVERSE);

        shooter = new Shooter(
                hardwareMap.get(DcMotor.class, "shooter1"));

        
        waitForStart();
        double shooterPower = 0;
        while (opModeIsActive()) {
            telemetry.addData("Motor Power:", intakeAndOuttake.getPower());
            telemetry.addData("Yeeter Power:", shooter.getShooterSpeed());
            telemetry.update();

            double intakeAndOuttakePower;
            if (gamepad1.right_trigger == 1) {
                intakeAndOuttakePower = 1;
            }
            else if (gamepad1.right_bumper) {
                intakeAndOuttakePower = -1;
            }
            else {
                intakeAndOuttakePower = 0;
            }
            intakeAndOuttake.setPower(intakeAndOuttakePower);

            if (gamepad1.left_trigger != 0) {
                shooter.startShooter(); //ONLY RUNS ONCE
                shooter.runShooter(); //ALWAYS RUNS IF THE SHOOTER IS ON
            }
            else if (gamepad1.left_trigger == 0) {
                shooter.stopShooter();
            }
            drivetrain.control(gamepad1.left_stick_y * 0.5, gamepad1.left_stick_x * 0.5, gamepad1.right_stick_x);
            if (gamepad1.a) {
                shooter.increaseShooterSpeed(0.05);
                sleep(500);
            }
            if (gamepad1.b) {
                shooter.decreaseShooterSpeed(0.05);
                sleep(500);
            }
        }
    }




}
