package org.firstinspires.ftc.teamcode.subsystems.actuator;

import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;

public class ShooterSystem {
    private TurretSystem turretSystem;
    private ArtifactLift artifactLift;
    private CarouselSystem carouselSystem;
    private Intake intake;
    private Motif motif;

    public ShooterSystem(TurretSystem turretSystem, ArtifactLift artifactLift, CarouselSystem carouselSystem, Intake intake){
        this.turretSystem = turretSystem;
        this.artifactLift = artifactLift;
        this.intake = intake;
        this.carouselSystem = carouselSystem;

    }

    public String teleOpTelemetry() {
        TelemetryString string = new TelemetryString();
        string.addLine("hello");
        return string.toString();
    }

    public void setFlywheelPower(double power) {
        turretSystem.setFlyWheelPower(power);
    }
    public void setFlywheelVelocity(double velocity) {
        turretSystem.setFlywheelVelocity(velocity);
    }
    public double getFlywheelVelocity(double velocity) {
        return turretSystem.getFlywheelVelocity();
    }
    public void setIntakePower(double power) {
        intake.setPower(power);
    }
    public void setArtifactLiftState(boolean up) {
        if (up) {
            artifactLift.setLiftUp();
        } else {
            artifactLift.setLiftDown();
        }
    }
    public void setCarouselPosition(double position) {carouselSystem.setPosition(position);}
    public double getCarouselPosition() {return carouselSystem.getPosition();}

    public void setArtifactToShoot(ArtifactState state) {
        carouselSystem.setArtifactToShoot(state);
    }
    public void setHoodPosition(double position) {turretSystem.setHoodAngle(position);}
}
