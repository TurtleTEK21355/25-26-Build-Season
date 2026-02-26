package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class LifterDownCommand extends Command {

    private ShooterSystem shooterSystem;

    public LifterDownCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
        shooterSystem.getCarouselSystem().setNearestSlotInShoot();
        shooterSystem.getArtifactLift().setLiftDownNoLimit();
    }

    @Override
    public boolean isCompleted() {
        return shooterSystem.getArtifactLift().getLiftDown();
    }
}