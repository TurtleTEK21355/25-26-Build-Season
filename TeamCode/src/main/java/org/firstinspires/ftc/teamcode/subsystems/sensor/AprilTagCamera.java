package org.firstinspires.ftc.teamcode.subsystems.sensor;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTagCamera {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> currentDetections;
    private Motif currentMotif = null;


    public AprilTagCamera(CameraName camera) {
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                //.setDrawAxes(false)
                //.setDrawCubeProjection(false)
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.

                .build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        aprilTag.setDecimation(1);

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera
        builder.setCamera(camera);

        // Choose a camera resolution. Not all cameras support all resolutions.
        builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        builder.setStreamFormat(VisionPortal.StreamFormat.MJPEG);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        visionPortal.setProcessorEnabled(aprilTag, true);
    }

    public String returnAllAprilTagData() {
        StringBuilder outputString = new StringBuilder();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                outputString.append("ID ")
                        .append(detection.id)
                        .append("\nx ")
                        .append(detection.ftcPose.x)
                        .append("\ny ")
                        .append(detection.ftcPose.y)
                        .append("\nz ")
                        .append(detection.ftcPose.z)
                        .append("\npitch ")
                        .append(detection.ftcPose.pitch)
                        .append("\nroll ")
                        .append(detection.ftcPose.roll)
                        .append("\nyaw ")
                        .append(detection.ftcPose.yaw);
            } else {
                outputString.append("\nDetection Unknown");
            }
            outputString.append("\n");
        }
        return outputString.toString();
    }

    public void updateDetections() {
        currentDetections = aprilTag.getDetections();
    }

    public AprilTagDetection getDetection(int id) {
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.id == id) {
                    return detection;
                }
            }

        }
        return null;
    }

    public Double getRange(AprilTagDetection detection) {
        if (detection != null && detection.metadata != null) {
            return detection.ftcPose.range;
        }
        return null;
    }

    public Motif getMotif() {
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.id == Motif.GPP.getID()) {
                    currentMotif = Motif.GPP;
                } else if (detection.id == Motif.PGP.getID()) {
                    currentMotif = Motif.PGP;
                } else if (detection.id == Motif.PPG.getID()) {
                    currentMotif = Motif.PPG;
                }
            }
        }
        return currentMotif;
    }

    public Pose2D getPositionFromGoalAprilTag(Pose2D currentPosition) { //gotta pass in the current because otherwise it will reset the position every time it doesn't detect a goal
        double minDistanceInches = 5;
        AprilTagDetection blueGoal = getDetection(AllianceSide.BLUE.getGoalID());
        AprilTagDetection redGoal = getDetection(AllianceSide.RED.getGoalID());
        if (blueGoal != null) {
            Pose2D blueGoalRelativePosition = new Pose2D(blueGoal.robotPose);
            TelemetryPasser.telemetry.addData("blueGoalRelativePos", blueGoalRelativePosition);
            TelemetryPasser.telemetry.addData("distancetoblue", currentPosition.distanceTo(blueGoalRelativePosition));
            Pose2D positionOnField = AllianceSide.BLUE.getGoalPosition().subtract(blueGoalRelativePosition);
            if (currentPosition.distanceTo(positionOnField) >= minDistanceInches) {
                return new Pose2D(positionOnField.x, positionOnField.y, currentPosition.h);
            }
        }
        if (redGoal != null) {
            Pose2D redGoalRelativePosition = new Pose2D(redGoal.robotPose);
            TelemetryPasser.telemetry.addData("redGoalRelativePos", redGoalRelativePosition);
            TelemetryPasser.telemetry.addData("distancetored", currentPosition.distanceTo(redGoalRelativePosition));
            Pose2D positionOnField = AllianceSide.RED.getGoalPosition().subtract(redGoalRelativePosition);
            if (currentPosition.distanceTo(positionOnField) >= minDistanceInches) {
                return new Pose2D(positionOnField.x, positionOnField.y, currentPosition.h);
            }
        }
        return currentPosition;
    }

}