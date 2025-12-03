package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
    public class FlyWheelVelocity extends Command {
        private ShooterSystem shooterSystem;
        private double velocity;
        private boolean on;

        public FlyWheelVelocity(ShooterSystem shooterSystem, boolean on) {
            this.on = on;
            this.shooterSystem = shooterSystem;

        }

        @Override
        public void init() {
            if (on) {
                velocity = 1200;
            } else {
                velocity = 0;
            }
            shooterSystem.flywheelSetVelocity(velocity);

        }

        public void loop() {
            TelemetryPasser.telemetry.addData("FlyWheel Velocity", shooterSystem.flywheelGetVelocity());
            TelemetryPasser.telemetry.addData("Is Completed", isCompleted());
        }

        @Override
        public boolean isCompleted() {
            return ((shooterSystem.flywheelGetVelocity() > 1100 && on) || shooterSystem.flywheelGetVelocity() < 20 && !on);
        }

    }
