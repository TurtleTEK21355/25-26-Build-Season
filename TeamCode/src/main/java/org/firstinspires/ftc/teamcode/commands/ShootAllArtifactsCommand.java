package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

import java.util.Timer;

public class ShootAllArtifactsCommand extends SequentialCommand {
    public String dataKey = "ShootAllArtifactsCommand";

    public ShootAllArtifactsCommand(ShooterSystem shooterSystem, Motif motif) {
        commandScheduler.add(new SetCarouselPositionCommand(shooterSystem.getCarouselSystem(), CarouselPosition.nextShootPosition(shooterSystem.getCarouselPosition())));
        commandScheduler.add(new TimerCommand(Constants.carouselMoveOneTimer));
        if (motif == null) {
            for (int i = 0; i < 3; i++) {
                commandScheduler.add(new NearestArtifactCommand(shooterSystem.getCarouselSystem()));
                commandScheduler.add(new LifterUpCommand(shooterSystem));
                commandScheduler.add(new LifterDownCommand(shooterSystem));
            }
        }
        else {
            for (int i = 0; i < 3; i++) {
                commandScheduler.add(new SelectArtifactCommand(shooterSystem.getCarouselSystem(), motif.getArtifactState(i)));
                commandScheduler.add(new LifterUpCommand(shooterSystem));
                commandScheduler.add(new LifterDownCommand(shooterSystem));
            }
        }
    }

}
