package org.firstinspires.ftc.teamcode.opmode.test.sensor;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.HardwareNames;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.sensor.AprilTagCamera;

@Disabled
@TeleOp(name = "April Tag Positioning Test", group = "test")
public class AprilTagPositioningTest extends OpMode {
    private HardwareNames hardwareNames = new HardwareNames();
    private AprilTagCamera aprilTagCamera;
    private Pose2D position = new Pose2D(0, 0, 0);

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;
        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(HardwareNames.Name.WEBCAM_VISION_SENSOR)));

    }

    @Override
    public void loop() {
        aprilTagCamera.updateDetections();
        position = aprilTagCamera.getPositionFromGoalAprilTag(position);
        telemetry.addLine(position.toString());
        telemetry.addLine(aprilTagCamera.returnAllAprilTagData());
        telemetry.addData("range from blue goal", aprilTagCamera.getRange(aprilTagCamera.getDetection(20)));
        telemetry.addData("range from red goal", aprilTagCamera.getRange(aprilTagCamera.getDetection(24)));
        telemetry.addData("Motif", String.valueOf(aprilTagCamera.getMotif()));

        telemetry.update();
    }

}
