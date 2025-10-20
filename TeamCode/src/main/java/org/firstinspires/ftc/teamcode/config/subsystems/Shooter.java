package org.firstinspires.ftc.teamcode.config.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;

import org.firstinspires.ftc.teamcode.config.core.SubsysCore;

@Configurable
public class Shooter extends SubsysCore {
    MotorGroup flywheel;
    Servo hood;
    public static double ZERO_OFFSET = 0;
    public static double INACTIVE_VELOCITY = 10;
    public static double READY_THRESHOLD = 1;
    public static double gearRatio = 0.1;
    double vel;
    public static double kp = 0.5, ki=0, kd=0.05;


    public Shooter(){
        // TODO: Assumed not inverted. Confirm.
        flywheel = new MotorGroup(new Motor(h, "shooter1", Motor.GoBILDA.BARE), new Motor(h, "shooter2", Motor.GoBILDA.BARE));
        flywheel.setRunMode(Motor.RunMode.VelocityControl);
        flywheel.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        setDefaultCommand(new RunCommand(() -> vel=INACTIVE_VELOCITY, this));
    }

    public void turnOn(){}

    public boolean readyToShoot(){
        return Math.abs(vel-flywheel.getVelocity()) < READY_THRESHOLD;
    }

    @Override
    public void periodic() {
        flywheel.setVeloCoefficients(kp, ki, kd);
        flywheel.set(vel);
    }
}
