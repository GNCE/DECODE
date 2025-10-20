package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.config.core.SubsysCore;
import org.firstinspires.ftc.teamcode.config.core.util.Artifact;
import org.firstinspires.ftc.teamcode.config.hardware.CachedMotor;

import kotlin.time.Instant;

@Configurable
public class Intake extends SubsysCore {
    CachedMotor im;
    Servo piv;
    DigitalChannel pin0, pin1; // Purple, Green
    double pwr;
    public static double INTAKE_PIVOT_ZERO_OFFSET = 0;
    public static double INTAKE_PIVOT_DOWN = 0.95;
    public static double INTAKE_PIVOT_TRANSFER = 0.72;
    public static long TRANSFER_ACTUATION_TIME_MS = 500;
    boolean pivotUp = false;

    public enum IntakeMotorPowerStates {
        INTAKE, TRANSFER, REJECT, STOP
    }
    public static class IntakeMotorPowerConfig {
        public static double INTAKE = 1;
        public static double TRANSFER = 1;
        public static double STOP = 0;
        public static double REJECT = -0.5;
    }

    public Intake(){
        im = new CachedMotor(h.get(DcMotorEx.class, "intakeMotor"));
        im.setDirection(DcMotorSimple.Direction.FORWARD);
        piv = h.get(Servo.class, "intakePivot");
        pin0 = h.get(DigitalChannel.class, "digital0");
        pin1 = h.get(DigitalChannel.class, "digital1");
        pwr = 0;

        this.setDefaultCommand(setPowerCommand(IntakeMotorPowerConfig.STOP));
    }

    public Command setPowerInstant(double newPower){
        return new InstantCommand(() -> pwr = newPower);
    }
    public Command setPowerCommand(double newPower) {
        return new RunCommand(() -> pwr = newPower, this);
    }
    public Command runUntilArtifactSensed(){
        return this.setPowerCommand(IntakeMotorPowerConfig.INTAKE).raceWith(new WaitUntilCommand(() -> getCurrentArtifact() != Artifact.NONE));
    }
    public Artifact getCurrentArtifact(){
        if(pin0.getState()) return Artifact.PURPLE;
        else if(pin1.getState()) return Artifact.GREEN;
        return Artifact.NONE;
    }

    public Command setUpCommand(boolean state){
        return new ConditionalCommand(
                new WaitCommand(TRANSFER_ACTUATION_TIME_MS),
                new InstantCommand(),
                () -> {
                    boolean changed = (pivotUp != state);
                    pivotUp = state;
                    return changed;
                }
        );
    }

    @Override
    public void periodic() {
        im.setPower(pwr);
        piv.setPosition(INTAKE_PIVOT_ZERO_OFFSET + (pivotUp?INTAKE_PIVOT_TRANSFER:INTAKE_PIVOT_DOWN));
        t.addData("Intake Power", pwr);
        t.addData("Intake Current", im.getCurrent());
        t.addData("Intake Artifact", getCurrentArtifact().name());
    }
}
