package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Robot.m_robotContainer;
public class OpenCloseBlocker extends CommandBase {
    private boolean open;
    public OpenCloseBlocker(boolean open)
    {
        this.open = open;
    }
    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        if(open)
        {
            m_robotContainer.getShootingSubsytem().open();
        }
        else
        {
            m_robotContainer.getShootingSubsytem().close();
        }
    }

    @Override
    public void end(boolean isInterrupted)
    {
        m_robotContainer.getShootingSubsytem().stopBlocker();

    }
    
}
