package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class LifterUpCommand extends Command {
    ShooterSystem shooterSystem;
    ElapsedTime timeout = new ElapsedTime();
    public String dataKey = "LifterUpCommand";


    public LifterUpCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
        timeout.reset();
        shooterSystem.getArtifactLift().setLiftTargetUp();
        shooterSystem.getArtifactLift().setLiftMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooterSystem.getArtifactLift().setLiftPower(Constants.artifactLiftPower);
    }

    @Override
    public boolean isCompleted() {
        if (shooterSystem.getArtifactLift().getLiftUp() || timeout.milliseconds() > Constants.artifactLiftTimeoutMilliseconds) {
            return true;
        }
        return false;
    }
}
