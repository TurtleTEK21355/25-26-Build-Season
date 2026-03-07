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
    private final double LL_POSITION_TOLERANCE = 5;
    public Limelight(Limelight3A limelight) {
        this.limelight = limelight;
        this.limelight.start();
    }
    public void telemetryLimelightAprilTagData(Pose2D position){
        limelight.updateRobotOrientation(position.h);
        LLResult result = limelight.getLatestResult();
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

    public void updateRobotOrientation(double yaw) {
        limelight.updateRobotOrientation((yaw - 180) % 360);
    }

    public LLResult getLatestResult() {
        return limelight.getLatestResult();
    }

    public Pose2D getPositionFromGoal() {
        LLResult result = limelight.getLatestResult();
        if (result.isValid()) {
            return new Pose2D(
                    result.getBotpose_MT2().getPosition().y * 39.3700787, //magic number
                    -result.getBotpose_MT2().getPosition().x * 39.3700787, //magic number
                    result.getBotpose_MT2().getOrientation().getYaw()
            );
        }
        else {
            return new Pose2D();
        }
    }

    public Pose2D getCorrectedPositionFromLL(Pose2D currentPosition){
        LLResult result = limelight.getLatestResult();
        Pose2D llPosition = new Pose2D(
                result.getBotpose_MT2().getPosition().y * 39.3700787, //magic number
                -result.getBotpose_MT2().getPosition().x * 39.3700787, //magic number
                currentPosition.h
        );
        if (result.isValid() && llPosition.distanceTo(currentPosition) > LL_POSITION_TOLERANCE) { //magic number
            return llPosition;
        }
        else {
            return currentPosition;
        }
    }
    /**
     * Returns NONE when no tag is detected
     * @return
     */
    public Motif getMotif(){
        LLResult result = limelight.getLatestResult();
        for (LLResultTypes.FiducialResult llData : result.getFiducialResults()) {
            Motif motif = Motif.fromID(llData.getFiducialId());
            if (motif != Motif.NONE) {
                return motif;
            }
        }
        return Motif.NONE;
    }
}
