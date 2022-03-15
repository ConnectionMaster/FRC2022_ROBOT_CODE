package frc.robot.commands;

import static frc.robot.Robot.m_robotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class NormalShootCommand extends CommandBase{
    @Override
    public void initialize() {
        addRequirements(m_robotContainer.getShootingSubSystem());
    }
    @Override
    public void execute() {
        m_robotContainer.getShootingSubSystem().shoot(0.9);
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getShootingSubSystem().stop();
    }
}
