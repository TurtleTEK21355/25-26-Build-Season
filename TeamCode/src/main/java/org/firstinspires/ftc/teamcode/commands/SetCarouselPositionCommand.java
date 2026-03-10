package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class SetCarouselPositionCommand extends Command {
    ShooterSystem shooterSystem;
    CarouselPosition carouselPosition;
    public String dataKey = "SetCarouselPositionCommand";



    public SetCarouselPositionCommand(CarouselPosition carouselPosition, ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;
        this.carouselPosition = carouselPosition;
    }

    @Override
    public void init() {
        shooterSystem.setCarouselPosition(carouselPosition);
    }

    @Override
    public boolean isCompleted() {
        return (true);

    }

}