package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;

public class SelectArtifactCommand extends Command {
    CarouselSystem carouselSystem;
    ArtifactState artifactState;

    public SelectArtifactCommand(CarouselSystem carouselSystem, ArtifactState artifactState) {
        this.carouselSystem = carouselSystem;
        this.artifactState = artifactState;
    }

    @Override
    public void init() {
        carouselSystem.setTargetArtifactToShoot(artifactState);
    }

    @Override
    public void loop() {
        //loop code
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        //string.addData("label", var);
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return carouselSystem.shootSlotIsTarget();
    }

}