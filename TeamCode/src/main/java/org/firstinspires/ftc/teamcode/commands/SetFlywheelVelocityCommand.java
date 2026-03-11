package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class SetFlywheelVelocityCommand extends Command {
    ShooterSystem shooterSystem;
    public String dataKey = "SetFlywheelVelocityCommand";

    double flywheelVelocity;


    /**
     * Sets intake speed
     * @param shooterSystem The shooterSystem
     * @param flywheelVelocity The power to set the intake to
     */
    public SetFlywheelVelocityCommand(ShooterSystem shooterSystem, double flywheelVelocity) {
        this.shooterSystem = shooterSystem;
        this.flywheelVelocity = flywheelVelocity;
    }

    @Override
    public void init() {
        shooterSystem.setFlywheelVelocity(flywheelVelocity);
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        string.addData("Flywheel Velocity: ", shooterSystem.getFlywheelVelocity());
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (shooterSystem.getFlywheelVelocity() > flywheelVelocity-30);

    }

}