package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "IntakeAndShooter", group = "")
public class DriveYeetSuck extends LinearOpMode {
    DcMotor lb;
    DcMotor rb;
    DcMotor lf;
    DcMotor rf;
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor intakeAndOuttake = hardwareMap.get(DcMotor.class, "intake2");
        DcMotor shooter = hardwareMap.get(DcMotor.class, "shooterUsingButton");

        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
        double shooterPower = 0;
        while (opModeIsActive()) {
            telemetry.addData("Motor Power:", intakeAndOuttake.getPower());
            telemetry.addData("Yeeter Power:", shooter.getPower());
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
                shooterPower += 0.03;
            }
            else if (gamepad1.left_trigger == 0) {
                shooterPower = 0;
            }

            if (shooterPower > 0.75) {
                shooterPower = 0.75;
            }
            shooter.setPower(shooterPower);
            //Alternative
//            if (shooterPower <= 0.75 && gamepad1.left_trigger != 0) {
//                shooter.setPower(gamepad1.left_trigger * shooterPower);
//                shooterPower += 0.03;
//            }
//            else if (gamepad1.left_trigger == 0)
//                shooter.setPower(gamepad1.left_trigger * 0);
//            else if (shooterPower > 0.75) {

//            }
            joystickMovement(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

            telemetry.addData("Motor Power:", intakeAndOuttakePower);
            telemetry.addData("Yeeter Power:", shooterPower);
            telemetry.update();
        }
    }



    public void joystickMovement(double lx, double ly, double rx) {
        if (rx != 0 ) {
            rf.setPower((rx) / 2);
            rb.setPower((rx) / 2);
            lf.setPower((rx) / 2);
            lb.setPower((rx) / 2);
        } else {
            rf.setPower((ly + lx) / 2);
            rb.setPower((ly - lx) / 2);
            lf.setPower((-ly + lx) / 2);
            lb.setPower((-ly - lx) / 2);
        }
    }

}
