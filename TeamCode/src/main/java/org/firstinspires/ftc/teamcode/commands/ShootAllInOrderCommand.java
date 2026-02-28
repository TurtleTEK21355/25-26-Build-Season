package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class ShootAllInOrderCommand extends SequentialCommand {
    final int FIRST_CAROUSEL_WAIT_TIME = 200;
    final int CAROUSEL_WAIT_TIME = 100;
    final int LAST_CAROUSEL_WAIT_TIME = 200;
    public ShootAllInOrderCommand(ShooterSystem shooterSystem) {
        commandList.add(new SimultaneousAllCommand(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.CSSHOOT), new TimerCommand(FIRST_CAROUSEL_WAIT_TIME)));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));
        commandList.add(new SimultaneousAllCommand(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.CSRIGHT), new TimerCommand(CAROUSEL_WAIT_TIME)));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));
        commandList.add(new SimultaneousAllCommand(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.CSLEFT), new TimerCommand(CAROUSEL_WAIT_TIME)));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));
        commandList.add(new SimultaneousAllCommand(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.INTAKESHOOT), new TimerCommand(LAST_CAROUSEL_WAIT_TIME)));
    }
}