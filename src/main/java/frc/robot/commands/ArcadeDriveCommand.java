package frc.robot.commands;

import static frc.robot.Robot.m_robotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ArcadeDriveCommand extends CommandBase{
    
    public ArcadeDriveCommand(){
        addRequirements(RobotContainer.driverSubsystem);
    }
    @Override
    public void initialize() {
        m_robotContainer.getDriverSubsystem().feed();//feeding the diff object data (thanks WPILib)
    }
    @Override
    public void execute() {
        m_robotContainer.getDriverSubsystem().feed();//feeding the diff object data (thanks WPILib)
        m_robotContainer.getDriverSubsystem().ArcadeDrive(Constants.Drive.rotation, -Constants.Drive.movement);//updatiing the movement of the robot based on gloval variables
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getDriverSubsystem().feed();//stoping the robot from moving
        m_robotContainer.getDriverSubsystem().StopDrive();
    }
}