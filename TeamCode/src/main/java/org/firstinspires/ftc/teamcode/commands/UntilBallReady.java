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
        if (ready) {
            while(!shooterSystem.ballReady());
        } else {
            while(shooterSystem.ballReady());
        }
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

}
