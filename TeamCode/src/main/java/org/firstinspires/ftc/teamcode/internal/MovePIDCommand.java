package org.firstinspires.ftc.teamcode.internal;

public class MovePIDCommand extends Command{
    Pose2D position;
    Pose2D target;
    double speed;
    Drivetrain drivetrain;
    PIDControllerSpeedLimit yPID;
    PIDControllerSpeedLimit xPID;
    PIDControllerHeading hPID;


    public MovePIDCommand(Drivetrain drivetrain, Pose2D target, double speed) {
        this.drivetrain = drivetrain;
        this.target = target;
        this.speed = speed;
        yPID = new PIDControllerSpeedLimit(drivetrain.getPIDConstants(), target.y, drivetrain.getTolerance().y, speed);
        xPID = new PIDControllerSpeedLimit(drivetrain.getPIDConstants(), target.x, drivetrain.getTolerance().x, speed);
        hPID = new PIDControllerHeading(drivetrain.getPIDConstants(), target.h, drivetrain.getTolerance().h, speed);
    }

    @Override
    public void loop() {
        position = drivetrain.getPosition();
        drivetrain.fcControl(yPID.calculate(position.y), xPID.calculate(position.x), hPID.calculate(position.h));
    }

    @Override
    public boolean is_completed() {
        return (!yPID.atTarget(position.y) || !xPID.atTarget(position.x) || !hPID.atTarget(position.h));
    }

}
