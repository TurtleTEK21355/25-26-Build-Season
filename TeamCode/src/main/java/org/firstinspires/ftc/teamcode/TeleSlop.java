package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.internal.BallPathChangeThisNamePleaseIDKWhatToCallIt;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.FlyWheel;
import org.firstinspires.ftc.teamcode.internal.Hopper;
import org.firstinspires.ftc.teamcode.internal.Intake;
import org.firstinspires.ftc.teamcode.internal.OtosSensor;
import org.firstinspires.ftc.teamcode.internal.Pose2D;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@TeleOp(name="TeleSlop", group="Iterative OpModes")
public class TeleSlop extends OpMode {

    Drivetrain drivetrain;
    OtosSensor otosSensor;
    BallPathChangeThisNamePleaseIDKWhatToCallIt ballPathChangeThisNamePleaseIDKWhatToCallIt;

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        ballPathChangeThisNamePleaseIDKWhatToCallIt = new BallPathChangeThisNamePleaseIDKWhatToCallIt(
                new FlyWheel(hardwareMap.get(DcMotor.class, "flyWheel")),
                new Hopper(hardwareMap.get(CRServo.class, "rightServo")),
                new Intake(hardwareMap.get(DcMotor.class, "Intake")));

        otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);
        drivetrain.configureDrivetrain(otosSensor);
    }

    @Override
    public void loop() {
        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        if (gamepad1.y){
            otosSensor.resetPosition();
        }
        ballPathChangeThisNamePleaseIDKWhatToCallIt.flywheelSpin(gamepad1.right_bumper, 1.0);
        ballPathChangeThisNamePleaseIDKWhatToCallIt.IntakeSpin(-gamepad1.right_stick_y);


    }
}