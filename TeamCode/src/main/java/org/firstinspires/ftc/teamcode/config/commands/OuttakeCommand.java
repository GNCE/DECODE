package org.firstinspires.ftc.teamcode.config.commands;

import com.seattlesolvers.solverslib.command.DeferredCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.config.core.util.Artifact;
import org.firstinspires.ftc.teamcode.config.subsystems.Door;
import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.config.subsystems.Spindex;
import org.firstinspires.ftc.teamcode.config.subsystems.Turret;

import java.util.Collections;

public class OuttakeCommand extends SequentialCommandGroup {

    public OuttakeCommand(Artifact artifact, Spindex spindex, Door door, Shooter shooter, Turret turret, Intake intake){
        addCommands(
                new InstantCommand(shooter::turnOn),
                new ParallelCommandGroup(
                        door.setOpenCommand(false),
                        spindex.goToSlot(artifact),
                        new WaitUntilCommand(turret::reachedTarget),
                        new WaitUntilCommand(shooter::readyToShoot)
                ),
                intake.setUpCommand(true),
                intake.setUpCommand(false)
        );
        addRequirements(spindex, door, shooter, intake);
    }
}
