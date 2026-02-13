package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.ProgrammingMovePIDCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot;

@Autonomous(name="BLUE")
public class PathTestState extends StateAutoOpMode {
    ProgrammingRobot robot;
    Pose2D startingPosition = new Pose2D(-53,51,54);
    AllianceSide side = AllianceSide.BLUE;

    double intake = -45;
    double move = -20;
    double top = 12;
    double middle = -12;
    double bottom = -36;
    
    @Override
    public void commands() {
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(move, top, 54),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(intake, top, 90),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(move, top, 54),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(move, middle, 90),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(intake, middle, 90),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(move, top, 54),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(move, bottom, 90),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(intake, bottom, 90),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(move, top, 54),speed, robot));
        addCommand(new ProgrammingMovePIDCommand(new Pose2D(move, middle, 54),speed, robot));




}
