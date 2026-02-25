package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ArtifactLift;

public class LifterDownCommand extends Command {

    private ArtifactLift artifactLift;

    public LifterDownCommand(ArtifactLift artifactLift) {
        this.artifactLift = artifactLift;

    }

    @Override
    public void init() {
        artifactLift.setLiftDownNoLimit();
    }

    @Override
    public boolean isCompleted() {
        return artifactLift.getLiftDown();
    }
}