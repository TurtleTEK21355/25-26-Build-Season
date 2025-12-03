package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
public class IntakeAuto extends Command {
    private ShooterSystem shooterSystem;
    private boolean on;
    public IntakeAuto(ShooterSystem shooterSystem, boolean on) {
        this.shooterSystem=shooterSystem;
        this.on = on;
    }

    @Override
    public void init() {
        if (on) {
            shooterSystem.intakeSetPower(0.8);
        } else {
            shooterSystem.intakeSetPower(0);
        }
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

}
