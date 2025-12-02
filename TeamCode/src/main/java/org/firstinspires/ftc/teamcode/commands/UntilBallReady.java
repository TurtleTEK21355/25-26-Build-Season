package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
public class UntilBallReady extends Command {
    private ShooterSystem shooterSystem;

    public UntilBallReady(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
    }

    @Override
    public boolean isCompleted() {
        return shooterSystem.ballReady();

    }
}
