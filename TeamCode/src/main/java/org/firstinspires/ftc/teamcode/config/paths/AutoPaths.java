package org.firstinspires.ftc.teamcode.config.paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.config.core.util.Alliance;

import java.util.EnumMap;

public final class AutoPaths {
    // ====== POSES (BLUE ONLY) ======
    public enum PoseId {
        START_FRONT (new Pose(54.69, 6.74, Math.toRadians(180))),
        START_BACK  (new Pose(54.69, 6.74, Math.toRadians(180))), // TODO

        SHOOT_FRONT (new Pose(58, 90, Math.toRadians(180))),
        SHOOT_BACK  (new Pose(54.69, 6.74, Math.toRadians(180))), // TODO
        OPEN_GATE   (new Pose(54.69, 6.74, Math.toRadians(180))), // TODO

        FRONT_SPIKE_START (new Pose(45, 84, Math.toRadians(180))),
        FRONT_SPIKE_END   (new Pose(20, 84, Math.toRadians(180))),

        MID_SPIKE_START   (new Pose(45, 60, Math.toRadians(180))),
        MID_SPIKE_END     (new Pose(20, 60, Math.toRadians(180))),

        FAR_SPIKE_START   (new Pose(45, 36, Math.toRadians(180))),
        FAR_SPIKE_END     (new Pose(20, 36, Math.toRadians(180)));

        private final Pose blue;

        PoseId(Pose blue) {
            this.blue = blue;
        }

        public Pose forAlliance(Alliance a) {
            return (a == Alliance.RED) ? blue.mirror() : blue;
        }
    }

    // ====== EVERY EDGE IS A PATH ======
    public enum PathId {
        START_FRONT_TO_SHOOT_FRONT,

        SHOOT_FRONT_TO_FRONT_SPIKE_START,
        FRONT_SPIKE_START_TO_FRONT_SPIKE_END,
        FRONT_SPIKE_END_TO_SHOOT_FRONT,

        SHOOT_FRONT_TO_MID_SPIKE_START,
        MID_SPIKE_START_TO_MID_SPIKE_END,
        MID_SPIKE_END_TO_SHOOT_FRONT,

        SHOOT_FRONT_TO_FAR_SPIKE_START,
        FAR_SPIKE_START_TO_FAR_SPIKE_END,
        FAR_SPIKE_END_TO_SHOOT_FRONT
    }

    // ====== INSTANCE STATE ======
    private final Follower f;
    private final Alliance a;

    private final EnumMap<PoseId, Pose> p     = new EnumMap<>(PoseId.class);
    private final EnumMap<PathId, PathChain> paths = new EnumMap<>(PathId.class);

    // ====== CONSTRUCTOR: BUILD EVERYTHING ======
    public AutoPaths(Follower f, Alliance a) {
        this.f = f;
        this.a = a;

        // Build alliance-correct poses
        for (PoseId id : PoseId.values()) {
            p.put(id, id.forAlliance(a));
        }

        // Build all individual path chains
        buildPaths();
    }

    // ====== ACCESS ======
    public Pose getPose(PoseId id) {
        return p.get(id);
    }

    public PathChain getPath(PathId id) {
        return paths.get(id);
    }

    // ====== PATH DEFINITIONS ======
    private void buildPaths() {

        // START → SHOOT
        put(PathId.START_FRONT_TO_SHOOT_FRONT,
                PoseId.START_FRONT, PoseId.SHOOT_FRONT);

        // FRONT SPIKE
        put(PathId.SHOOT_FRONT_TO_FRONT_SPIKE_START,
                PoseId.SHOOT_FRONT, PoseId.FRONT_SPIKE_START);
        put(PathId.FRONT_SPIKE_START_TO_FRONT_SPIKE_END,
                PoseId.FRONT_SPIKE_START, PoseId.FRONT_SPIKE_END);
        put(PathId.FRONT_SPIKE_END_TO_SHOOT_FRONT,
                PoseId.FRONT_SPIKE_END, PoseId.SHOOT_FRONT);

        // MID SPIKE
        put(PathId.SHOOT_FRONT_TO_MID_SPIKE_START,
                PoseId.SHOOT_FRONT, PoseId.MID_SPIKE_START);
        put(PathId.MID_SPIKE_START_TO_MID_SPIKE_END,
                PoseId.MID_SPIKE_START, PoseId.MID_SPIKE_END);
        put(PathId.MID_SPIKE_END_TO_SHOOT_FRONT,
                PoseId.MID_SPIKE_END, PoseId.SHOOT_FRONT);

        // FAR SPIKE
        put(PathId.SHOOT_FRONT_TO_FAR_SPIKE_START,
                PoseId.SHOOT_FRONT, PoseId.FAR_SPIKE_START);
        put(PathId.FAR_SPIKE_START_TO_FAR_SPIKE_END,
                PoseId.FAR_SPIKE_START, PoseId.FAR_SPIKE_END);
        put(PathId.FAR_SPIKE_END_TO_SHOOT_FRONT,
                PoseId.FAR_SPIKE_END, PoseId.SHOOT_FRONT);
    }

    // Small helper: ONE BezierLine = ONE PathChain
    private void put(PathId id, PoseId from, PoseId to) {
        Pose s = p.get(from);
        Pose e = p.get(to);

        paths.put(id,
                f.pathBuilder()
                        .addPath(new BezierLine(s, e))
                        .setLinearHeadingInterpolation(
                                s.getHeading(),
                                e.getHeading()
                        )
                        .build()
        );
    }
}
