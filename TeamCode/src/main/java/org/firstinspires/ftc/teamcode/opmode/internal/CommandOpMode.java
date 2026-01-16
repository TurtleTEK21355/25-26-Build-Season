package org.firstinspires.ftc.teamcode.opmode.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;

public abstract class CommandOpMode extends OpMode {
    private CommandScheduler commandScheduler = new CommandScheduler();

    @Override
    public void init() {
        initialize(); //where you put in commands and configuration

    }

    @Override
    public void loop() {
        commandScheduler.loop();

        if (!commandScheduler.isCompleted()) {
            stop();
        }
    }

    @Override
    public void stop() {
        cleanup(); //for blackboard or resetting of odometry
    }

    public abstract void initialize();
    public abstract void cleanup();

    public void addCommand(Command command) {
        commandScheduler.add(command);
    }

}
