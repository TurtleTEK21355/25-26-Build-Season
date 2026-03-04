package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Intake;

public class CarouselSelectNextCommand extends Command {
    CarouselSystem carouselSystem;
    boolean intake;

    public CarouselSelectNextCommand(CarouselSystem carouselSystem, boolean intake) {
        this.carouselSystem = carouselSystem;
        this.intake = intake;
    }

    @Override
    public void init() {
        //init code
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