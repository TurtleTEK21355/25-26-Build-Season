package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Hood;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class SetHoodAngleCommand extends Command {
    ShooterSystem shooterSystem;
    public String dataKey = "SetHoodAngleCommand";

    private final double targetAngle;

    public SetHoodAngleCommand(double targetAngle, ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;
        this.targetAngle = targetAngle;
    }

    @Override
    public void init() {
        shooterSystem.setHoodAngle(targetAngle);

    }

    @Override
    public boolean isCompleted() {
        return true;

    }

}