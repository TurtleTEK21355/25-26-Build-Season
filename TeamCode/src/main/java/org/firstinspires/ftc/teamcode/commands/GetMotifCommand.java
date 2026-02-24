package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.sensor.Limelight;

public class GetMotifCommand extends Command {
    Limelight limelight;
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
        Motif motif = limelight.getMotif();
        if (limelight.getMotif() != null) {
            data = motif;
            return true;
        }
        return false;
    }


    @Override
    public boolean isCompleted() {

    }

}