package org.firstinspires.ftc.teamcode.opmodes.auto;

import org.firstinspires.ftc.teamcode.config.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.config.commands.OuttakeAllCommand;
import org.firstinspires.ftc.teamcode.config.core.MyCommandOpMode;
import org.firstinspires.ftc.teamcode.config.core.MyRobot;
import org.firstinspires.ftc.teamcode.config.core.util.Alliance;
import org.firstinspires.ftc.teamcode.config.core.util.OpModeType;
import org.firstinspires.ftc.teamcode.config.paths.AutoPaths;

public class FrontSoloAuto extends MyCommandOpMode {
    AutoPaths autoPaths;

    @Override
    public void initialize() {
        r = new MyRobot(hardwareMap, telemetry, gamepad1, gamepad2, OpModeType.AUTO);
    }

    @Override
    public void initialize_loop() {
        r.preloadSelection();
    }

    @Override
    public void atStart() {
        autoPaths = new AutoPaths(r.f, MyRobot.isRed ? Alliance.RED : Alliance.BLUE);
        r.overrideAutoEndPose(autoPaths.getPose(AutoPaths.PoseId.START_FRONT));
        schedule(
                new FollowPathCommand(r.f, autoPaths.getPath(AutoPaths.PathId.START_FRONT_TO_SHOOT_FRONT)),
                new OuttakeAllCommand(r.intake, r.spindex, r.turret, r.shooter, r.door)
        );
    }
}
