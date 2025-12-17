package org.firstinspires.ftc.teamcode.opmode.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;

public abstract class CommandOpMode extends LinearOpMode {
    private CommandList commandList = new CommandList();

    @Override
    public void runOpMode() throws InterruptedException {

        initialize(); //where you put in commands and configuration

        waitForStart();

        for (Command command : commandList) { //runs through all commands in commandList
            command.init();
            while (opModeIsActive()) {
                command.loop();
                telemetry.update(); //make sure this isn't in any command
                if (command.isCompleted()) { //this is after the loop so if things get set in the loop that are in isCompleted there will be no issue
                    break;

                }

            }

        }

        cleanup(); //assign blackboard variables because they were being assigned at initialize before

    }

    protected abstract void initialize();

    protected abstract void cleanup();

    protected void addCommand(Command command) {
        commandList.add(command);
    }

}
