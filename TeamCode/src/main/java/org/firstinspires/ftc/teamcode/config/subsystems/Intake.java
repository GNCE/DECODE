package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.core.SubsysCore;
import org.firstinspires.ftc.teamcode.config.core.util.Artifact;
import org.firstinspires.ftc.teamcode.config.hardware.CachedMotor;

public class Intake extends SubsysCore {
    CachedMotor im;
    Servo piv;
    DigitalChannel pin0, pin1;
    double pwr;

    public Intake(){
        im = new CachedMotor(h.get(DcMotorEx.class, "intakeMotor"));
        piv = h.get(Servo.class, "intakePivot");
        pin0 = h.get(DigitalChannel.class, "digital0");
        pin1 = h.get(DigitalChannel.class, "digital1");
        pwr = 0;
    }

    public Artifact getCurrentArtifact(){
        if(pin0.getState()) return Artifact.PURPLE;
        else if(pin1.getState()) return Artifact.GREEN;
        return Artifact.NONE;
    }

    @Override
    public void periodic() {
        im.setPower(pwr);
    }
}
