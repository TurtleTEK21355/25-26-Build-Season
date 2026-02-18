package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.ProgrammingAutoOpMode;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="Path Test")
public class PathTest extends ProgrammingAutoOpMode {
    Pose2D startingPosition = new Pose2D(-40,64,0);
    AllianceSide side = AllianceSide.BLUE;

    double speed = 0.5;

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingPosition(startingPosition);
        super.initialize();
    }
    
    @Override
    public void commands() {
        addCommand(new MovePIDCommand(new Pose2D(-24,24,40), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-24,12,0), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-54,12,0), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-24,24,40), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-24,-12,0), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-54,-12,0), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-24,24,40), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-24,-36,0), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-54,-36,0), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-24,24,40), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(-48,0,0), speed, robot.getDrivetrain(), robot.getOtosSensor()));
    }


}
