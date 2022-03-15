package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Robot.m_robotContainer;

public class TimedShoot extends CommandBase{
    long starttime;
    @Override
    public void initialize() {
        addRequirements(m_robotContainer.getShootingSubSystem());
        starttime = System.currentTimeMillis();
        System.out.println("hey bitch");
    }
    @Override
    public void execute() {

            m_robotContainer.getShootingSubSystem().shoot(0.9);
            System.out.println("hey bitch");
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getShootingSubSystem().stop();
    }
    
}
