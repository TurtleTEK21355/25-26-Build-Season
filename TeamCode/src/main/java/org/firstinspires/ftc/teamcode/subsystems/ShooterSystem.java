package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.ElapsedTime;

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


    public ShooterSystem(FlyWheel flyWheel, GateSystem gateSystem, Intake intake){
        this.flyWheel = flyWheel;
        this.intake = intake;

    }

    public void flywheelSetPower(double power) {
            flyWheel.setPower(power);
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
    public void teleOpControl(double shoot, boolean intakeSpin, boolean gate) {
        TelemetryPasser.telemetry.addData("shoot", gateSystem.ballReady());
        flyWheel.setPower(shoot);
        if (intakeSpin) {
            intake.setPower(0.8);
        } else {intake.setPower(0);}
        if (gate) {
            gateSystem.openGate();
        } else {
            gateSystem.closeGate();}
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
