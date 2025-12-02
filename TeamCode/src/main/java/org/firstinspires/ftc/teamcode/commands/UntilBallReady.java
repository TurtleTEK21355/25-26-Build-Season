package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
public class UntilBallReady extends Command {
    private ShooterSystem shooterSystem;

    public UntilBallReady(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        this.ready = ready;
    }

    @Override
    public void init() {
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    }

    @Override
    public boolean isCompleted() {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        if (ready) {
            return (!shooterSystem.ballReady());
        } else {
            return (shooterSystem.ballReady());
        }
=======
        return shooterSystem.ballReady();
>>>>>>> Stashed changes
=======
        return shooterSystem.ballReady();
>>>>>>> Stashed changes
    }
}
