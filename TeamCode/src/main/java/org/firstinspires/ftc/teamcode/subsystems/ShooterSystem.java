package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.AllianceSide;
import org.firstinspires.ftc.teamcode.Motif;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class ShooterSystem {
    private FlyWheel flyWheel;
    private GateSystem gateSystem;
    private Intake intake;

    private AllianceSide side;
    private final Pose2D redBasketPosition = new Pose2D(56.4, 60, 0);
    private final Pose2D blueBasketPosition = new Pose2D(-56.4, 60, 0);
    private Motif motif;

    double tpsChange = 0;
    private final int FLYWHEEL_VELOCITY_TOLERANCE_TPS = 40;

    private final double GRAVITY = 386.09; //Inches per second squared
    private final double HEIGHT = 48; //inches tall + ball diameter
    private final double THETA = 1.13446401; //Ramp Angle in Radians
    private final double MAX_SPEED = 386; //inches per second?
    private final double REGRESSION_VARIABLE = 5.43557;
    private final double MAX_RPM = 3214; //this ones gotta be rpm hopefully
    private final double TICKS_PER_ROTATION = 28; //ticks per rotation of 5000 series motor
    ElapsedTime velocityChangeTimer = new ElapsedTime();


    public ShooterSystem(FlyWheel flyWheel, GateSystem gateSystem, Intake intake, AllianceSide side){
        this.flyWheel = flyWheel;
        this.intake = intake;
        this.gateSystem = gateSystem;
        this.side = side;
    }

    public void flywheelSetVelocity(double velocity) {
            flyWheel.setVelocity(velocity);
    }
    public double flywheelGetVelocity() {
        return flyWheel.getVelocity();
    }
    public void intakeSetPower(double power) {
        intake.setPower(power);
    }
    public void openGate() {
        gateSystem.openGate();
    }
    public void closeGate() {
        gateSystem.closeGate();
    }
    public boolean ballReady() {return gateSystem.ballReady();}

    public void teleOpControl(Pose2D position, boolean intakeForward, boolean shoot, double intakeBackward, boolean add, boolean minus) {
        double range = getDistanceFromGoal(side, position);
        TelemetryPasser.telemetry.addData("Range from Goal:", range);
        double flyWheelTargetSpeed = getTicksPerSecondForRange(range);
        TelemetryPasser.telemetry.addData("Calculated Ticks Per second:", flyWheelTargetSpeed);
        if (add && velocityChangeTimer.milliseconds()>25) {
            tpsChange += 10;
        }
        double editedTicksPerSecond = Math.sqrt(flyWheelTargetSpeed) * 3.5;
        if (editedTicksPerSecond > 1500){
            editedTicksPerSecond = 1500;
        }
        else if (minus && velocityChangeTimer.milliseconds()>25) {
            tpsChange -= 10;
        }
        editedTicksPerSecond = (flyWheelTargetSpeed)+tpsChange;
        flywheelSetVelocity(Range.clip(editedTicksPerSecond, -1500, 1500));
        TelemetryPasser.telemetry.addData("Edited: ", editedTicksPerSecond);
        if (shoot && (flywheelGetVelocity() > (editedTicksPerSecond- FLYWHEEL_VELOCITY_TOLERANCE_TPS))) {
            openGate();
        } else {
            closeGate();
        }
        TelemetryPasser.telemetry.addData("ball ready", ballReady());
        if (intakeForward) {
            intakeSetPower(1);
        }
        else if (intakeBackward > 0.1) {
             intakeSetPower(-0.8);
        } else {
            intakeSetPower(0);
        }

        TelemetryPasser.telemetry.addData("FlyWheel Velocity in ticks/s", flyWheel.getVelocity());

    }
    public void teleOpControlConfigurableVelocity(double velocity, boolean intakeForward, boolean shoot, double intakeBackward) {
        flyWheel.setVelocity(velocity);
        if (intakeForward) {
            intake.setPower(0.8);
        } else if (intakeBackward > 0.1)
        {intake.setPower(-0.7);} else {
            intake.setPower(0);
        }
        if (shoot &&(flywheelGetVelocity() > (velocity- FLYWHEEL_VELOCITY_TOLERANCE_TPS))) {
            gateSystem.openGate();
        } else {
            gateSystem.closeGate();}

        TelemetryPasser.telemetry.addData("FlyWheel Velocity in ticks/s", flyWheel.getVelocity());
        TelemetryPasser.telemetry.addData("shoot", ballReady());
    }

    private double getDistanceFromGoal(AllianceSide side, Pose2D position) {
        if (side == AllianceSide.RED) {
            return Math.sqrt(Math.pow(position.x - redBasketPosition.x, 2) + Math.pow(position.y - redBasketPosition.y, 2));

        } else {
            return Math.sqrt(Math.pow(position.x - blueBasketPosition.x, 2) + Math.pow(position.y - blueBasketPosition.y, 2));

        }

    }

    private double getTicksPerSecondForRange(double range) {

        double expectedVelocity = (
            REGRESSION_VARIABLE *
                    Math.sqrt((-GRAVITY*(Math.pow(range, 2)))
                            /
                    ((2*Math.cos(THETA)) * (HEIGHT-(range*Math.tan(THETA)))))
        );

        double adjustedForMaxSpeed  = expectedVelocity / MAX_SPEED; //seems weird to divide by the max?

        double rotationsPerMinute = adjustedForMaxSpeed * MAX_RPM; //also weird...

        return (rotationsPerMinute / 60) * TICKS_PER_ROTATION; //conversion from rots per min to ticks per sec
    }

    public Motif getMotif() {
        return motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }
}
