package org.firstinspires.ftc.teamcode.commands;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class ShootCommand extends Command {
    private ShooterSystem shooterSystem;
    private double range;
    private double timer = 0;
    private double power;
    private static final double GRAVITY = 386.09; //Inches per second squared
    private static final double HEIGHT = 40; //inches tall
    private static final double THETA = 1.13446401; //Ramp Angle in Radians
    private static final double MAX_SPEED = 388.590;

    ShootCommand(double range, ShooterSystem shooterSystem) {
        this.range = range;
        this.shooterSystem = shooterSystem;
        power = (Math.sqrt((-GRAVITY*Math.pow(range, 2))/(2*Math.pow(Math.cos(THETA), 2)*(HEIGHT - range * Math.tan(THETA)))))/ MAX_SPEED;

    }

    @Override
    public void loop() {
        shooterSystem.flywheelSetPower(power);

    }

    @Override
    public boolean isCompleted() {
        return false;
    }

}
