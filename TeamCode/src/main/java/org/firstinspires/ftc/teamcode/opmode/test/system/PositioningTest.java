package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
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
    private Limelight3A limelight;
    private Limelight wrappedLimelight;

    @Override
    public void init() {
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, HardwareName.FRONT_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.FRONT_RIGHT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.BACK_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.BACK_RIGHT_MOTOR.getName())
        );
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, HardwareName.ODOMETRY_SENSOR.getName()));
        otosSensor.setPosition(new Pose2D(0, 0, 0));
        otosSensor.configureOtos(Constants.getPhysicalOffset(), DistanceUnit.INCH, AngleUnit.DEGREES, 1, Constants.angularScalar); //default
        limelight = hardwareMap.get(Limelight3A.class, HardwareName.LIMELIGHT.getName());
        wrappedLimelight = new Limelight(limelight);
    }

    @Override
    public void loop() {
        if (gamepad1.y) {
            otosSensor.setPosition(new Pose2D(0,0,0));
        }

        Pose2D position = otosSensor.getPosition();

        LLResult latestResult = limelight.getLatestResult();
        Pose2D llPosition = new Pose2D(
                latestResult.getBotpose().getPosition().x * 39.3700787,
                latestResult.getBotpose().getPosition().y * 39.3700787,
                latestResult.getBotpose().getOrientation().getYaw()
                );

        if (gamepad1.a) {
            if (llPosition.distanceTo(position) > 5) {
                otosSensor.setPosition(llPosition);

            }
        }

        drivetrain.control(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        telemetry.addData("Position", position);
        telemetry.addLine();
        telemetry.addData("result is valid", latestResult.isValid());
        telemetry.addData("Limelight detection Position", llPosition);

    }

}
