package org.firstinspires.ftc.teamcode.opmode.test.function;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.GetMotifCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.SimultaneousAnyCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.ProgrammingAutoOpMode;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;

import java.util.Objects;

@Autonomous(name="Motif Detection Test") // Replace name with clear and identifiable name
public class MotifDetectionTest extends StateAutoOpMode {
    Pose2D startingPosition = new Pose2D(0,0,0); // Replace 0s with starting position
    AllianceSide side = AllianceSide.BLUE; // Replace BLUE with RED if required

    final double SPEED = 0.5; // Replace with wanted speed

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingPosition(startingPosition);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new SimultaneousAnyCommand(new GetMotifCommand(robot.getLimelight()), new TimerCommand(3000)));
        addCommand(new TimerCommand(10000));
    }

    public void dataHandler(){
        Object object = commandScheduler.getData();
        if (Objects.equals(commandScheduler.getDataKey(), "GetMotifCommand")) {
            if(object != null){
                motif = (Motif) object;
            }
        }
        telemetry.addData("Motif: ", motif.getID());
    }


}
