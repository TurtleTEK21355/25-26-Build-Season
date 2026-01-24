package org.firstinspires.ftc.teamcode.commands.shoot;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class CloseGateCommand extends Command{
    private ShooterSystem shooterSystem;

    public CloseGateCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }
    @Override
    public void init() {
        shooterSystem.closeGate();
    }
    @Override
    public boolean isCompleted() {
        return true;
    }
}