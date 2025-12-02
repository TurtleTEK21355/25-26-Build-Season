package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
public class UntilBallReady extends Command {
    private ShooterSystem shooterSystem;
    private boolean ready;

    public UntilBallReady(ShooterSystem shooterSystem, boolean ready) {
        this.shooterSystem = shooterSystem;
        this.ready = ready;
    }

    @Override
    public void init() {
    }

    @Override
    public boolean isCompleted() {
        if (ready) {
            return (!shooterSystem.ballReady());
        } else {
            return (shooterSystem.ballReady());
        }
    }
}
