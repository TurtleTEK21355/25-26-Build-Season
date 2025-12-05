package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.commands.CloseGateCommand;
import org.firstinspires.ftc.teamcode.commands.OpenGateCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.SimultaneousCommand;
import org.firstinspires.ftc.teamcode.commands.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.commands.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.menu.DoubleMenuItem;
import org.firstinspires.ftc.teamcode.lib.menu.Menu;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.subsystems.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.GateSystem;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

@Autonomous(name="Auto Back Blue (Invasive)", group="Autonomous")
public class AutoBackBlueInvasive extends CommandOpMode{
    HardwareNames hardwareNames = new HardwareNames();
    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    AprilTagCamera aprilTagCamera;
    ShooterSystem shooterSystem;
    double kp = 0.06;
    double ki;
    double kd;
    double kpTheta = 0.03;
    double kiTheta;
    double kdTheta;
    double speed = 0.3;
    double valueChangeAmount = 0.01;
    int shootWaitTime = 300;
    int lastShootWaitTime = 400;

    int flyWheelVelocity = 1150;

    @Override
    public void initialize() {
        TelemetryPasser.telemetry = telemetry;
        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(HardwareNames.Name.APRIL_TAG_CAMERA)));
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(HardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(-15, -61, 0, DistanceUnit.INCH, AngleUnit.DEGREES, 1.0, 1.0);
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor);
        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotorEx.class, hardwareNames.get(HardwareNames.Name.SHOOTER_FLYWHEEL))),
                new GateSystem(
                        hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.SHOOTER_GATE)),
                        hardwareMap.get(Ada2167BreakBeam.class, hardwareNames.get(HardwareNames.Name.BALL_READY_SENSOR))),
                new Intake(hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.INTAKE_MOTOR))));
//        configureVariables();
        drivetrain.configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));

        addCommand(new SimultaneousCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(new Pose2D(-16, 16, 45),1000, speed, drivetrain))));
        addCommand(new OpenGateCommand(shooterSystem));
        addCommand(new TimerCommand(1000));

        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
//        addCommand(new UntilBallReadyCommand(shooterSystem, false));
        addCommand(new StopIntakeCommand(shooterSystem));

        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
//        addCommand(new UntilBallReadyComm447and(shooterSystem, false));
        addCommand(new StopIntakeCommand(shooterSystem));

        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(lastShootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));

        addCommand(new CloseGateCommand(shooterSystem));
        addCommand(new SetFlywheelCommand(shooterSystem, 0));
        addCommand(new TimerCommand(400));
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-16, -24, 0),1500, speed, drivetrain));


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
