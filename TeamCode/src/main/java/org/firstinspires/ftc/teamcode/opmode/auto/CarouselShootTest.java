package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="Carousel Shoot Test", group = "test")
public class CarouselShootTest extends StateAutoOpMode {
    Pose2D startingPosition = new Pose2D(0,0,0);
    AllianceSide side = AllianceSide.BLUE;

    final double SPEED = 0.75;

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingHeading(startingPosition);
        super.initialize();
    }

    @Override
    public void commands() {

    }


}
