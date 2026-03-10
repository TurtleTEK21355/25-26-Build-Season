package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class PreviousShootCommand extends Command {
    ShooterSystem shooterSystem;
    CommandScheduler commandScheduler = new CommandScheduler();

    public PreviousShootCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;
    }

    @Override
    public void init() {
        CarouselPosition currentCarouselPosition = shooterSystem.getCarouselPosition();
        commandScheduler.add(new SetCarouselPositionCommand(CarouselPosition.previousShootPosition(currentCarouselPosition), shooterSystem.getCarouselSystem()));
        commandScheduler.add(new TimerCommand(Constants.carouselMoveOneTimer));
        commandScheduler.add(new LifterUpCommand(shooterSystem));
        commandScheduler.add(new LifterDownCommand(shooterSystem));
    }

    @Override
    public void loop() {
        commandScheduler.loop();
    }

    @Override
    public String telemetry() {
        return commandScheduler.getTelemetry();
    }

    @Override
    public boolean isCompleted() {
        return commandScheduler.isCompleted();
    }

}