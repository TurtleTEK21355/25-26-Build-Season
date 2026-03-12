package org.firstinspires.ftc.teamcode.opmode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.GetMotifCommand;
import org.firstinspires.ftc.teamcode.commands.SimultaneousAnyCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;

@Disabled
@Autonomous(name="Motif Detection Test", group = "test") // Replace name with clear and identifiable name
public class MotifDetectionTest extends StateAutoOpMode {
    double startingHeading = 0;
    AllianceSide side = AllianceSide.BLUE; // Replace BLUE with RED if required

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingHeading(startingHeading);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new SimultaneousAnyCommand(new GetMotifCommand(robot.getLimelight()), new TimerCommand(3000)));
        addCommand(new TimerCommand(10000));
    }

    public void dataHandler(){
        if (commandScheduler.getData("GetMotifCommand") != null) {
            motif = (Motif) commandScheduler.getData("GetMotifCommand");
        }

        telemetry.addData("Motif", motif.toString());

    }

}
