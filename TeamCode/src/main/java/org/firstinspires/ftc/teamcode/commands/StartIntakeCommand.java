package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class StartIntakeCommand extends Command {
    private ShooterSystem shooterSystem;
    private double power;

    public StartIntakeCommand(ShooterSystem shooterSystem, double power) {
        this.shooterSystem = shooterSystem;
        this.power = power;

    }

    public StartIntakeCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;
        power = 1;

    }

    @Override
    public void init() {

    }

    @Override
    public boolean isCompleted() {
        return true;
    }
}
