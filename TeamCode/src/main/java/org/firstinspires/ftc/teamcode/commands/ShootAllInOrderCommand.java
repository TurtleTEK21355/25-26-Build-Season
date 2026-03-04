package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class ShootAllInOrderCommand extends SequentialCommand {
    public String dataKey = "ShootAllInOrderCommand";

    final int FIRST_CAROUSEL_WAIT_TIME = 800;
    final int CAROUSEL_WAIT_TIME = 400;
    public ShootAllInOrderCommand(ShooterSystem shooterSystem) {
        commandList.add(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.SHOOT_SLOT_0));
        commandList.add(new TimerCommand(FIRST_CAROUSEL_WAIT_TIME));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));
        commandList.add(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.SHOOT_SLOT_1));
        commandList.add(new TimerCommand(CAROUSEL_WAIT_TIME));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));
        commandList.add(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.SHOOT_SLOT_2));
        commandList.add(new TimerCommand(CAROUSEL_WAIT_TIME));
        commandList.add(new LifterUpCommand(shooterSystem));
        commandList.add(new LifterDownCommand(shooterSystem));

    }
}