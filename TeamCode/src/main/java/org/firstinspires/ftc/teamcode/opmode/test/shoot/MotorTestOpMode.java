package org.firstinspires.ftc.teamcode.opmode.test.shoot;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.shared.sensor.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShootHardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.shared.sensor.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShooterSystem;

@Disabled
@Autonomous(name="MotorTestOpMode", group = "test")
public class MotorTestOpMode extends LinearOpMode {
    ShootHardwareNames hardwareNames = new ShootHardwareNames();
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    ShooterSystem shooterSystem;
    OTOSSensor otosSensor;
    AprilTagCamera aprilTagCamera;

    @Override
    public void runOpMode() {

        frontLeftMotor = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.FRONT_LEFT_MOTOR));
        frontRightMotor = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.FRONT_RIGHT_MOTOR));
        backLeftMotor = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.BACK_LEFT_MOTOR));
        backRightMotor = hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.BACK_RIGHT_MOTOR));

        waitForStart();

        testMotor(frontLeftMotor, "frontLeft");
        testMotor(frontRightMotor, "frontRight");
        testMotor(backLeftMotor, "backLeft");
        testMotor(backRightMotor, "backRight");
        telemetry.addData("frontLeftDirection", frontLeftMotor.getDirection());
        telemetry.addData("frontRightDirection", frontRightMotor.getDirection());
        telemetry.addData("backLeftDirection", backLeftMotor.getDirection());
        telemetry.addData("backRightDirection", frontLeftMotor.getDirection());

    }
    private void testMotor(DcMotor motor, String name) {
        while (opModeIsActive()) {
            motor.setPower(0.5);
            telemetry.addLine("testing: " + name);
            telemetry.addLine("is it moving forwards?");
            telemetry.addLine("a for yes, b for no");
            if (gamepad1.a) {
                break;
            } else if (gamepad1.b){
                motor.setDirection(DcMotorSimple.Direction.REVERSE);
                testMotor(motor, name);
            }
        }
    }

}
