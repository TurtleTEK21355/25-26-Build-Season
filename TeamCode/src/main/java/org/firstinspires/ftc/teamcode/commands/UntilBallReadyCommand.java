package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
public class UntilBallReadyCommand extends Command {
    private ShooterSystem shooterSystem;
    private boolean invert;

    public UntilBallReadyCommand(ShooterSystem shooterSystem, boolean invert) {
        this.shooterSystem = shooterSystem;
        this.invert = invert;

    }

    @Override
    public boolean isCompleted() {
        if (invert) {
            return !shooterSystem.ballReady();
        } else {
            return shooterSystem.ballReady();
        }
    }
}
