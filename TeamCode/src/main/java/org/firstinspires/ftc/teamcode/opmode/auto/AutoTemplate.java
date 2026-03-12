package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Disabled // Remove this line to make auto show up in Driver Station
@Autonomous(name="Auto Template", group = "auto") // Replace name with clear and identifiable name
public class AutoTemplate extends StateAutoOpMode {
    double startingHeading = 0; // Replace 0s with starting position
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
        // Add Commands Here
        // addCommand(new CommandName(Args args);
    }


}
