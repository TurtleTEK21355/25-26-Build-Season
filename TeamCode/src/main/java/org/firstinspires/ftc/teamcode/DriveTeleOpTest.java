
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Drive TeleOp Test", group="Testing")
public class DriveTeleOpTest extends LinearOpMode {
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
            joystickMovement(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
        }
    }

    public void joystickMovement(double x, double y) {
        double prf = (y+x)/2;
        double prb = (y-x)/2;
        double plf = (-y+x)/2;
        double plb = (-y-x)/2;
        double norm = Math.max(Math.max(prf, prb), Math.max(plf, plb));
        double mag = Math.sqrt((x*x)+(y*y));
        rf.setPower((prf/norm)*mag*0.9);
        rb.setPower((prb/norm)*mag*0.9);
        lf.setPower((plf/norm)*mag*0.9);
        lb.setPower((plb/norm)*mag*0.9);
    }
}
