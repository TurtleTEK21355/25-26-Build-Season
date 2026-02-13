package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.ProgrammingMovePIDCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot;

@Autonomous(name="Path Test")
public class PathTest extends StateAutoOpMode {
    ProgrammingRobot robot;
    Pose2D startingPosition = new Pose2D(-40,64,0);
    AllianceSide side = AllianceSide.BLUE;
    
    
    @Override
    public void commands() {
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-24,24,40), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-24,12,0), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-54,12,0), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-24,24,40), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-24,-12,0), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-54,-12,0), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-24,24,40), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-24,-36,0), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-54,-36,0), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-24,24,40), speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(-48,0,0), speed, robot));
    }


}
