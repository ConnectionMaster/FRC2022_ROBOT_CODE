package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import static frc.robot.Robot.m_robotContainer;

public class ReleasClimbingString extends CommandBase{
    @Override
    public void initialize() {
        addRequirements(m_robotContainer.getClimbingSubSystem());
    }
    @Override
    public void execute() {
        m_robotContainer.getClimbingSubSystem().releaseString(Constants.Climb.releasePower);
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getClimbingSubSystem().stop();
    }
}
