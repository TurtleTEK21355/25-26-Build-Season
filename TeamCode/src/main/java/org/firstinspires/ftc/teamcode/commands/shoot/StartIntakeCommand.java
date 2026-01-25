package org.firstinspires.ftc.teamcode.commands.shoot;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShooterSystem;

public class StartIntakeCommand extends Command {
    private ShooterSystem shooterSystem;

    public StartIntakeCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
        shooterSystem.intakeSetPower(0.8);
    }

    @Override
    public boolean isCompleted() {
        return true;
    }
}
