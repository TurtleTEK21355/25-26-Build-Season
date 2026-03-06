package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.HardwareName;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

@TeleOp(name = "Positioning Test", group = "test")
public class PositioningTest extends OpMode {
    private Drivetrain drivetrain;
    private OTOSSensor otosSensor;
    private Limelight wrappedLimelight;

    @Override
    public void init() {
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotorEx.class, HardwareName.FRONT_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotorEx.class, HardwareName.FRONT_RIGHT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.BACK_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.BACK_RIGHT_MOTOR.getName())
        );
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, HardwareName.ODOMETRY_SENSOR.getName()));
        otosSensor.setPosition(new Pose2D(0, 0, 0));
        otosSensor.configureOtos(Constants.getPhysicalOffset(), DistanceUnit.INCH, AngleUnit.DEGREES, 1, Constants.angularScalar); //default
        wrappedLimelight = new Limelight(hardwareMap.get(Limelight3A.class, HardwareName.LIMELIGHT.getName()));
    }

    @Override
    public void loop() {
        if (gamepad1.y) {
            otosSensor.setPosition(new Pose2D(0,0,0));
        }

        Pose2D position = otosSensor.getPosition();
        wrappedLimelight.updateRobotOrientation(position.h); //gets proper position

        if (gamepad1.a) {
            otosSensor.setPosition(wrappedLimelight.getCorrectedPositionFromLL(position));
        }

        drivetrain.control(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        telemetry.addData("Position", position);

    }

}
