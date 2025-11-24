package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.internal.AprilTagCamera;
import org.firstinspires.ftc.teamcode.internal.Command;
import org.firstinspires.ftc.teamcode.internal.CommandList;
import org.firstinspires.ftc.teamcode.internal.DoubleMenuItem;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.HardwareNames;
import org.firstinspires.ftc.teamcode.internal.Menu;
import org.firstinspires.ftc.teamcode.internal.MovePIDCommand;
import org.firstinspires.ftc.teamcode.internal.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.internal.OTOSSensor;
import org.firstinspires.ftc.teamcode.internal.PIDConstants;
import org.firstinspires.ftc.teamcode.internal.Pose2D;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@Autonomous(name="Test Auto Phil", group="Linear OpMode")
public class BasicGarbage extends LinearOpMode {
    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    AprilTagCamera aprilTagCamera;
    double kp = 0.05;
    double ki;
    double kd;
    double kpTheta = 0.03;
    double kiTheta;
    double kdTheta;
    double speed = 0.5;
    double valueChangeAmount = 0.01;
    HardwareNames hardwareNames = new HardwareNames();

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryPasser.telemetry = telemetry;

        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(HardwareNames.Name.APRIL_TAG_CAMERA)));

        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(HardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);

        waitForStart();

        configureVariables();

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor);
        drivetrain.configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta), 0, 0, 0);

        CommandList commands = new CommandList();

        commands.add(new MovePIDHoldTimeCommand(new Pose2D(10, 0, 0),1000, speed, drivetrain));
        commands.add(new MovePIDHoldTimeCommand(new Pose2D(10, 10, 90), 1000, speed, drivetrain));
        commands.add(new MovePIDHoldTimeCommand(new Pose2D(10, 10, 135), 1000, speed, drivetrain));
        commands.add(new MovePIDHoldTimeCommand(new Pose2D(10, 0, -135), 1000, speed, drivetrain));
        commands.add(new MovePIDHoldTimeCommand(new Pose2D(0, 0, -90), 1000, speed, drivetrain));
        commands.add(new MovePIDHoldTimeCommand(new Pose2D(0, 0, 0), 1000, speed, drivetrain));

        for (Command command : commands) {
            do {
                command.loop();
                telemetry.update();
            } while (!command.isCompleted() && opModeIsActive());
        }

    }

    public void configureVariables(){
        DoubleMenuItem speedItem =  new DoubleMenuItem(speed, valueChangeAmount, "Speed");
        DoubleMenuItem kpItem = new DoubleMenuItem(kp, valueChangeAmount, "Kp");
        DoubleMenuItem kiItem = new DoubleMenuItem(ki, valueChangeAmount, "Ki");
        DoubleMenuItem kdItem = new DoubleMenuItem(kd, valueChangeAmount, "Kd");
        DoubleMenuItem kpThetaItem = new DoubleMenuItem(kpTheta, valueChangeAmount, "KpTheta");
        DoubleMenuItem kiThetaItem = new DoubleMenuItem(kiTheta, valueChangeAmount, "KiTheta");
        DoubleMenuItem kdThetaItem = new DoubleMenuItem(kdTheta, valueChangeAmount, "KdTheta");

        Menu menu = new Menu();
        menu.add(speedItem, kpItem, kiItem, kdItem, kpThetaItem, kiThetaItem, kdThetaItem);

        while(opModeIsActive() && !gamepad1.start) {
            menu.itemSelection(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left);

            telemetry.addLine("Press start to Start");

            telemetry.addLine(menu.reportMenuItemValue());
            telemetry.update();

        }

        speed = speedItem.getValue();
        kp = kpItem.getValue();
        ki = kiItem.getValue();
        kd = kdItem.getValue();
        kpTheta = kpThetaItem.getValue();
        kiTheta = kiThetaItem.getValue();
        kdTheta = kdThetaItem.getValue();

    }
}