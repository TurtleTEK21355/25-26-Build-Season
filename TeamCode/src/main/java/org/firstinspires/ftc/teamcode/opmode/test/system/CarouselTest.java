/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;

@TeleOp(name = "Carousel Test", group = "test")
public class CarouselTest extends LinearOpMode {
    CarouselSystem carouselSystem;

    private StateRobot robot;

    public void initialize() {
        Pose2D startingPosition = (Pose2D) blackboard.get(StateRobot.POSITION_BLACKBOARD_KEY);
        AllianceSide side = (AllianceSide) blackboard.get(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY);
        robot = StateRobot.build(hardwareMap);
        robot.setPosition(startingPosition);
        robot.setAllianceSide(side);
    }

    @Override
    public void runOpMode() {
        TelemetryPasser.telemetry = telemetry;
        initialize();


        waitForStart();
        while (opModeIsActive()) {
            robot.drivetrainFCControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            robot.manualControls(0,0, gamepad1.left_trigger, false, 0);

            telemetry.addData("Shoot", carouselSystem.getArtifactState(ColorSensorPosition.SHOOT));
            telemetry.addData("Left", carouselSystem.getArtifactState(ColorSensorPosition.LEFT));
            telemetry.addData("Right", carouselSystem.getArtifactState(ColorSensorPosition.RIGHT));
            telemetry.update();

        }
    }
}
