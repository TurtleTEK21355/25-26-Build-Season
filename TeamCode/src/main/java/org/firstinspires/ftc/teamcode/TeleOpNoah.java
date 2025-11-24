package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.internal.HardwareNames;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@Disabled
@TeleOp(name="TeleOpNoah", group="Iterative OpModes")
public class TeleOpNoah extends OpMode {

    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    ShooterSystem shooterSystem;
    HardwareNames hardwareNames = new HardwareNames();

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;

        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor);

        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotorEx.class, hardwareNames.get(HardwareNames.Name.SHOOTER_FLYWHEEL))),
                new Hopper(
                            hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.SHOOTER_GATE)),
                            hardwareMap.get(Ada2167BreakBeam.class, hardwareNames.get(HardwareNames.Name.BALL_READY_SENSOR))),
                new Intake(hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.INTAKE_MOTOR))));

    }

    @Override
    public void loop() {
        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        if (gamepad1.y){
            otosSensor.resetPosition();
        }
//       shooterSystem.teleOpControl(gamepad1.right_bumper);

        telemetry.update();

    }

}
