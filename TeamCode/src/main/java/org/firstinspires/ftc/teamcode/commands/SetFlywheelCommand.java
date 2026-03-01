package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

public class SetFlywheelCommand extends Command {
StateRobot robot;
    public String dataKey = "SetFlywheelCommand";


    final int FLYWHEEL_ON_VELOCITY = 1; //placeholder
    final int FLYWHEEL_OFF_VELOCITY = 0;
    double flywheelVelocity;


    /**
     * Sets intake speed. Use a boolean instead of a double for setting off/on
     * @param robot The robot
     * @param flywheelVelocity The power to set the intake to
     */
    public SetFlywheelCommand(StateRobot robot, double flywheelVelocity) {
        this.robot = robot;
        this.flywheelVelocity = flywheelVelocity;
    }

    /**
     * Sets intake off/on. Use a double instead of a boolean for setting a numerical value
     * @param robot The robot
     * @param flyhweelBoolean Whether to set the flywheel off/on
     */
    public SetFlywheelCommand(StateRobot robot, boolean flyhweelBoolean) {
        this.robot = robot;
        if (flyhweelBoolean) {
            flywheelVelocity = FLYWHEEL_ON_VELOCITY;
        } else {
            flywheelVelocity = FLYWHEEL_OFF_VELOCITY;
        }
    }

    @Override
    public void init() {
        robot.getShooterSystem().setFlywheelVelocity(flywheelVelocity);
    }

    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        string.addData("Flywheel Velocity: ", robot.getShooterSystem().getFlywheelVelocity());
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return (robot.getShooterSystem().getFlywheelVelocity() > flywheelVelocity-30);

    }

}