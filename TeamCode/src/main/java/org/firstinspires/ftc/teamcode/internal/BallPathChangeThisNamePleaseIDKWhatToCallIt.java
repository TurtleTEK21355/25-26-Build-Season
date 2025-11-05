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

    public void flywheelSpin(boolean enabled, double power) {
        if (enabled) {
            flyWheel.spin(power);
        }
    }
    public void HopperSpin(double power) {
        hopper.spin(power);
    }
    public void IntakeSpin(double power) {
        intake.spin(power);
    }
    //this is not permanent and very bad
    //there will be methods that handle moving balls around to the right place and shoot them all at the same time
}
