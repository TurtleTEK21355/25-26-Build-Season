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

        DcMotor intake = hardwareMap.get(DcMotor.class, "intake2");
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

        while (opModeIsActive()) {
            telemetry.addData("Motor Power:", intake.getPower());
            telemetry.addData("Yeeter Power:", shooter.getPower());
            telemetry.update();

            intake.setPower(gamepad1.right_stick_y);
            shooter.setPower(gamepad1.left_trigger * .75);
            joystickMovement(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);


        }
    }



    public void joystickMovement(double lx, double ly, double rx) {
        if (rx != 0 ) {
            rf.setPower((rx));
            rb.setPower((rx));
            lf.setPower((rx));
            lb.setPower((rx));
        } else {
            rf.setPower((ly + lx));
            rb.setPower((ly - lx));
            lf.setPower((-ly + lx));
            lb.setPower((-ly - lx));
        }
    }

}
