package frc.robot.commands;

import static frc.robot.Robot.m_robotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class CollectorCommand extends CommandBase {
    public void initialize() {
        addRequirements(m_robotContainer.getCollectorSubsystem());
    }
    @Override
    public void execute() {
        m_robotContainer.getCollectorSubsystem().Collect(Constants.collect.CollectPower);
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getCollectorSubsystem().stop();
    }
}