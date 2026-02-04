package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class StopFlywheelCommand extends Command{
    private ShooterSystem shooterSystem;
    private double velocity = 0;

    public StopFlywheelCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
        shooterSystem.setFlywheelVelocity(velocity);

    }

    public void loop() {

    }

    @Override
    public boolean isCompleted() {
        return true;
    }

}
