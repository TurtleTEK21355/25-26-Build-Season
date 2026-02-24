package org.firstinspires.ftc.teamcode.subsystems.sensor;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;

public class Limelight {
    private Limelight3A limelight;
    public Limelight(Limelight3A limelight) {
        this.limelight = limelight;
        this.limelight.start();
    }
    public void telemetryLimelightAprilTagData(Pose2D position){
        limelight.updateRobotOrientation(position.h);
        LLResult result =limelight.getLatestResult();
        Pose3D botpose = result.getBotpose_MT2();
        for (LLResultTypes.FiducialResult llData : result.getFiducialResults()) {
            int id = llData.getFiducialId();
            if(id == Motif.GPP.getID() || id == Motif.PGP.getID() || id == Motif.PPG.getID()) {
                TelemetryPasser.telemetry.addData("Motif: ", Motif.fromID(id).toString());
            }
        }
        if (botpose != null) {
            TelemetryPasser.telemetry.addData("Field Position From AprilTags: ", botpose.toString());
        } else {
            TelemetryPasser.telemetry.addData("Field Position From AprilTags: ", "No Active Detections");
        }
    }

    /**
     * CAN RETURN NULL IF NO POSITIONING APRILTAGS ARE DETECTED
     * @return
     */
    public Pose2D getPosition(){
        LLResult result = limelight.getLatestResult();
        Pose3D botpose = result.getBotpose();
        return new Pose2D(botpose);
    }

    /**
     * CAN RETURN NULL IF NO MOTIF IS DETECTED
     * @return
     */
    public Motif getMotif(){
        LLResult result =limelight.getLatestResult();
        for (LLResultTypes.FiducialResult llData : result.getFiducialResults()) {
            return Motif.fromID(llData.getFiducialId());
        }
        return null;
    }
}
