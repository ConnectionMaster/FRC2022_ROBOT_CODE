package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import static frc.robot.Robot.m_robotContainer;

public class ShootingCommand extends CommandBase {
    private long startTime;

    @Override
    public void initialize() {
        addRequirements(m_robotContainer.getShootingSubsytem());
        this.startTime = System.currentTimeMillis();
    }
    
    @Override
    public void execute() {
        m_robotContainer.getShootingSubsytem().shoot(/*Constants.Shoot.ShootPower*/);
        if((System.currentTimeMillis() - startTime) >= Constants.Shoot.preparing_Time_for_shoot && (System.currentTimeMillis() - startTime) < Constants.Shoot.Ready_to_shoot) m_robotContainer.getShootingSubsytem().open();
        if(System.currentTimeMillis() - startTime >= Constants.Shoot.Ready_to_shoot) m_robotContainer.getShootingSubsytem().stopBlocker(); 
    }

    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getShootingSubsytem().stop();        
    }
}
