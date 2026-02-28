package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;

public class SetCarouselPositionCommand extends Command {
    CarouselSystem carouselSystem;
    CarouselPosition carouselPosition;

    public SetCarouselPositionCommand(CarouselSystem carouselSystem, CarouselPosition carouselPosition) {
        this.carouselSystem = carouselSystem;
        this.carouselPosition = carouselPosition;
    }

    @Override
    public void init() {
        carouselSystem.setPosition(carouselPosition.getAbsolutePosition());
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
        // replace false with completion condition
        return (false);

    }

}