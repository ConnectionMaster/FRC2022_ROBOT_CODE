package frc.robot.commands;

import static frc.robot.Robot.m_robotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoAimCommand extends CommandBase{
    @Override
    public void initialize() {
        addRequirements(m_robotContainer.driverSubsystem, m_robotContainer.m_Limelight);
    }
    @Override
    public void execute() {
        if(m_robotContainer.m_Limelight.getTv() == 0) end(true);
        else if(m_robotContainer.m_Limelight.getTy() < -5) m_robotContainer.getDriverSubsystem().ArcadeDrive(0, 0.25);
        else if(m_robotContainer.m_Limelight.getTy() > 5) m_robotContainer.getDriverSubsystem().ArcadeDrive(0, -0.25);
        else if(m_robotContainer.m_Limelight.getTx() > 3) m_robotContainer.getDriverSubsystem().ArcadeDrive(-0.25, 0);
        else if(m_robotContainer.m_Limelight.getTx() < -3) m_robotContainer.getDriverSubsystem().ArcadeDrive(0.25, 0);
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getDriverSubsystem().StopDrive();
    }
}