package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ArtifactLift;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class LifterUpCommand extends Command {

    private ShooterSystem shooterSystem;
    public String dataKey = "LifterUpCommand";


    public LifterUpCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
        shooterSystem.getArtifactLift().setLiftUpNoLimit();
    }

    @Override
    public boolean isCompleted() {
        return shooterSystem.getArtifactLift().getLiftUp();
    }
}
