package org.firstinspires.ftc.teamcode.internal;

public class BallPathChangeThisNamePleaseIDKWhatToCallIt {
    FlyWheel flyWheel;
    Hopper hopper;
    Intake intake;

    public BallPathChangeThisNamePleaseIDKWhatToCallIt(FlyWheel flyWheel, Hopper hopper, Intake intake){
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
    //this is not permanent and very bad
    //there will be methods that handle moving balls around to the right place and shoot them all at the same time
}
