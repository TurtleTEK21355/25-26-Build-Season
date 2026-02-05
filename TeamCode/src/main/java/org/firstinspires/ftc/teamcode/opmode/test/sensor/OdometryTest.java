package org.firstinspires.ftc.teamcode.opmode.test.sensor;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.StateHardwareName;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

@TeleOp
public class OdometryTest extends LinearOpMode {
    protected Drivetrain drivetrain;
    protected OTOSSensor otosSensor;
    protected AprilTagCamera webCamera;
    protected AprilTagCamera limelightCamera;
    protected AllianceSide side;
    protected Pose2D startingPosition;
    protected double kp = 0.05;
    protected double ki;
    protected double kd;
    protected double kpTheta = 0.03;
    protected double kiTheta;
    protected double kdTheta;
    protected double speed = 0.5;
    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";


    public void runOpMode() {

        TelemetryPasser.telemetry = telemetry;
        side = (AllianceSide) blackboard.getOrDefault(ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        startingPosition = (Pose2D) blackboard.getOrDefault(POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));
        webCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, StateHardwareName.VISION_SENSOR.getName()));
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, StateHardwareName.ODOMETRY_SENSOR.getName()));
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, StateHardwareName.FRONT_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.FRONT_RIGHT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.BACK_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.BACK_RIGHT_MOTOR.getName()));
        otosSensor.resetPosition();
        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1);

        waitForStart();
        while (opModeIsActive()){
            Pose2D position = otosSensor.getPosition();
            drivetrain.fcControl(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 0, position);
            telemetry.addData("Position: ", position.toString());
            drivetrain.powerTelemetry();

            telemetry.update();
        }
    }
}
