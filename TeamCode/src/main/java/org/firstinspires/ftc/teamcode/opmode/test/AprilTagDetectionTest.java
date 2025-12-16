package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystems.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;

public class AprilTagDetectionTest extends OpMode {

    private HardwareNames hardwareNames = new HardwareNames();
    private AprilTagCamera aprilTagCamera;

    @Override
    public void init() {
        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(HardwareNames.Name.APRIL_TAG_CAMERA)));

    }

    @Override
    public void loop() {
        telemetry.addLine(aprilTagCamera.aprilTagTelemetry());
    }
}
