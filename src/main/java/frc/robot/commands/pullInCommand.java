package frc.robot.commands;

import static frc.robot.Robot.m_robotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class pullInCommand extends CommandBase{
    @Override
    public void initialize() {
        addRequirements(m_robotContainer.getClimbingSubSystem());
    }
    @Override
    public void execute() {
        m_robotContainer.getClimbingSubSystem().pull(Constants.Climb.PullUpPower);//releasing string for saftey    
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getClimbingSubSystem().stop();
    }
}
