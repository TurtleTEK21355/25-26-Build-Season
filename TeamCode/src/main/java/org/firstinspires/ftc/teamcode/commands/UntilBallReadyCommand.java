package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
public class UntilBallReadyCommand extends Command {
    private ShooterSystem shooterSystem;

    public UntilBallReadyCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public boolean isCompleted() {
        return shooterSystem.ballReady();

    }
}
