package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

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
        m_robotContainer.getShootingSubsytem().shoot(0.9);
        if((System.currentTimeMillis() - startTime) >= 2000 && (System.currentTimeMillis() - startTime) < 2500){
            m_robotContainer.getShootingSubsytem().open();
        } 
        if(System.currentTimeMillis() - startTime >= 2500){
            m_robotContainer.getShootingSubsytem().stopBlocker();
            System.out.println("Stop!");
        } 
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("end");
        m_robotContainer.getShootingSubsytem().stop();        
    }
}
