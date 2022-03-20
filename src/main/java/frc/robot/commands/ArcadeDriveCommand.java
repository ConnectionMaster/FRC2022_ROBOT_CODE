package frc.robot.commands;

import static frc.robot.Robot.m_robotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ArcadeDriveCommand extends CommandBase{
    
    public ArcadeDriveCommand(){
        addRequirements(RobotContainer.driverSubsystem);
    }
    @Override
    public void initialize() {
        m_robotContainer.getDriverSubsystem().feed();
    }
    @Override
    public void execute() {
        if(m_robotContainer.getDriverSubsystem().x >= 0.4) m_robotContainer.getDriverSubsystem().x = 0.4;
        else if(m_robotContainer.getDriverSubsystem().x <= -0.4) m_robotContainer.getDriverSubsystem().x = -0.4;
        m_robotContainer.getDriverSubsystem().feed();
        m_robotContainer.getDriverSubsystem().ArcadeDrive(m_robotContainer.controller.getRightX(), -m_robotContainer.controller.getLeftY());
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getDriverSubsystem().feed();
        m_robotContainer.getDriverSubsystem().StopDrive();
    }
}