package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.sensor.Limelight;

public class GetMotifCommand extends Command {
    Limelight limelight;
    private Motif motif;

    public String dataKey = "GetMotifCommand";
    public GetMotifCommand(Limelight limelight) {
    this.limelight = limelight;
    }



    @Override
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        string.addLine("Finding Motif");
        return string.toString();
    }

    @Override
    public void loop() {
        motif = limelight.getMotif();
        data = motif;
    }


    @Override
    public boolean isCompleted() {
        return motif != Motif.NONE;
    }

}