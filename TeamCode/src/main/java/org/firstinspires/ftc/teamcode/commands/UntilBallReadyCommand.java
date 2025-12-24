package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
public class UntilBallReadyCommand extends Command {
    private ShooterSystem shooterSystem;
    private boolean invert; //TODO inverts are lame just make another command
    private ElapsedTime timer;

    public UntilBallReadyCommand(ShooterSystem shooterSystem, boolean invert) {
        this.shooterSystem = shooterSystem;
        this.invert = invert;

    }

    @Override
    public void init(){
        timer = new ElapsedTime();
    }

    @Override
    public boolean isCompleted() {
        if (invert) {
            return (!shooterSystem.ballReady()||timer.seconds()>=3); //TODO might as well use TimerCommand if you're gonna do this
        } else {
            return (shooterSystem.ballReady()||timer.seconds()>=3);
        }
    }
}
