package frc.robot.commands;

import static frc.robot.Robot.m_robotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PullOutCommand extends CommandBase{
    static boolean interrupted = false;
    @Override
    public void initialize() {
        addRequirements(m_robotContainer.getClimbingSubSystem());
    }
    @Override
    public void execute() {
        
        if(m_robotContainer.getClimbingSubSystem().getSwitch() && !interrupted)
        {
            
            m_robotContainer.getClimbingSubSystem().expand(-0.50);
        }
        else
        {
            interrupted = true;
            end(true);
        }
        
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getClimbingSubSystem().stop();
    }
}
