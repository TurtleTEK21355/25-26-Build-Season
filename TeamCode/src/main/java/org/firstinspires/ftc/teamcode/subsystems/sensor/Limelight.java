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

    public Pose2D correctPositionFromLL(Pose2D currentPosition){
        Pose2D llPosition = getPosition();
        if (llPosition != null || llPosition.distanceTo(currentPosition) > 10) {
            return llPosition;
        }
        else {
            return currentPosition;
        }
    }

    public Pose2D getPosition(){
        LLResult result = limelight.getLatestResult();
        if (result.isValid()) {
            Pose2D llPosition = new Pose2D(
                    result.getBotpose().getPosition().x * 39.3700787,
                    result.getBotpose().getPosition().y * 39.3700787,
                    result.getBotpose().getOrientation().getYaw()
            );
            return llPosition;
        }
        else {
            return null;
        }
    }

    /**
     * CAN RETURN NULL IF NO MOTIF IS DETECTED
     * @return
     */
    public Motif getMotif(){
        LLResult result = limelight.getLatestResult();
        for (LLResultTypes.FiducialResult llData : result.getFiducialResults()) {
            return Motif.fromID(llData.getFiducialId());
        }
        return null;
    }
}
