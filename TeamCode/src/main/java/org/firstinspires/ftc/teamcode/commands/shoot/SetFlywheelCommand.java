package org.firstinspires.ftc.teamcode.commands.shoot;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShooterSystem;

public class SetFlywheelCommand extends Command {
    private ShooterSystem shooterSystem;
    private double velocity = 0;

    public SetFlywheelCommand(ShooterSystem shooterSystem, double velocity) {
        this.shooterSystem = shooterSystem;
        this.velocity = velocity;
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
        return (shooterSystem.flywheelGetVelocity() > (velocity-40));
    }
}
