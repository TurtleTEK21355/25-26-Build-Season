package org.firstinspires.ftc.teamcode.opmode.auto.test;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Interactive subsystem test mode.
 * - Dpad Up/Down selects test
 * - A runs test
 * - B cancels running test
 * - Y runs full suite
 */
@Autonomous(name = "Interactive Subsystem Test (StateAuto)", group = "Test")
public class InteractiveSubsystemTestAuto extends StateAutoOpMode {

    @Override
    public void initialize() {
        // Set these BEFORE super.initialize()
        setAllianceSide(AllianceSide.BLUE);

        // If Motif.NONE doesn't exist in your enum, change/remove it.
        setMotif(Motif.NONE);

        setStartingPosition(new Pose2D(0, 0, 0));

        // Conservative PID (you can tune)
        kp = 0.08;  ki = 0.0;  kd = 0.006;
        kpTheta = 0.02; kiTheta = 0.0; kdTheta = 0.001;

        super.initialize();
    }

    @Override
    public void commands() {
        schedule(new InteractiveTestCommand(robot, this));
    }

    // ------------------------------------------------------------
    // Interactive Test Runner
    // ------------------------------------------------------------
    private static class InteractiveTestCommand extends CommandBase {

        private final StateRobot robot;
        private final InteractiveSubsystemTestAuto opMode;

        private final EdgeButtons edge = new EdgeButtons();

        // Tunables
        private double motorPower = 0.20;
        private boolean includeDriveMotorsInAllMotors = false;

        // Reflection device lists (cached)
        private final List<DeviceRef<DcMotor>> allMotors = new ArrayList<>();
        private final List<DeviceRef<Servo>> allServos = new ArrayList<>();

        private enum RunnerState { MENU, RUNNING, FULL_SUITE }
        private RunnerState state = RunnerState.MENU;

        // Menu tests
        private final List<TestItem> tests = new ArrayList<>();
        private int selectedIndex = 0;

        // Currently running test
        private TestInstance current = null;

        // Full suite bookkeeping
        private int suiteIndex = 0;

        InteractiveTestCommand(StateRobot robot, InteractiveSubsystemTestAuto opMode) {
            this.robot = robot;
            this.opMode = opMode;
        }

        @Override
        public void initialize() {
            buildDeviceCache();
            buildTestMenu();
            state = RunnerState.MENU;
            selectedIndex = 0;
            suiteIndex = 0;
            current = null;

            stopDrivetrain();
        }

        @Override
        public void execute() {
            edge.update(opMode.gamepad1);

            // Global: show pose
            Pose2D pose = robot.getOtosSensor().getPosition();

            // Allow quick power tuning in menu
            if (state == RunnerState.MENU) {
                if (edge.lbPressed()) motorPower = clamp(motorPower - 0.05, 0.05, 0.70);
                if (edge.rbPressed()) motorPower = clamp(motorPower + 0.05, 0.05, 0.70);
                if (edge.xPressed()) includeDriveMotorsInAllMotors = !includeDriveMotorsInAllMotors;
            }

            // Cancel
            if (state != RunnerState.MENU && edge.bPressed()) {
                cancelCurrent("Canceled by driver");
                state = RunnerState.MENU;
            }

            // Run state machine
            switch (state) {
                case MENU:
                    handleMenu(pose);
                    break;

                case RUNNING:
                    runCurrentTest(pose);
                    break;

                case FULL_SUITE:
                    runFullSuite(pose);
                    break;
            }

            renderTelemetry(pose);
        }

        @Override
        public boolean isFinished() {
            // Keep running until opmode ends
            return false;
        }

        @Override
        public void end(boolean interrupted) {
            cancelCurrent(interrupted ? "Interrupted" : "Ended");
            stopAllMotors();
            stopDrivetrain();
        }

        // -----------------------
        // MENU
        // -----------------------
        private void handleMenu(Pose2D pose) {
            if (edge.dpadUpPressed()) selectedIndex = (selectedIndex - 1 + tests.size()) % tests.size();
            if (edge.dpadDownPressed()) selectedIndex = (selectedIndex + 1) % tests.size();

            if (edge.aPressed()) {
                // Start selected test
                TestItem item = tests.get(selectedIndex);
                current = item.createInstance();
                current.start();
                state = RunnerState.RUNNING;
            }

            if (edge.yPressed()) {
                // Start full suite
                suiteIndex = 0;
                current = null;
                state = RunnerState.FULL_SUITE;
            }
        }

        // -----------------------
        // RUNNING
        // -----------------------
        private void runCurrentTest(Pose2D pose) {
            if (current == null) {
                state = RunnerState.MENU;
                return;
            }
            current.update(pose);

            if (current.isDone()) {
                current.stop("Completed");
                current = null;
                state = RunnerState.MENU;
            }
        }

        // -----------------------
        // FULL SUITE
        // -----------------------
        private void runFullSuite(Pose2D pose) {
            if (suiteIndex >= tests.size()) {
                // done
                state = RunnerState.MENU;
                current = null;
                return;
            }

            if (current == null) {
                current = tests.get(suiteIndex).createInstance();
                current.start();
            }

            current.update(pose);

            if (current.isDone()) {
                current.stop("Suite step complete");
                current = null;
                suiteIndex++;
            }
        }

        // -----------------------
        // Tests list
        // -----------------------
        private void buildTestMenu() {
            tests.clear();

            // Drivetrain PID tests
            tests.add(new TestItem("Drive PID: +20in then -20in", () ->
                    new DriveDistancePidTest(robot, +20, -20)));

            tests.add(new TestItem("Rotate PID: 0 -> 90 -> 0", () ->
                    new RotateHeadingPidTest(robot, 0, 90, 0)));

            // Targeted subsystem motor tests (by path matching)
            tests.add(new TestItem("Intake motor (fwd/rev)", () ->
                    new MotorByPathTest(robot, allMotors, motorPower, "intake")));

            tests.add(new TestItem("Flywheel motor (spin)", () ->
                    new MotorByPathTest(robot, allMotors, motorPower, "flywheel")));

            tests.add(new TestItem("Artifact lift motor (fwd/rev)", () ->
                    new MotorByPathTest(robot, allMotors, motorPower, "artifact")));

            tests.add(new TestItem("Partner park motor (fwd/rev)", () ->
                    new MotorByPathTest(robot, allMotors, motorPower, "partnerPark")));

            // Servo tests (by path matching)
            tests.add(new TestItem("Hood servo sweep", () ->
                    new ServoByPathSweepTest(allServos, "hood")));

            tests.add(new TestItem("Carousel servo sweep", () ->
                    new ServoByPathSweepTest(allServos, "carousel")));

            // Bulk tests
            tests.add(new TestItem("ALL MOTORS (low power)", () ->
                    new AllMotorsTest(allMotors, motorPower, includeDriveMotorsInAllMotors)));

            tests.add(new TestItem("ALL SERVOS sweep", () ->
                    new AllServosSweepTest(allServos)));

            tests.add(new TestItem("Limelight -> OTOS correction (snapshot)", () ->
                    new LimelightCorrectionTest(robot)));
        }

        // -----------------------
        // Device discovery
        // -----------------------
        private void buildDeviceCache() {
            allMotors.clear();
            allServos.clear();
            IdentityHashMap<Object, Boolean> visited = new IdentityHashMap<>();
            collectDevices(robot, "robot", visited);

            allMotors.sort(Comparator.comparing(a -> a.path.toLowerCase()));
            allServos.sort(Comparator.comparing(a -> a.path.toLowerCase()));
        }

        private void collectDevices(Object obj, String path, IdentityHashMap<Object, Boolean> visited) {
            if (obj == null) return;
            if (visited.containsKey(obj)) return;
            visited.put(obj, true);

            if (obj instanceof DcMotor) {
                allMotors.add(new DeviceRef<>(path, (DcMotor) obj));
                return;
            }
            if (obj instanceof Servo) {
                allServos.add(new DeviceRef<>(path, (Servo) obj));
                return;
            }

            Class<?> c = obj.getClass();
            String pkg = (c.getPackage() == null) ? "" : c.getPackage().getName();

            // Only recurse into your code packages to avoid crawling SDK internals too deeply
            if (!pkg.startsWith("org.firstinspires.ftc.teamcode")) return;

            for (Field f : c.getDeclaredFields()) {
                try {
                    f.setAccessible(true);
                    Object child = f.get(obj);
                    if (child == null) continue;
                    collectDevices(child, path + "." + f.getName(), visited);
                } catch (Exception ignored) { }
            }
        }

        // -----------------------
        // Telemetry
        // -----------------------
        private void renderTelemetry(Pose2D pose) {
            if (TelemetryPasser.telemetry == null) return;

            TelemetryPasser.telemetry.addLine("=== INTERACTIVE SUBSYSTEM TEST ===");
            TelemetryPasser.telemetry.addData("State", state);
            TelemetryPasser.telemetry.addData("OTOS Pose", "(%.2f, %.2f, %.1f°)", pose.x, pose.y, pose.h);

            if (state == RunnerState.MENU) {
                TelemetryPasser.telemetry.addLine("Menu: Dpad Up/Down select | A run | Y full suite | LB/RB power | X toggle drivetrain-in-all");
                TelemetryPasser.telemetry.addData("MotorPower", "%.2f", motorPower);
                TelemetryPasser.telemetry.addData("AllMotors includes drivetrain", includeDriveMotorsInAllMotors);

                // show 5-line window around selection
                int window = 2;
                for (int i = -window; i <= window; i++) {
                    int idx = (selectedIndex + i + tests.size()) % tests.size();
                    String prefix = (idx == selectedIndex) ? "👉 " : "   ";
                    TelemetryPasser.telemetry.addLine(prefix + tests.get(idx).name);
                }
            } else {
                TelemetryPasser.telemetry.addLine("Running: B cancels");
                if (current != null) {
                    TelemetryPasser.telemetry.addData("Test", current.name());
                    TelemetryPasser.telemetry.addData("Status", current.status());
                }
                if (state == RunnerState.FULL_SUITE) {
                    TelemetryPasser.telemetry.addData("Suite", (suiteIndex + 1) + "/" + tests.size());
                }
            }

            TelemetryPasser.telemetry.addData("Motors found", allMotors.size());
            TelemetryPasser.telemetry.addData("Servos found", allServos.size());
            TelemetryPasser.telemetry.update();
        }

        // -----------------------
        // Helpers
        // -----------------------
        private void cancelCurrent(String reason) {
            if (current != null) {
                current.stop(reason);
                current = null;
            }
            stopAllMotors();
            stopDrivetrain();
        }

        private void stopAllMotors() {
            for (DeviceRef<DcMotor> m : allMotors) {
                try { m.device.setPower(0); } catch (Exception ignored) {}
            }
        }

        private void stopDrivetrain() {
            try { robot.getDrivetrain().control(0, 0, 0); } catch (Exception ignored) {}
        }

        private static double clamp(double v, double lo, double hi) {
            return Math.max(lo, Math.min(hi, v));
        }
    }

    // ------------------------------------------------------------
    // Test infrastructure
    // ------------------------------------------------------------
    private static class TestItem {
        final String name;
        final Factory factory;

        TestItem(String name, Factory factory) {
            this.name = name;
            this.factory = factory;
        }

        TestInstance createInstance() {
            return factory.create(name);
        }

        interface Factory {
            TestInstance create(String name);
        }
    }

    private interface TestInstance {
        void start();
        void update(Pose2D pose);
        boolean isDone();
        void stop(String reason);
        String name();
        String status();
    }

    private static class DeviceRef<T> {
        final String path;
        final T device;
        DeviceRef(String path, T device) { this.path = path; this.device = device; }
    }

    // ------------------------------------------------------------
    // Individual tests
    // ------------------------------------------------------------

    /** Drive +20 inches then -20 inches using OTOS + PID (holds heading). */
    private static class DriveDistancePidTest implements TestInstance {
        private final StateRobot robot;
        private final double first, second;
        private final ElapsedTime timer = new ElapsedTime();

        private int phase = 0;
        private DistancePidController ctrl = null;
        private String status = "init";

        DriveDistancePidTest(StateRobot robot, double first, double second) {
            this.robot = robot;
            this.first = first;
            this.second = second;
        }

        @Override public void start() {
            phase = 0;
            timer.reset();
            ctrl = new DistancePidController(robot, first);
            ctrl.init();
            status = "Phase 1";
        }

        @Override public void update(Pose2D pose) {
            if (phase == 0) {
                ctrl.update();
                if (ctrl.done()) {
                    ctrl.stop();
                    phase = 1;
                    ctrl = new DistancePidController(robot, second);
                    ctrl.init();
                    status = "Phase 2";
                }
            } else if (phase == 1) {
                ctrl.update();
                if (ctrl.done()) {
                    ctrl.stop();
                    phase = 2;
                    status = "Done";
                }
            }
        }

        @Override public boolean isDone() { return phase >= 2 || timer.seconds() > 10; }
        @Override public void stop(String reason) {
            if (ctrl != null) ctrl.stop();
            status = reason;
        }
        @Override public String name() { return "Drive PID +20/-20"; }
        @Override public String status() { return status; }
    }

    /** Rotate headings with PID using OTOS heading. */
    private static class RotateHeadingPidTest implements TestInstance {
        private final StateRobot robot;
        private final double[] targets;
        private int idx = 0;
        private HeadingPidController ctrl = null;
        private final ElapsedTime timer = new ElapsedTime();
        private String status = "init";

        RotateHeadingPidTest(StateRobot robot, double... targets) {
            this.robot = robot;
            this.targets = targets;
        }

        @Override public void start() {
            idx = 0;
            timer.reset();
            ctrl = new HeadingPidController(robot, targets[idx]);
            ctrl.init();
            status = "Target " + targets[idx];
        }

        @Override public void update(Pose2D pose) {
            ctrl.update();
            if (ctrl.done()) {
                ctrl.stop();
                idx++;
                if (idx >= targets.length) {
                    status = "Done";
                    return;
                }
                ctrl = new HeadingPidController(robot, targets[idx]);
                ctrl.init();
                status = "Target " + targets[idx];
            }
        }

        @Override public boolean isDone() { return idx >= targets.length || timer.seconds() > 10; }
        @Override public void stop(String reason) { if (ctrl != null) ctrl.stop(); status = reason; }
        @Override public String name() { return "Rotate PID 0/90/0"; }
        @Override public String status() { return status; }
    }

    /** Runs the first motor matching substring in its path. */
    private static class MotorByPathTest implements TestInstance {
        private final StateRobot robot;
        private final List<DeviceRef<DcMotor>> motors;
        private final double pwr;
        private final String match;
        private DeviceRef<DcMotor> chosen = null;
        private final ElapsedTime timer = new ElapsedTime();
        private int stage = 0; // 0 fwd, 1 rev, 2 done
        private String status = "init";

        MotorByPathTest(StateRobot robot, List<DeviceRef<DcMotor>> motors, double pwr, String match) {
            this.robot = robot;
            this.motors = motors;
            this.pwr = pwr;
            this.match = match.toLowerCase();
        }

        @Override public void start() {
            chosen = null;
            for (DeviceRef<DcMotor> m : motors) {
                if (m.path.toLowerCase().contains(match)) { chosen = m; break; }
            }
            timer.reset();
            stage = 0;

            if (chosen == null) status = "Not found: " + match;
            else status = "Running: " + chosen.path;
        }

        @Override public void update(Pose2D pose) {
            if (chosen == null) return;

            if (stage == 0) {
                chosen.device.setPower(+pwr);
                if (timer.milliseconds() > 600) { stage = 1; timer.reset(); }
            } else if (stage == 1) {
                chosen.device.setPower(-pwr);
                if (timer.milliseconds() > 600) { stage = 2; timer.reset(); chosen.device.setPower(0); }
            }
        }

        @Override public boolean isDone() { return chosen == null || stage >= 2 || timer.seconds() > 5; }
        @Override public void stop(String reason) { if (chosen != null) chosen.device.setPower(0); status = reason; }
        @Override public String name() { return "Motor test: " + match; }
        @Override public String status() { return status; }
    }

    /** Runs all motors sequentially (low power). */
    private static class AllMotorsTest implements TestInstance {
        private final List<DeviceRef<DcMotor>> motors;
        private final double pwr;
        private final boolean includeDrive;
        private int idx = 0;
        private int stage = 0; // 0 fwd 1 rev 2 next
        private final ElapsedTime timer = new ElapsedTime();
        private String status = "init";

        AllMotorsTest(List<DeviceRef<DcMotor>> motors, double pwr, boolean includeDrive) {
            this.motors = motors;
            this.pwr = pwr;
            this.includeDrive = includeDrive;
        }

        @Override public void start() {
            idx = 0;
            stage = 0;
            timer.reset();
            status = "Running";
        }

        @Override public void update(Pose2D pose) {
            if (idx >= motors.size()) return;

            DeviceRef<DcMotor> m = motors.get(idx);
            String path = m.path.toLowerCase();

            // optionally skip drivetrain motors
            if (!includeDrive && path.contains("drivetrain")) {
                idx++;
                stage = 0;
                timer.reset();
                return;
            }

            if (stage == 0) {
                m.device.setPower(+pwr);
                if (timer.milliseconds() > 400) { stage = 1; timer.reset(); }
            } else if (stage == 1) {
                m.device.setPower(-pwr);
                if (timer.milliseconds() > 400) { stage = 2; timer.reset(); }
            } else {
                m.device.setPower(0);
                idx++;
                stage = 0;
                timer.reset();
            }
        }

        @Override public boolean isDone() { return idx >= motors.size(); }

        @Override public void stop(String reason) {
            for (DeviceRef<DcMotor> m : motors) {
                try { m.device.setPower(0); } catch (Exception ignored) {}
            }
            status = reason;
        }

        @Override public String name() { return "ALL MOTORS"; }
        @Override public String status() { return status + " (" + idx + "/" + motors.size() + ")"; }
    }

    /** Sweeps servos that match a substring. */
    private static class ServoByPathSweepTest implements TestInstance {
        private final List<DeviceRef<Servo>> servos;
        private final String match;
        private DeviceRef<Servo> chosen = null;
        private final ElapsedTime timer = new ElapsedTime();
        private int stage = 0;
        private String status = "init";

        ServoByPathSweepTest(List<DeviceRef<Servo>> servos, String match) {
            this.servos = servos;
            this.match = match.toLowerCase();
        }

        @Override public void start() {
            chosen = null;
            for (DeviceRef<Servo> s : servos) {
                if (s.path.toLowerCase().contains(match)) { chosen = s; break; }
            }
            timer.reset();
            stage = 0;

            if (chosen == null) status = "Not found: " + match;
            else status = "Sweeping: " + chosen.path;
        }

        @Override public void update(Pose2D pose) {
            if (chosen == null) return;

            if (stage == 0) {
                chosen.device.setPosition(0.2);
                if (timer.milliseconds() > 400) { stage = 1; timer.reset(); }
            } else if (stage == 1) {
                chosen.device.setPosition(0.8);
                if (timer.milliseconds() > 400) { stage = 2; timer.reset(); }
            } else if (stage == 2) {
                chosen.device.setPosition(0.5);
                if (timer.milliseconds() > 400) { stage = 3; timer.reset(); }
            }
        }

        @Override public boolean isDone() { return chosen == null || stage >= 3; }
        @Override public void stop(String reason) { status = reason; }
        @Override public String name() { return "Servo sweep: " + match; }
        @Override public String status() { return status; }
    }

    /** Sweeps all servos sequentially. */
    private static class AllServosSweepTest implements TestInstance {
        private final List<DeviceRef<Servo>> servos;
        private int idx = 0;
        private int stage = 0;
        private final ElapsedTime timer = new ElapsedTime();
        private String status = "init";

        AllServosSweepTest(List<DeviceRef<Servo>> servos) { this.servos = servos; }

        @Override public void start() {
            idx = 0; stage = 0;
            timer.reset();
            status = "Running";
        }

        @Override public void update(Pose2D pose) {
            if (idx >= servos.size()) return;
            Servo s = servos.get(idx).device;

            if (stage == 0) {
                s.setPosition(0.2);
                if (timer.milliseconds() > 300) { stage = 1; timer.reset(); }
            } else if (stage == 1) {
                s.setPosition(0.8);
                if (timer.milliseconds() > 300) { stage = 2; timer.reset(); }
            } else {
                s.setPosition(0.5);
                idx++;
                stage = 0;
                timer.reset();
            }
        }

        @Override public boolean isDone() { return idx >= servos.size(); }
        @Override public void stop(String reason) { status = reason; }
        @Override public String name() { return "ALL SERVOS"; }
        @Override public String status() { return status + " (" + idx + "/" + servos.size() + ")"; }
    }

    /** Calls correctPositionFromLL once and shows whether pose changed. */
    private static class LimelightCorrectionTest implements TestInstance {
        private final StateRobot robot;
        private boolean done = false;
        private String status = "init";

        LimelightCorrectionTest(StateRobot robot) { this.robot = robot; }

        @Override public void start() {
            Pose2D before = robot.getOtosSensor().getPosition();
            robot.correctPositionFromLL();
            Pose2D after = robot.getOtosSensor().getPosition();

            double dx = after.x - before.x;
            double dy = after.y - before.y;
            double dh = after.h - before.h;

            status = String.format("Δ(%.2f, %.2f, %.1f°)", dx, dy, dh);
            done = true;
        }

        @Override public void update(Pose2D pose) { }
        @Override public boolean isDone() { return done; }
        @Override public void stop(String reason) { status = reason; }
        @Override public String name() { return "Limelight -> OTOS correction"; }
        @Override public String status() { return status; }
    }

    // ------------------------------------------------------------
    // PID controllers used by tests (OTOS-based)
    // ------------------------------------------------------------

    private static class DistancePidController {
        private final StateRobot robot;
        private final double targetInches;

        private Pose2D startPose;
        private double startHeadingDeg;

        private double integral, prevErr;
        private double thetaIntegral, prevThetaErr;
        private long prevNanos;
        private long startNanos;

        private final double maxDrive = 0.6;
        private final double maxTurn  = 0.35;
        private final double posTol   = 0.7;
        private final double headTol  = 2.5;
        private final double timeout  = 4.0;

        DistancePidController(StateRobot robot, double targetInches) {
            this.robot = robot;
            this.targetInches = targetInches;
        }

        void init() {
            startPose = robot.getOtosSensor().getPosition();
            startHeadingDeg = startPose.h;

            integral = 0; prevErr = 0;
            thetaIntegral = 0; prevThetaErr = 0;

            prevNanos = System.nanoTime();
            startNanos = prevNanos;

            robot.getDrivetrain().control(0, 0, 0);
        }

        void update() {
            Pose2D cur = robot.getOtosSensor().getPosition();

            long now = System.nanoTime();
            double dt = (now - prevNanos) / 1e9;
            if (dt < 1e-4) dt = 1e-4;
            prevNanos = now;

            // distance along starting heading axis
            double headingRad = Math.toRadians(startHeadingDeg);
            double dx = cur.x - startPose.x;
            double dy = cur.y - startPose.y;
            double traveled = dx * Math.cos(headingRad) + dy * Math.sin(headingRad);

            double err = targetInches - traveled;

            PIDConstants drive = robot.getDrivetrain().getPIDConstants();
            PIDConstants theta = robot.getDrivetrain().getThetaPIDConstants();

            integral += err * dt;
            double deriv = (err - prevErr) / dt;
            prevErr = err;

            double driveCmd = drive.kp * err + drive.ki * integral + drive.kd * deriv;
            driveCmd = clamp(driveCmd, -maxDrive, maxDrive);

            double thetaErr = wrapDeg(startHeadingDeg - cur.h);
            thetaIntegral += thetaErr * dt;
            double thetaDeriv = (thetaErr - prevThetaErr) / dt;
            prevThetaErr = thetaErr;

            double turnCmd = theta.kp * thetaErr + theta.ki * thetaIntegral + theta.kd * thetaDeriv;
            turnCmd = clamp(turnCmd, -maxTurn, maxTurn);

            robot.getDrivetrain().control(driveCmd, 0.0, turnCmd);
        }

        boolean done() {
            Pose2D cur = robot.getOtosSensor().getPosition();

            double headingRad = Math.toRadians(startHeadingDeg);
            double dx = cur.x - startPose.x;
            double dy = cur.y - startPose.y;
            double traveled = dx * Math.cos(headingRad) + dy * Math.sin(headingRad);

            double err = targetInches - traveled;
            double thetaErr = wrapDeg(startHeadingDeg - cur.h);
            double elapsed = (System.nanoTime() - startNanos) / 1e9;

            return (Math.abs(err) <= posTol && Math.abs(thetaErr) <= headTol) || elapsed >= timeout;
        }

        void stop() {
            robot.getDrivetrain().control(0, 0, 0);
        }
    }

    private static class HeadingPidController {
        private final StateRobot robot;
        private final double targetHeadingDeg;

        private double integral, prevErr;
        private long prevNanos, startNanos;

        private final double maxTurn = 0.5;
        private final double tolDeg  = 2.5;
        private final double timeout = 3.0;

        HeadingPidController(StateRobot robot, double targetHeadingDeg) {
            this.robot = robot;
            this.targetHeadingDeg = targetHeadingDeg;
        }

        void init() {
            integral = 0;
            prevErr = 0;
            prevNanos = System.nanoTime();
            startNanos = prevNanos;
            robot.getDrivetrain().control(0, 0, 0);
        }

        void update() {
            Pose2D cur = robot.getOtosSensor().getPosition();

            long now = System.nanoTime();
            double dt = (now - prevNanos) / 1e9;
            if (dt < 1e-4) dt = 1e-4;
            prevNanos = now;

            double err = wrapDeg(targetHeadingDeg - cur.h);

            PIDConstants theta = robot.getDrivetrain().getThetaPIDConstants();

            integral += err * dt;
            double deriv = (err - prevErr) / dt;
            prevErr = err;

            double turnCmd = theta.kp * err + theta.ki * integral + theta.kd * deriv;
            turnCmd = clamp(turnCmd, -maxTurn, maxTurn);

            robot.getDrivetrain().control(0.0, 0.0, turnCmd);
        }

        boolean done() {
            Pose2D cur = robot.getOtosSensor().getPosition();
            double err = wrapDeg(targetHeadingDeg - cur.h);
            double elapsed = (System.nanoTime() - startNanos) / 1e9;
            return Math.abs(err) <= tolDeg || elapsed >= timeout;
        }

        void stop() {
            robot.getDrivetrain().control(0, 0, 0);
        }
    }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    private static double wrapDeg(double deg) {
        while (deg > 180) deg -= 360;
        while (deg <= -180) deg += 360;
        return deg;
    }

    // ------------------------------------------------------------
    // Button edge detection helper
    // ------------------------------------------------------------
    private static class EdgeButtons {
        private boolean prevA, prevB, prevX, prevY;
        private boolean prevUp, prevDown;
        private boolean prevLB, prevRB;

        private boolean a, b, x, y, up, down, lb, rb;

        void update(com.qualcomm.robotcore.hardware.Gamepad g) {
            a = g.a && !prevA; prevA = g.a;
            b = g.b && !prevB; prevB = g.b;
            x = g.x && !prevX; prevX = g.x;
            y = g.y && !prevY; prevY = g.y;

            up = g.dpad_up && !prevUp; prevUp = g.dpad_up;
            down = g.dpad_down && !prevDown; prevDown = g.dpad_down;

            lb = g.left_bumper && !prevLB; prevLB = g.left_bumper;
            rb = g.right_bumper && !prevRB; prevRB = g.right_bumper;
        }

        boolean aPressed() { return a; }
        boolean bPressed() { return b; }
        boolean xPressed() { return x; }
        boolean yPressed() { return y; }

        boolean dpadUpPressed() { return up; }
        boolean dpadDownPressed() { return down; }

        boolean lbPressed() { return lb; }
        boolean rbPressed() { return rb; }
    }
}
