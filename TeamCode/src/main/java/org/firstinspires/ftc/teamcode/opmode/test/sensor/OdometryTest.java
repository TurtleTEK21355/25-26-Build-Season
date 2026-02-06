package org.firstinspires.ftc.teamcode.opmode.test.sensor;


import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.gamepad.GamepadManager;
import com.bylazar.gamepad.PanelsGamepad;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.subsystems.HardwareName;
import org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
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
    private ProgrammingRobot robot;


    public void runOpMode() {
//        Telemetry combined = new MultipleTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
//        TelemetryPasser.telemetry = combined;
//        PanelsGamepad virtualGamepad = PanelsGamepad.INSTANCE;
        TelemetryPasser.telemetry = telemetry;
        side = (AllianceSide) blackboard.getOrDefault(ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        startingPosition = (Pose2D) blackboard.getOrDefault(POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));
        webCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, HardwareName.VISION_SENSOR.getName()));
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, HardwareName.ODOMETRY_SENSOR.getName()));
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, HardwareName.FRONT_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.FRONT_RIGHT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.BACK_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.BACK_RIGHT_MOTOR.getName()));
        otosSensor.resetPosition();
//        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1, (double) 3600 /(3600-16.8));
        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1);
//        Telemetry combined = new MultipleTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
//        TelemetryPasser.telemetry = combined;
//        PanelsGamepad virtualGamepad = PanelsGamepad.INSTANCE;
        side = (AllianceSide) blackboard.getOrDefault(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        startingPosition = (Pose2D) blackboard.getOrDefault(StateRobot.POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));

        waitForStart();
        while (opModeIsActive()){
            Pose2D position = otosSensor.getPosition();
//            GamepadManager virtualGamepad1 = virtualGamepad.getFirstManager();
//            GamepadManager virtualGamepad2 = virtualGamepad.getSecondManager();
//            drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, side, position);
//            drivetrain.powerTelemetry();
//            telemetry.addData("Position: ", position);
//            telemetry.update();
        }
    }
}
