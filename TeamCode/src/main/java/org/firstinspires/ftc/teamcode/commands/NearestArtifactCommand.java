package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;

public class NearestArtifactCommand extends Command {
    CarouselSystem carouselSystem;

    public NearestArtifactCommand(CarouselSystem carouselSystem) {
        this.carouselSystem = carouselSystem;
    }

    @Override
    public void init() {
        carouselSystem.setNearestArtifactToShoot();
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
        return true;
    }

}