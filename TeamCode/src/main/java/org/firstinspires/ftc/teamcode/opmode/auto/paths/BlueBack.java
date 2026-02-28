package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import org.firstinspires.ftc.teamcode.commands.LifterDownCommand;
import org.firstinspires.ftc.teamcode.commands.LifterUpCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

public class BlueBack extends StateAutoOpMode {
    Pose2D startingPosition = new Pose2D(-53, 51, 54);
    AllianceSide side = AllianceSide.BLUE;

    double speed = 0.5;

    double intake = -45;
    double move = -20;
    double top = 12;
    double middle = -12;
    double bottom = -36;

    double theVoidY = -54;

    @Override
    public void initialize() {
        setAllianceSide(side);
        setStartingPosition(startingPosition);
        super.initialize();
    }

    @Override
    public void commands() {
        //assuming the void isnt the start position
        addCommand(new MovePIDCommand(new Pose2D(move, theVoidY, 54), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(move, bottom, 90), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(intake, bottom, 90), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(move, theVoidY, 54), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(move, middle, 90), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(intake, middle, 90), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(move,top, 54), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        //if the command above knocks away the artifacts, either move to the move column middle row first, or change to move, thevoidy.
        addCommand(new MovePIDCommand(new Pose2D(move, top, 90), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(intake, top, 90), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(move, top, 54), speed, robot.getDrivetrain(), robot.getOtosSensor()));
        addCommand(new MovePIDCommand(new Pose2D(move, middle, 54), speed, robot.getDrivetrain(), robot.getOtosSensor()));


    }
}
