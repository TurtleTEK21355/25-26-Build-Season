package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="BasicGarbage", group="Linear OpMode")
public class BasicGarbage extends LinearOpMode {
    Drivetrain drivetrain;
    OtosSensor otosSensor;
    double kp = 0.09;
    double ki;
    double kd;

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryPasser.telemetry = telemetry;
        otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos sensor"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);

        drivetrain = new Drivetrain(
            hardwareMap.get(DcMotor.class, "front left drive"),
            hardwareMap.get(DcMotor.class, "front right drive"),
            hardwareMap.get(DcMotor.class, "back left drive"),
            hardwareMap.get(DcMotor.class, "back right drive"));

        waitForStart();
        configurePID();
        drivetrain.configureDrivetrain(otosSensor, kp, ki, kd);

        double speed = 0.3;

        drivetrain.movePID(0, 10, 0, speed);
        drivetrain.movePID(10, 10, 0, speed);
        drivetrain.movePID(10, 0, 0, speed);
        drivetrain.movePID(0, 0, 0, speed);

    }

    public void configurePID(){
        ModeController modeController = new ModeController();
        modeController.add(
                new Mode(kp, "Kp"),
                new Mode(ki, "Ki"),
                new Mode(kd, "Kd")

        );

        while(opModeIsActive() && !gamepad1.start) {
            modeController.modeSelection(gamepad1.dpad_left, gamepad1.dpad_right, gamepad1.dpad_up, gamepad1.dpad_down);

            telemetry.addLine("Press start to Start");

            if (gamepad1.dpad_down) {
                telemetry.addLine("Dpad Down");
            } else if (gamepad1.dpad_up) {
                telemetry.addLine("Dpad Up");
            } else {
                telemetry.addLine("");
            }

            telemetry.addLine(modeController.reportModeValue());
            telemetry.update();

        }
        kp = modeController.getModeValue("Kp");
        ki = modeController.getModeValue("Ki");
        kd = modeController.getModeValue("Kd");
        telemetry.addData("values", kp);
        telemetry.addData("values", ki);
        telemetry.addData("values", kd);
    }
}