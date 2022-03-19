package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Robot.m_robotContainer;
public class InactiveShooting extends CommandBase{
    private long startTime;
    @Override
    public void initialize()
    {
        addRequirements(m_robotContainer.getShootingSubsytem());
        startTime = System.currentTimeMillis();
    }

    @Override
    public void execute()
    {
        if(System.currentTimeMillis() - this.startTime <= 400){ 
            m_robotContainer.getShootingSubsytem().close();
            System.out.println("Close!");
        
        }
        else{
            System.out.println("Stop!");
 
            m_robotContainer.getShootingSubsytem().stopBlocker();
        }
    }

    @Override
    public void end(boolean isInterrupted)
    {
        m_robotContainer.getShootingSubsytem().stopBlocker();
    }
    
}
