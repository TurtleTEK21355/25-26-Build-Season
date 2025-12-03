package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class ShooterSystem {
    private FlyWheel flyWheel;
    private GateSystem gateSystem;
    private Intake intake;

    private final double GRAVITY = 386.09; //Inches per second squared
    private final double HEIGHT = 40; //inches tall
    private final double THETA = 1.13446401; //Ramp Angle in Radians
    private final double MAX_SPEED = 386; //inches per second?
    private final double MAX_RPM = 7214; //this ones gotta be rpm hopefully
    private final double TICKS_PER_ROTATION = 28; //ticks per rotation of 5000 series motor


    public ShooterSystem(FlyWheel flyWheel, GateSystem gateSystem, Intake intake){
        this.flyWheel = flyWheel;
        this.intake = intake;
        this.gateSystem = gateSystem;

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

    public void teleOpControl(Pose2D position, boolean intakeForward, boolean shoot, double intakeBackward) {
        TelemetryPasser.telemetry.addData("shoot", ballReady());
        double flyWheelTargetSpeed = getTicksPerSecondForPosition(position);
        flywheelSetVelocity(Range.clip(flyWheelTargetSpeed, 600, 1500));
        if (intakeForward) {
            intake.setPower(0.8);
        } else if (intakeBackward > 0.1)
        {intake.setPower(-0.7);} else {
            intake.setPower(0);
        }
        if (shoot && (flywheelGetVelocity() > (flyWheelTargetSpeed-80))) { //what does minus 80 mean???!?!?!?!??!
            gateSystem.openGate();
        } else {
            gateSystem.closeGate();}

    }
    public void teleOpControlConfigurableVelocity(double velocity, boolean intakeForward, boolean shoot, double intakeBackward) {
        TelemetryPasser.telemetry.addData("shoot", ballReady());
        flyWheel.setVelocity(velocity);
        if (intakeForward) {
            intake.setPower(0.8);
        } else if (intakeBackward > 0.1)
        {intake.setPower(-0.7);} else {
            intake.setPower(0);
        }
        if (shoot &&(flywheelGetVelocity() > (velocity-80))) {
            gateSystem.openGate();
        } else {
            gateSystem.closeGate();}

    }

    private double getRange(Pose2D position) {
        return Math.sqrt(Math.pow(position.x-67.215, 2)+Math.pow(position.y+74.871, 2));
        //TODO: GET RID OF NUMBERS
    }

    private double getTicksPerSecondForPosition(Pose2D position) {

        double range = getRange(position);

        double expectedVelocity = (
                Math.sqrt(
                    (
                        -GRAVITY*Math.pow(range, 2)
                    )
                    /
                    (
                        2*Math.pow(Math.cos(THETA), 2)
                        *
                        (HEIGHT - range * Math.tan(THETA))
                    )
                )
            );

        double adjustedForMaxSpeed  = expectedVelocity/MAX_SPEED;

        double rotationPerMinute = adjustedForMaxSpeed/MAX_RPM;

        double ticksPerSecond = (rotationPerMinute/60)*TICKS_PER_ROTATION;

        return ticksPerSecond;
    }

}
