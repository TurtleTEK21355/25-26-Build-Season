package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;

public class LifterDownCommand extends Command {
    ShooterSystem shooterSystem;
    ElapsedTime timeout = new ElapsedTime();
    public String dataKey = "LifterDownCommand";


    public LifterDownCommand(ShooterSystem shooterSystem) {
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void init() {
        timeout.reset();
        shooterSystem.getArtifactLift().setLiftTargetDown();
    }

    @Override
    public boolean isCompleted() {
        if (shooterSystem.getArtifactLift().getLiftDown() || timeout.milliseconds() > Constants.artifactLiftTimeoutMilliseconds) {
            shooterSystem.getArtifactLift().setLiftMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            shooterSystem.getArtifactLift().setLiftPower(0);
            return true;
        }
        return false;
    }
}