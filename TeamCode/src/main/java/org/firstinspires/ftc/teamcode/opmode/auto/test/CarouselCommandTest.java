package org.firstinspires.ftc.teamcode.opmode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.NearestArtifactCommand;
import org.firstinspires.ftc.teamcode.commands.SelectArtifactCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;

@Disabled
@Autonomous(name="Carousel ArtifactState Command Test", group = "test") // Replace name with clear and identifiable name
public class CarouselCommandTest extends StateAutoOpMode {
    double startingHeading = 0;
    AllianceSide side = AllianceSide.BLUE; // Replace BLUE with RED if required

    final double SPEED = 0.5; // Replace with wanted speed

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingHeading(startingHeading);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new NearestArtifactCommand(robot.getShooterSystem().getCarouselSystem()));
        addCommand(new TimerCommand(5000));
        addCommand(new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.EMPTY));
        addCommand(new TimerCommand(5000));
        addCommand(new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.GREEN));
        addCommand(new TimerCommand(5000));
        addCommand(new SelectArtifactCommand(robot.getShooterSystem().getCarouselSystem(), ArtifactState.PURPLE));
        addCommand(new TimerCommand(5000));

    }


}
