package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class GatePosition extends Command {
    private ShooterSystem shooterSystem;
    private boolean open;

    public GatePosition(ShooterSystem shooterSystem, boolean open) {
        this.shooterSystem = shooterSystem;
        this.open = open;
    }
    @Override
    public void init() {
        if (open) {
            shooterSystem.openGate();
        } else {
            shooterSystem.closeGate();
        }
    }
    @Override
    public boolean isCompleted() {
        return true;
    }
}
