package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;


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
    public Hood getHood(){return turretSystem.getHood();};

    public String teleOpTelemetry() {
        TelemetryString string = new TelemetryString();
        string.addLine("hello");
        return string.toString();
    }

    public void manualControls(double intakePower, double flywheelPower, double hood) {
        setFlywheelPower(flywheelPower);
        TelemetryPasser.telemetry.addData("Flywheel Velocity:", getFlywheelVelocity());
        setIntakePower(intakePower);
        setHoodAngle(hood);
        TelemetryPasser.telemetry.addData("Hood Angle:", hood);
    }

    /**
     *
     * @param intake
     * @param shooter
     * @param hood
     * @param carouselPosition
     * @param artifactLift
     */
    public void mainTeleOpWithoutTrajectoryMath(boolean intake, double shooter, double hood, CarouselPosition carouselPosition, boolean artifactLift) {
        setHoodAngle(Range.clip(hood*0.5, 0, 0.5));
        if (intake) {
            setIntakePower(1);
        } else {
            setIntakePower(0);
        }
        setFlywheelPower(shooter);
        setCarouselPosition(carouselPosition);
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

    public void setHoodAngle(double position) {
        turretSystem.setHoodAngle(position);
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

    public CarouselPosition getCarouselPosition() {
        return carouselSystem.getPosition();
    }
    public void setCarouselPosition(CarouselPosition position) {
        carouselSystem.setPosition(position);
    }
    public void setArtifactToShoot(ArtifactState state) {
        carouselSystem.setTargetArtifactToShoot(state);
    }

}
