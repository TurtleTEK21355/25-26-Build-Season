package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class Shoot3Command extends SequentialCommand{
    public Shoot3Command(ShooterSystem shooterSystem) {
        commandScheduler.addAll(
                new NextShootCommand(shooterSystem),
                new NextShootCommand(shooterSystem),
                new NextShootCommand(shooterSystem)
        );
    }
}
