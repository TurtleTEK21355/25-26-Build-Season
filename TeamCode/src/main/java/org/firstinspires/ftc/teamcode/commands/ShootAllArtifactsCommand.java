package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class ShootAllArtifactsCommand extends SequentialCommand {
    public String dataKey = "ShootAllArtifactsCommand";


    public ShootAllArtifactsCommand(ShooterSystem shooterSystem) {
        for (int i = 0; i < 3; i++) {
            commandList.add(new NearestArtifactCommand(shooterSystem.getCarouselSystem()));
            commandList.add(new LifterUpCommand(shooterSystem));
            commandList.add(new LifterDownCommand(shooterSystem));
        }
    }

    public ShootAllArtifactsCommand(ShooterSystem shooterSystem, Motif motif) {
        if (motif == null) {
            for (int i = 0; i < 3; i++) {
                commandList.add(new NearestArtifactCommand(shooterSystem.getCarouselSystem()));
                commandList.add(new LifterUpCommand(shooterSystem));
                commandList.add(new LifterDownCommand(shooterSystem));
            }
        }
        else {
            for (int i = 0; i < 3; i++) {
                commandList.add(new SelectArtifactCommand(shooterSystem.getCarouselSystem(), motif.getArtifactState(i)));
                commandList.add(new LifterUpCommand(shooterSystem));
                commandList.add(new LifterDownCommand(shooterSystem));
            }
        }
    }

}
