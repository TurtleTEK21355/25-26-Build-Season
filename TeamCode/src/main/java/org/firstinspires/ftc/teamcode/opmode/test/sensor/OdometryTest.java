package org.firstinspires.ftc.teamcode.opmode.test.sensor;

//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.bylazar.gamepad.GamepadManager;
//import com.bylazar.gamepad.PanelsGamepad;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry; this also broken
import com.bylazar.gamepad.GamepadManager;
import com.bylazar.gamepad.PanelsGamepad;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.subsystems.StateHardwareName;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.sensor.AprilTagCamera;


@TeleOp(name="Drivetrain + Odometry")
public class OdometryTest extends LinearOpMode {
    protected Drivetrain drivetrain;
    protected OTOSSensor otosSensor;
    protected AprilTagCamera webCamera;
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
    private StateRobot robot;
    private AllianceSide side;
    private Pose2D startingPosition;


    public void runOpMode() {
//        Telemetry combined = new MultipleTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
//        TelemetryPasser.telemetry = combined;
//        PanelsGamepad virtualGamepad = PanelsGamepad.INSTANCE;
        TelemetryPasser.telemetry = telemetry;
        side = (AllianceSide) blackboard.getOrDefault(ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        startingPosition = (Pose2D) blackboard.getOrDefault(POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));
        webCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, StateHardwareName.VISION_SENSOR.getName()));
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, StateHardwareName.ODOMETRY_SENSOR.getName()));
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, StateHardwareName.FRONT_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.FRONT_RIGHT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.BACK_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.BACK_RIGHT_MOTOR.getName()),
                otosSensor);
        otosSensor.resetPosition();
//        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1, (double) 3600 /(3600-16.8));
        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1);
//        Telemetry combined = new MultipleTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry()); this broken for me
        TelemetryPasser.telemetry = combined;
        PanelsGamepad virtualGamepad = PanelsGamepad.INSTANCE;

        robot = StateRobot.build();

        robot.resetPosition();
        robot.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1);
        side = (AllianceSide) blackboard.getOrDefault(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        startingPosition = (Pose2D) blackboard.getOrDefault(StateRobot.POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));

        waitForStart();
        while (opModeIsActive()){
//            GamepadManager virtualGamepad1 = virtualGamepad.getFirstManager();
//            GamepadManager virtualGamepad2 = virtualGamepad.getSecondManager();
            drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0);
//            drivetrain.fcControl(-gamepad1.left_stick_y-virtualGamepad1.getLeftStickY(), gamepad1.left_stick_x+ virtualGamepad1.getLeftStickX(), gamepad1.right_stick_x+ virtualGamepad1.getRightStickX(), 0);
//            combined.addData("Position: ", drivetrain.getPosition());
            telemetry.addData("Position: ", drivetrain.getPosition());

            drivetrain.powerTelemetry();
//            combined.update();
            telemetry.update();
            GamepadManager virtualGamepad1 = virtualGamepad.getFirstManager();
            GamepadManager virtualGamepad2 = virtualGamepad.getSecondManager();
            robot.drivetrainFCControl(-gamepad1.left_stick_y-virtualGamepad1.getLeftStickY(), gamepad1.left_stick_x+ virtualGamepad1.getLeftStickX(), gamepad1.right_stick_x+ virtualGamepad1.getRightStickX());
            combined.addData("Position: ", robot.getPosition());
            combined.update();
        }
    }
}
