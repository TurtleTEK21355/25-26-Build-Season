package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TelemetryPasser;

public class ShooterSystem {
    private FlyWheel flyWheel;
    private GateSystem gateSystem;
    private Intake intake;
    ElapsedTime flyWheelTimer = new ElapsedTime();
    ElapsedTime generalTimer = new ElapsedTime();
    private enum Mode {NORMAL, SHOOT};
    private Mode mode = Mode.NORMAL;
    static double GRAVITY = 386.09; //Inches per second squared
    static double HEIGHT = 40; //inches tall
    static double THETA = 1.13446401; //Ramp Angle in Radians
    static double maxSpeed = 388.590;
    private double expectedVelocity;


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

//    public void autoShoot(double range) {
//        double timer = 0;
//        double power = (Math.sqrt((-GRAVITY*Math.pow(range, 2))/(2*Math.pow(cos(THETA), 2)*(HEIGHT - range * tan(THETA)))))/maxSpeed;
//        flyWheel.setPower(power);
//        while (flyWheel.getPower() < power-0.075);
//        hopper.openGate();
//        generalTimer.startTime();
//        hopper.setPower(1);
//        while(generalTimer.milliseconds()<1250);
//        generalTimer.reset();
//        hopper.setPower(0);
//        flyWheel.setPower(0);
//        hopper.closeGate();
//
//    }

    public void teleOpControl(double range, boolean intakeForward, boolean shoot, double intakeBackward) {
        TelemetryPasser.telemetry.addData("shoot", ballReady());
        expectedVelocity = (Math.sqrt((-GRAVITY*Math.pow(range, 2))/(2*Math.pow(Math.cos(THETA), 2)*(HEIGHT - range * Math.tan(THETA)))))/maxSpeed;
        flyWheel.setVelocity(Range.clip(expectedVelocity, 600, 1500));
        if (intakeForward) {
            intake.setPower(0.8);
        } else if (intakeBackward > 0.1)
        {intake.setPower(-0.7);} else {
            intake.setPower(0);
        }
        if (shoot &&(flywheelGetVelocity() > (expectedVelocity-80))) {
            gateSystem.openGate();
        } else {
            gateSystem.closeGate();}

    }
    public void teleOpControlConfigurableVelocity(double range, boolean intakeForward, boolean shoot, double intakeBackward, double velocity) {
        TelemetryPasser.telemetry.addData("shoot", ballReady());
        flyWheel.setVelocity(velocity);
        if (intakeForward) {
            intake.setPower(0.8);
        } else if (intakeBackward > 0.1)
        {intake.setPower(-0.7);} else {
            intake.setPower(0);
        }
        if (shoot &&(flywheelGetVelocity() > (expectedVelocity-80))) {
            gateSystem.openGate();
        } else {
            gateSystem.closeGate();}

    }

    public void philTeleOpControl(boolean shoot, boolean intake, double range) {
        if (shoot) {      //if a ball is detected in the top and the shoot button is pressed
            TelemetryPasser.telemetry.addData("shoot", ballReady());
            expectedVelocity = (Math.sqrt((-GRAVITY*Math.pow(range, 2))/(2*Math.pow(Math.cos(THETA), 2)*(HEIGHT - range * Math.tan(THETA)))))/maxSpeed;
            flywheelSetVelocity(Range.clip(expectedVelocity, 600, 1500));
            flyWheelTimer.reset(); //reset timer to start
            mode = Mode.SHOOT;      //this is so theres no issue with setting the power of things to the wrong level on the bottom

        }

        if (mode == Mode.SHOOT) {       //while the flywheel is shooting
            if (flyWheelTimer.milliseconds() > 1000) {      //if the shooting timer is up it will turn off everything and set back to non shooting mode
                flywheelSetVelocity(0);
                intakeSetPower(0);
                closeGate();
                flyWheelTimer.reset();
                mode = Mode.NORMAL;

            } else if (flyWheelTimer.milliseconds() > 400) {
                openGate();
                intakeSetPower(1);

            }

        }

        if (mode == Mode.NORMAL) {
            if (intake) {
                intakeSetPower(0.8);

            } else {
                intakeSetPower(0);
            }
        }

    }

}
