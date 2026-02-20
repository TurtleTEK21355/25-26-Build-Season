package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

public class SetIntakeCommand extends Command {
StateRobot robot;

    final double INTAKE_ON_SPEED = 1.0;
    final double INTAKE_OFF_SPEED = 0.0;
    double intakePower;


    /**
     * Sets intake speed. Use a boolean instead of a double for setting off/on(0/1)
     * @param robot The robot
     * @param intakePower The power to set the intake to
     */
    public SetIntakeCommand(StateRobot robot, double intakePower) {
        this.robot = robot;
        this.intakePower = intakePower;
    }

    /**
     * Sets intake off/on (0/1). Use a double instead of a boolean for setting a numerical value
     * @param robot The robot
     * @param intakeBoolean Whether to set the intake off/on (0/1)
     */
    public SetIntakeCommand(StateRobot robot, boolean intakeBoolean) {
        this.robot = robot;
        if (intakeBoolean) {
            intakePower = INTAKE_ON_SPEED;
        } else {
            intakePower = INTAKE_OFF_SPEED;
        }
    }

    @Override
    public void init() {robot.getShooterSystem().setIntakePower(intakePower);}

    @Override
    public boolean isCompleted() {
        return (true);

    }

}