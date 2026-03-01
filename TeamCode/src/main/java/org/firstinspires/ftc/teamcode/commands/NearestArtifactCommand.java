package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;

public class NearestArtifactCommand extends Command {
    CarouselSystem carouselSystem;
    public String dataKey = "NearestArtifactCommand";


    public NearestArtifactCommand(CarouselSystem carouselSystem) {
        this.carouselSystem = carouselSystem;
    }

    @Override
    public void init() {
        carouselSystem.setTargetArtifactState(ArtifactState.ANY);
        carouselSystem.updateArtifactStates();
        carouselSystem.setNearestArtifactToShoot();
    }

    @Override
    public void loop() {
        carouselSystem.updateArtifactStates();
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        string.addData("Color Sensor Data", carouselSystem.getArtifactState(CarouselPosition.CSSHOOT));
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return carouselSystem.shootSlotIsTarget();
    }

}