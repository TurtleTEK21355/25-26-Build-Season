package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
public class UntilBallReadyCommand extends Command {
    private ShooterSystem shooterSystem;
    private boolean invert;
    private ElapsedTime timer;

    public UntilBallReadyCommand(ShooterSystem shooterSystem, boolean invert) {
        this.shooterSystem = shooterSystem;
        this.invert = invert;

    }
    public void init(){
        timer = new ElapsedTime();
    }
    @Override
    public boolean isCompleted() {
        if (invert) {
            return (!shooterSystem.ballReady()||timer.seconds()>=3);
        } else {
            return (shooterSystem.ballReady()||timer.seconds()>=3);
        }
    }
}
