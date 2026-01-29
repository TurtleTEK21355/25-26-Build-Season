package org.firstinspires.ftc.teamcode.opmode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.commands.logic.SimultaneousAndCommand;
import org.firstinspires.ftc.teamcode.commands.logic.TimerCommand;
import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.CloseGateCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.OpenGateCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.menu.DoubleMenuItem;
import org.firstinspires.ftc.teamcode.lib.menu.Menu;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.subsystems.shared.sensor.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.shared.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.shared.actuator.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.shoot.GateSystem;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShootHardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.shared.actuator.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shared.sensor.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShooterSystem;

public abstract class ShootAutoOpModeLinear extends LinearCommandOpMode { //the robots name is shoot
    protected ShootHardwareNames hardwareNames = new ShootHardwareNames();
    protected Drivetrain drivetrain;
    protected OTOSSensor otosSensor;
    protected AprilTagCamera aprilTagCamera;
    protected ShooterSystem shooterSystem;

    protected AllianceSide side;
    protected Pose2D startingPosition;

    protected double kp = 0.06;
    protected double ki;
    protected double kd;
    protected double kpTheta = 0.03;
    protected double kiTheta;
    protected double kdTheta;
    protected double speed = 0.6;
    protected final Pose2D SHOOT_POSITION = new Pose2D(-20,12,36);


    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";
    public int shootWaitTime = 300;
    public int lastShootWaitTime = 400;
    public int flyWheelVelocity = 1150;

    @Override
    public void initialize() {
        telemetry.addData("Starting Position", startingPosition.x + ", " +  startingPosition.y + ", " + startingPosition.h);
        TelemetryPasser.telemetry = telemetry;
        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(ShootHardwareNames.Name.APRIL_TAG_CAMERA)));
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(ShootHardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1.0, 1.0);
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor);
        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotorEx.class, hardwareNames.get(ShootHardwareNames.Name.SHOOTER_FLYWHEEL))),
                new GateSystem(
                        hardwareMap.get(Servo.class, hardwareNames.get(ShootHardwareNames.Name.SHOOTER_GATE)),
                        hardwareMap.get(Ada2167BreakBeam.class, hardwareNames.get(ShootHardwareNames.Name.BALL_READY_SENSOR))),
                new Intake(hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.INTAKE_MOTOR))), side);
        //configureVariables();
        drivetrain.configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));

        commands();

    }

    @Override
    public void cleanup() {
        blackboard.put(POSITION_BLACKBOARD_KEY, otosSensor.getPosition());
        blackboard.put(ALLIANCE_SIDE_BLACKBOARD_KEY, side);
    }

    public abstract void commands();

    public void setAllianceSide(AllianceSide side) {
        this.side = side;
    }

    public void setStartingPosition(Pose2D offset) {
        startingPosition = offset;
    }

    public void setStartingPosition(double x, double y, double h) {
        startingPosition = new Pose2D(x, y, h);
    }

    public void configureVariables(){
        double valueChangeAmount = 0.01;
        DoubleMenuItem speedItem =  new DoubleMenuItem(speed, valueChangeAmount, "Speed");
        DoubleMenuItem kpItem = new DoubleMenuItem(kp, valueChangeAmount, "Kp");
        DoubleMenuItem kiItem = new DoubleMenuItem(ki, valueChangeAmount, "Ki");
        DoubleMenuItem kdItem = new DoubleMenuItem(kd, valueChangeAmount, "Kd");
        DoubleMenuItem kpThetaItem = new DoubleMenuItem(kpTheta, valueChangeAmount, "KpTheta");
        DoubleMenuItem kiThetaItem = new DoubleMenuItem(kiTheta, valueChangeAmount, "KiTheta");
        DoubleMenuItem kdThetaItem = new DoubleMenuItem(kdTheta, valueChangeAmount, "KdTheta");

        Menu menu = new Menu();
        menu.add(speedItem, kpItem, kiItem, kdItem, kpThetaItem, kiThetaItem, kdThetaItem);

        while(!gamepad1.start) {
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

    /**
     * Does not stop the flywheel after shooting to maybe save time.
     * @param amount
     */
    public void shoot(int amount){
        if (amount < 0 || amount > 3) {
            throw new IllegalArgumentException("Shooting Amount must be between 0 and 3");
        }

        // This is switch-case abuse
        switch (amount) {
            case 0:
                break;
            case 1:
                addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain, true))));
                addCommand(new SimultaneousAndCommand((new OpenGateCommand(shooterSystem)), (new TimerCommand(1500))));

                addCommand(new StartIntakeCommand(shooterSystem));
                addCommand(new TimerCommand(shootWaitTime));
                addCommand(new StopIntakeCommand(shooterSystem));
            case 2:
                addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
                addCommand(new StartIntakeCommand(shooterSystem));
                addCommand(new TimerCommand(shootWaitTime));
                addCommand(new StopIntakeCommand(shooterSystem));
            case 3:
                addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
                addCommand(new StartIntakeCommand(shooterSystem));
                addCommand(new TimerCommand(lastShootWaitTime));
                addCommand(new StopIntakeCommand(shooterSystem));
            default:
                addCommand(new CloseGateCommand(shooterSystem));
        }

    }

}
