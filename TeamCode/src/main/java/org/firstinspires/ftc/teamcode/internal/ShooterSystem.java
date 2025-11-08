package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ShooterSystem {
    FlyWheel flyWheel;
    Hopper hopper;
    Intake intake;
    ElapsedTime flyWheelTimer = new ElapsedTime();
    private enum Mode {NORMAL, SHOOT};
    private Mode mode = Mode.NORMAL;


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
    public void flywheelSetPower(double power) {
            flyWheel.setPower(power);
    }
    public void hopperSetPower(double power) {
        hopper.setPower(power);
    }
    public void IntakeSetPower(double power) {
        intake.setPower(power);
    }

    public void teleOpControl(boolean intakeSpin, boolean shoot) {
        if (hopper.ballReady() && shoot) {      //if a ball is detected in the top and the shoot button is pressed
            flyWheel.setPower(0.3);       //turn on the flywheel
            flyWheelTimer.startTime();      //start the timer for the shooter to run
            flyWheelTimer.reset();      //reset it for good measure
            mode = Mode.SHOOT;      //this is so theres no issue with setting the power of things to the wrong level on the bottom

        }

        if (mode == Mode.SHOOT) {       //while the flywheel is shooting
            if (flyWheelTimer.milliseconds() > 1000) {      //if the shooting timer is up it will turn off everything and set back to non shooting mode
                flyWheel.setPower(0.3);
                hopper.setPower(0);
                hopper.closeGate();
                flyWheelTimer.reset();
                mode = Mode.NORMAL;

            } else if (flyWheelTimer.milliseconds() > 500) {
                hopper.openGate();
                hopper.setPower(1);

            }

            if (intakeSpin) {
                intake.setPower(0.7);

            }

        }

        if (mode == Mode.NORMAL) {
            if (intakeSpin && !hopper.ballReady()) {
                intake.setPower(0.7);
                hopper.setPower(1);

            } else if (intakeSpin) {
                intake.setPower(0.7);

            }else {
                intake.setPower(0);
                hopper.setPower(0);
                flyWheelTimer.reset();

            }

        }

    TelemetryPasser.telemetry.addData("ball at top", hopper.ballReady());

    }
    public void testWheels(boolean intakeSpin, boolean shoot) {
        if (intakeSpin) {
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }
        if (shoot) {
            flyWheel.setPower(1);
        } else {
            flyWheel.setPower(0);
        }
    }

}
