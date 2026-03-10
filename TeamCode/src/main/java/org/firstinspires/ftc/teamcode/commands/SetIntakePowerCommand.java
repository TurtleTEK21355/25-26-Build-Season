package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class SetIntakePowerCommand extends Command {
    ShooterSystem shooterSystem;
    double intakePower;

    public String dataKey = "SetIntakePowerCommand";


    public SetIntakePowerCommand(double intakePower, ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;
        this.intakePower = intakePower;
    }


    @Override
    public void init() {shooterSystem.setIntakePower(intakePower);}

    @Override
    public boolean isCompleted() {
        return true;

    }

}