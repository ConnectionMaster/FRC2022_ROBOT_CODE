package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import static frc.robot.Robot.m_robotContainer;
public class InactiveShooting extends CommandBase{
    private long startTime;
    @Override
    public void initialize() {
        addRequirements(m_robotContainer.getShootingSubsytem());
        startTime = System.currentTimeMillis();//setting up a timer
    }

    @Override
    public void execute() {
        if(System.currentTimeMillis() - this.startTime <= Constants.Shoot.InactiveTime) m_robotContainer.getShootingSubsytem().close();//closing the blocker after he has been open
        else m_robotContainer.getShootingSubsytem().stopBlocker();
    }

    @Override
    public void end(boolean isInterrupted) {
        m_robotContainer.getShootingSubsytem().stopBlocker();
    }
    
}
