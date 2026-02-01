package org.firstinspires.ftc.teamcode.commands.shoot;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShooterSystem;

public class StartIntakeCommand extends Command {
    private ShooterSystem shooterSystem;
    private double power;

    /**
     *
     * @param shooterSystem
     * @param power
     */
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
        shooterSystem.intakeSetPower(power);
    }

    @Override
    public boolean isCompleted() {
        return true;
    }
}
