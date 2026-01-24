package org.firstinspires.ftc.teamcode.opmode.test.sensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystems.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;

@TeleOp(name = "April Tag Detection Test", group = "test")
public class AprilTagDetectionTest extends OpMode {

    private HardwareNames hardwareNames = new HardwareNames();
    private AprilTagCamera aprilTagCamera;

    @Override
    public void init() {
        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(HardwareNames.Name.APRIL_TAG_CAMERA)));

    }

    @Override
    public void loop() {
        aprilTagCamera.updateDetections();
        telemetry.addLine(aprilTagCamera.returnAllAprilTagData());
        telemetry.addData("range from blue goal", aprilTagCamera.getRange(aprilTagCamera.getDetection(20)));
        telemetry.addData("range from red goal", aprilTagCamera.getRange(aprilTagCamera.getDetection(24)));
        telemetry.addData("Motif", String.valueOf(aprilTagCamera.getMotif()));

        telemetry.update();
    }

}
