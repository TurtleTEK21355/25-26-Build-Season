package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ArtifactLift;

public class LifterUpCommand extends Command {

    private ArtifactLift artifactLift;

    public LifterUpCommand(ArtifactLift artifactLift) {
        this.artifactLift = artifactLift;

    }

    @Override
    public void init() {
        artifactLift.setLiftUpNoLimit();
    }

    @Override
    public boolean isCompleted() {
        return artifactLift.getLiftUp();
    }
}
