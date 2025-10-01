package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.internal.AprilTagCamera;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.Mode;
import org.firstinspires.ftc.teamcode.internal.ModeController;
import org.firstinspires.ftc.teamcode.internal.OtosSensor;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@Autonomous(name="BasicGarbage", group="Linear OpMode")
public class TurnTowardsAThing extends LinearOpMode {
    Drivetrain drivetrain;
    OtosSensor otosSensor;
    AprilTagCamera aprilTagCamera;
    double kp = 0.05;
    double ki;
    double kd;
    double kpTheta = 0.03;
    double kiTheta;
    double kdTheta;
    double speed;
    boolean fieldCentricEnabled;

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryPasser.telemetry = telemetry;

        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        waitForStart();
        configureVariables();
        drivetrain.configureDrivetrain(aprilTagCamera, otosSensor, kp, ki, kd, kpTheta, kiTheta, kdTheta);



    }

    public void configureVariables(){
        ModeController modeController = new ModeController();
        modeController.add(
                new Mode(kp, "Kp"),
                new Mode(ki, "Ki"),
                new Mode(kd, "Kd"),
                new Mode(kpTheta, "KpTheta"),
                new Mode(kiTheta, "KiTheta"),
                new Mode(kdTheta, "KdTheta"),
                new Mode(0.3, "Speed"),
                new Mode(true, "FieldCentric")

        );

        while(opModeIsActive() && !gamepad1.start) {
            modeController.modeSelection(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left);

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
        kp = modeController.getModeValueDouble("Kp");
        ki = modeController.getModeValueDouble("Ki");
        kd = modeController.getModeValueDouble("Kd");
        kpTheta = modeController.getModeValueDouble("KpTheta");
        kiTheta = modeController.getModeValueDouble("KiTheta");
        kdTheta = modeController.getModeValueDouble("KdTheta");
        speed = modeController.getModeValueDouble("Speed");
        fieldCentricEnabled = modeController.getModeValueBoolean("FieldCentric");

    }
}