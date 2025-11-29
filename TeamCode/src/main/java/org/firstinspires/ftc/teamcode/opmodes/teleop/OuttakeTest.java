package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.core.MyCommandOpMode;
import org.firstinspires.ftc.teamcode.config.core.MyRobot;
import org.firstinspires.ftc.teamcode.config.core.util.SubsystemConfig;

import java.util.List;

@Configurable
@TeleOp(name="Bare Shooter Test", group="Test")
public class OuttakeTest extends MyCommandOpMode {
    public static double targetVel = 15;
    public static double hoodAngle = 55;

    @Override
    public void initialize() {
        r = new MyRobot(hardwareMap, telemetry, gamepad1, gamepad2, List.of(SubsystemConfig.SHOOTER));
    }

    @Override
    public void initialize_loop() {

    }

    @Override
    public void run() {
        r.shooter.setVelocity(targetVel);
        r.shooter.setHoodAngle(hoodAngle);
    }
}
