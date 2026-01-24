package org.firstinspires.ftc.teamcode.commands.shoot;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class OpenGateCommand extends Command {
    private ShooterSystem shooterSystem;

    public OpenGateCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }
    @Override
    public void init() {
        shooterSystem.openGate();
    }
    @Override
    public boolean isCompleted() {
        return true;
    }
}
