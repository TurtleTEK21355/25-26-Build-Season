package org.firstinspires.ftc.teamcode.internal;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

import com.qualcomm.robotcore.util.ElapsedTime;

public class ShooterSystem {
    private FlyWheel flyWheel;
    private Hopper hopper;
    private Intake intake;
    ElapsedTime flyWheelTimer = new ElapsedTime();
    ElapsedTime generalTimer = new ElapsedTime();
    private enum Mode {NORMAL, SHOOT};
    private Mode mode = Mode.NORMAL;
    static double GRAVITY = 386.09; //Inches per second squared
    static double HEIGHT = 40; //inches tall

    static double THETA = 1.13446401; //Ramp Angle in Radians

    static double maxSpeed = 388.590;


    public ShooterSystem(FlyWheel flyWheel, Hopper hopper, Intake intake){
        this.flyWheel = flyWheel;
        this.hopper = hopper;
        this.intake = intake;

    }

    public void flywheelSetPower(boolean enabled, double power) {
        if (enabled) {
            flyWheel.setPower(power);
        }
    }
    public void HopperSetPower(double power) {
        hopper.setPower(power);
    }
    public void IntakeSetPower(double power) {
        intake.setPower(power);
    }

    public void autoShoot(double range) {
        double timer = 0;
        double power = (Math.sqrt((-GRAVITY*Math.pow(range, 2))/(2*Math.pow(cos(THETA), 2)*(HEIGHT - range * tan(THETA)))))/maxSpeed;
        flyWheel.setPower(power);
        while (flyWheel.getPower() < power-0.075);
        hopper.openGate();
        generalTimer.startTime();
        hopper.setPower(1);
        while(generalTimer.milliseconds()<1250);
        generalTimer.reset();
        hopper.setPower(0);
        flyWheel.setPower(0);
        hopper.closeGate();


    }
    public void teleOpControl(boolean shoot, boolean intakeSpin, boolean hopperspinforward, boolean gate, boolean hopperspinbackward) {
        TelemetryPasser.telemetry.addData("shoot", hopper.ballReady());
        if(hopperspinforward) {
            hopper.setPower(1);
        } else if (hopperspinbackward) {
            hopper.setPower(-1);
        } else {hopper.setPower(0);}
        if (shoot) {
            flyWheel.setPower(0.8);
        } else {flyWheel.setPower(0);}
        if (intakeSpin) {
            intake.setPower(0.8);
        } else {intake.setPower(0);}
        if (gate) {
            hopper.openGate();
        } else {hopper.closeGate();}
//        if (shoot) {      //if a ball is detected in the top and the shoot button is pressed
//            TelemetryPasser.telemetry.addData("shoot", hopper.ballReady());
//            flyWheel.setPower(0.8);       //turn on the flywheel
//            flyWheelTimer.startTime();      //start the timer for the shooter to run
//            flyWheelTimer.reset(); //reset it for good measure
//            mode = Mode.SHOOT;      //this is so theres no issue with setting the power of things to the wrong level on the bottom
//
//        }
//
//        if (mode == Mode.SHOOT) {       //while the flywheel is shooting
//            if (flyWheelTimer.milliseconds() > 1000) {      //if the shooting timer is up it will turn off everything and set back to non shooting mode
//                flyWheel.setPower(0);
//                hopper.setPower(0);
//                hopper.closeGate();
//                flyWheelTimer.reset();
//                mode = Mode.NORMAL;
//
//            } else if (flyWheelTimer.milliseconds() > 400) {
//                hopper.openGate();
//                hopper.setPower(1);
//
//            }
//
//            if (intakeSpin) {
//                intake.setPower(0.5);
//
//            }
//
//        }
//
//        if (mode == Mode.NORMAL) {
//            if (intakeSpin && !hopper.ballReady()) {
//                intake.setPower(0.7);
//                hopper.setPower(1);
//
//            } else if (intakeSpin) {
//                intake.setPower(0.7);
//
//            }else {
//                intake.setPower(0);
//                hopper.setPower(0);
//                flyWheelTimer.reset();
//
//            }
//
//        }
//
//    TelemetryPasser.telemetry.addData("ball at top", hopper.ballReady());

    }

}
