package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class StopIntakeCommand extends Command {
    private ShooterSystem shooterSystem;

    public StopIntakeCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
        shooterSystem.intakeSetPower(0);
    }

    @Override
    public boolean isCompleted() {
        return true;
    }
}
