package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.subsystems.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class GetMotifCommand extends Command {
    AprilTagCamera aprilTagCamera;
    ShooterSystem shooterSystem;

    public GetMotifCommand(AprilTagCamera aprilTagCamera, ShooterSystem shooterSystem) {
        this.aprilTagCamera = aprilTagCamera;
        this.shooterSystem = shooterSystem;

    }

    @Override
    public void loop() {
        shooterSystem.setMotif(aprilTagCamera.getMotif());
    }

    @Override
    public boolean isCompleted() {
        return shooterSystem.getMotif() != null;
    }
}
