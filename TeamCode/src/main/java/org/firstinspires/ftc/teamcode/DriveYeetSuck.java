package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.Shooter;

@TeleOp(name = "IntakeAndShooter", group = "")
public class DriveYeetSuck extends LinearOpMode {
    DcMotor lb;
    DcMotor rb;
    DcMotor lf;
    DcMotor rf;

    Drivetrain drivetrain;
    Shooter shooter;
    @Override
    public void runOpMode() throws InterruptedException {

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
                shooter.startShooter(); //ONLY RUNS ONCE
                shooter.runShooter(); //ALWAYS RUNS IF THE SHOOTER IS ON
            }
            else if (gamepad1.left_trigger == 0) {
                shooterPower = 0;
            }

            if (shooterPower > 0.75) {
                shooterPower = 0.75;
            }
            shooter.setPower(shooterPower);

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
