package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class ShootAllInOrderCommand extends SequentialCommand {
    public String dataKey = "ShootAllInOrderCommand";

    final int FIRST_CAROUSEL_WAIT_TIME = 800;
    final int CAROUSEL_WAIT_TIME = 400;
    final int LAST_CAROUSEL_WAIT_TIME = 500;
    public ShootAllInOrderCommand(ShooterSystem shooterSystem) {
        commandList.add(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.CSSHOOT));
        commandList.add(new TimerCommand(FIRST_CAROUSEL_WAIT_TIME));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));
        commandList.add(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.CSRIGHT));
        commandList.add(new TimerCommand(CAROUSEL_WAIT_TIME));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));
        commandList.add(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.CSLEFT));
        commandList.add(new TimerCommand(CAROUSEL_WAIT_TIME));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));
        commandList.add(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.INTAKESHOOT));
        commandList.add(new TimerCommand(LAST_CAROUSEL_WAIT_TIME));

    }
}