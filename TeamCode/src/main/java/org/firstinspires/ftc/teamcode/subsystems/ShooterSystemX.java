package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.AllianceSide;
import org.firstinspires.ftc.teamcode.Motif;

public class ShooterSystemX {
    private TurretSystem turretSystem;
    private CarouselSystem carouselSystem;
    private Intake intake;
    private Motif motif;
    private AllianceSide side;

    public ShooterSystemX(TurretSystem turretSystem, CarouselSystem carouselSystem, Intake intake, AllianceSide side){
        this.turretSystem = turretSystem;
        this.intake = intake;
        this.carouselSystem = carouselSystem;
        this.side = side;

    }

    public void teleOpControl() {
        //color sensor feedback
        //rotation from controllers
        //hood control from calculated distance
        //flywheel power from calculated distance
        //shooting from controllers
    }

    public String teleOpTelemetry() {
        StringBuilder string = new StringBuilder()
                .append("this is telemetry\n <-- that is a line break")
                .append("hello");
        return string.toString();
    }
}
