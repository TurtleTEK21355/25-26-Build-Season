package org.firstinspires.ftc.teamcode.commands;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class ShootCommand extends Command {
    private ShooterSystem shooterSystem;
    private double range;
    private double timer = 0;
    private double velocity;
    private static final double GRAVITY = 386.09; //Inches per second squared
    private static final double HEIGHT = 40; //inches tall
    private static final double THETA = 1.13446401; //Ramp Angle in Radians
    private static final double MAX_SPEED = 388.590;

    ShootCommand(double range, ShooterSystem shooterSystem) {

    }

    @Override
    public void loop() {
        shooterSystem.flywheelSetVelocity(velocity);

    }

    @Override
    public boolean isCompleted() {
        return false;
    }

}
