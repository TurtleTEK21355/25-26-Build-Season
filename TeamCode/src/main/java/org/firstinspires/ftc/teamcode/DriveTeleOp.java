
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Drive TeleOp")
public class DriveTeleOp extends LinearOpMode {
    DcMotor lb;
    DcMotor rb;
    DcMotor lf;
    DcMotor rf;

    @Override
    public void runOpMode() {
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            joystickMovement(-gamepad1.left_stick_x, -gamepad1.left_stick_y, -gamepad1.right_stick_x, -gamepad1.right_stick_y);
        }
    }

    public void joystickMovement(double lx, double ly, double rx, double ry) {
        if (rx != 0 || ry != 0) {
            rf.setPower((-ry + rx) / 2);
            rb.setPower((-ry + rx) / 2);
            lf.setPower((ry + rx) / 2);
            lb.setPower((ry + rx) / 2);
        } else {
            rf.setPower((ly + lx) / 2);
            rb.setPower((ly - lx) / 2);
            lf.setPower((-ly + lx) / 2);
            lb.setPower((-ly - lx) / 2);
        }
    }
}
