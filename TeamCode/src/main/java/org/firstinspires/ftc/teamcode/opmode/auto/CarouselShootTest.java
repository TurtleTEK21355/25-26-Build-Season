package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.LifterDownCommand;
import org.firstinspires.ftc.teamcode.commands.LifterUpCommand;
import org.firstinspires.ftc.teamcode.commands.NearestArtifactCommand;
import org.firstinspires.ftc.teamcode.commands.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.commands.SetHoodCommand;
import org.firstinspires.ftc.teamcode.commands.ShootAllArtifactsCommand;
import org.firstinspires.ftc.teamcode.commands.ShootAllInOrderCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="Carousel Shoot Test")
public class CarouselShootTest extends StateAutoOpMode {
    Pose2D startingPosition = new Pose2D(0,0,0);
    AllianceSide side = AllianceSide.BLUE;

    final double SPEED = 0.75;

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingPosition(startingPosition);
        super.initialize();
    }

    @Override
    public void commands() {
//        addCommand(new SetFlywheelCommand(robot, 1300));
//        addCommand(new SetHoodCommand(35, robot.getShooterSystem().getHood()));
        addCommand(new ShootAllInOrderCommand(robot.getShooterSystem()));
//        addCommand(new NearestArtifactCommand(robot.getShooterSystem().getCarouselSystem()));
//        addCommand(new LifterUpCommand(robot.getShooterSystem()));
//        addCommand(new LifterDownCommand(robot.getShooterSystem()));
    }


}
