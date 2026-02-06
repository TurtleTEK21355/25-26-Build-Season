package org.firstinspires.ftc.teamcode.opmode.test.sensor;

//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry; this also broken
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
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.subsystems.StateHardwareName;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.sensor.AprilTagCamera;


@TeleOp(name="Drivetrain + Odometry")
public class OdometryTest extends LinearOpMode {
    private StateRobot robot;
    private AllianceSide side;
    private Pose2D startingPosition;


    public void runOpMode() {
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
            GamepadManager virtualGamepad1 = virtualGamepad.getFirstManager();
            GamepadManager virtualGamepad2 = virtualGamepad.getSecondManager();
            robot.drivetrainFCControl(-gamepad1.left_stick_y-virtualGamepad1.getLeftStickY(), gamepad1.left_stick_x+ virtualGamepad1.getLeftStickX(), gamepad1.right_stick_x+ virtualGamepad1.getRightStickX());
            combined.addData("Position: ", robot.getPosition());
            combined.update();
        }
    }
}
