package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;

public class SetCarouselPositionCommand extends Command {
    CarouselSystem carouselSystem;
    CarouselPosition carouselPosition;
    public String dataKey = "SetCarouselPositionCommand";



    public SetCarouselPositionCommand(CarouselSystem carouselSystem, CarouselPosition carouselPosition) {
        this.carouselSystem = carouselSystem;
        this.carouselPosition = carouselPosition;
    }

    @Override
    public void init() {
        carouselSystem.setPosition(carouselPosition.getAbsolutePosition());
    }

    @Override
    public boolean isCompleted() {
        return (true);

    }

}