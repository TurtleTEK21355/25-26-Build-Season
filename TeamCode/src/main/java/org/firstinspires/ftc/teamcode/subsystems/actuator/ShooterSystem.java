package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;


public class ShooterSystem {
    private TurretSystem turretSystem;
    private ArtifactLift artifactLift;
    private CarouselSystem carouselSystem;
    private Intake intake;

    public ShooterSystem(TurretSystem turretSystem, ArtifactLift artifactLift, CarouselSystem carouselSystem, Intake intake){
        this.turretSystem = turretSystem;
        this.artifactLift = artifactLift;
        this.intake = intake;
        this.carouselSystem = carouselSystem;

    }

    public ArtifactLift getArtifactLift() {
        return artifactLift;
    }
    public CarouselSystem getCarouselSystem(){return carouselSystem;}

    public String teleOpTelemetry() {
        TelemetryString string = new TelemetryString();
        string.addLine("hello");
        return string.toString();
    }

    public void manualControls(double intake, double shooter, double carousel, double hood) {
        setFlywheelPower(shooter);
        TelemetryPasser.telemetry.addData("Flywheel Velocity:", getFlywheelVelocity());
        setIntakePower(intake);
        setCarouselPosition(carousel);
        TelemetryPasser.telemetry.addData("Carousel Position:", getCarouselPosition());
        setHoodAngle(hood);
        TelemetryPasser.telemetry.addData("Hood Angle:", hood);
    }
    public void manualControls(double intake, double shooter, double hood) {
        setFlywheelPower(shooter);
        TelemetryPasser.telemetry.addData("Flywheel Velocity:", getFlywheelVelocity());
        setIntakePower(intake);
        setHoodAngle(hood);
        TelemetryPasser.telemetry.addData("Hood Angle:", hood);
    }

    /**
     *
     * @param intake
     * @param shooter
     * @param hood
     * @param carousel
     * @param artifactLift
     */
    public void mainTeleOpWithoutTrajectoryMath(boolean intake, double shooter, double hood, ColorSensorPosition carousel, boolean artifactLift) {
        setHoodAngle(Range.clip(hood*0.5, 0, 0.5));
        if (intake) {
            setIntakePower(1);
        } else {
            setIntakePower(0);
        }
        setFlywheelPower(shooter);
        setCarouselPosition(carousel.getAbsolutePosition());
        setArtifactLiftState(artifactLift);
    }


    public void setFlywheelPower(double power) {
        turretSystem.setFlyWheelPower(power);
    }
    public void setFlywheelVelocity(double velocity) {
        turretSystem.setFlywheelVelocity(velocity);
    }
    public double getFlywheelVelocity() {
        return turretSystem.getFlywheelVelocity();
    }
    public void setIntakePower(double power) {
        intake.setPower(power);
    }
    public void setArtifactLiftState(boolean up) {
        if (up) {
            artifactLift.setLiftUpNoLimit();
        } else {
            artifactLift.setLiftDownNoLimit();
        }
    }
    public void setCarouselPosition(double position) {carouselSystem.setPosition(position);}
    public double getCarouselPosition() {return carouselSystem.getPosition();}

    public void setArtifactToShoot(ArtifactState state) {
        carouselSystem.setTargetArtifactToShoot(state);
    }
    public void setHoodAngle(double position) {turretSystem.setHoodAngle(position);}
}
