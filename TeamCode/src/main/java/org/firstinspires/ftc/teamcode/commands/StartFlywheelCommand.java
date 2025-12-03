package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class StartFlywheelCommand extends Command {
    private ShooterSystem shooterSystem;
    private double velocity = 1200;

    public StartFlywheelCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
        shooterSystem.flywheelSetVelocity(velocity);

    }

    public void loop() {
        TelemetryPasser.telemetry.addData("FlyWheel Velocity", shooterSystem.flywheelGetVelocity());

    }

    @Override
    public boolean isCompleted() {
        return (shooterSystem.flywheelGetVelocity() > 1100);
    }
}
