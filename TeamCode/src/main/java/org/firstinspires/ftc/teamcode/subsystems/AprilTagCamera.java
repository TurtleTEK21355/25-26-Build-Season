package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.teamcode.Motif;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTagCamera {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> currentDetections;


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
        aprilTag.setDecimation(3);

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera
        builder.setCamera(camera);

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);
    }

    public String aprilTagTelemetry() {
        String outputString = "";
        updateDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection != null) {
                outputString.concat("ID " + detection.id);
                outputString.concat("\nx " + detection.ftcPose.x + "\ny " + detection.ftcPose.y + "\nz " + detection.ftcPose.z);
                outputString.concat("\npitch " + detection.ftcPose.pitch + "\nroll " + detection.ftcPose.roll + "\nyaw " + detection.ftcPose.yaw);

            } else {
                outputString.concat("\nDetection Unknown");

            }
            outputString.concat("\n");
        }
        return outputString;

    }

    public void updateDetections() {
        currentDetections = aprilTag.getDetections();

    }

    public AprilTagDetection getDetection(int id) { // gets a detection
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == id) {
                return detection;

            }

        }
        return null;

    }

    public Double getRange(int id) { //this function might be useful in the future
        AprilTagDetection detection = getDetection(id);
        if (detection != null) {
            return detection.ftcPose.range;
        }
        return null; //since its a double this is the only kinda null thing it can return
    }

    public Motif getMotifFromDetections() {
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == Motif.GPP.getID()) {
                return Motif.GPP;
            } else if (detection.id == Motif.PGP.getID()) {
                return Motif.PGP;
            } else if (detection.id == Motif.PPG.getID()) {
                return Motif.PPG;
            }
        }
        return null;
    }

}

