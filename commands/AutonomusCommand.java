package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.ShootingCommand;
import frc.robot.commands.InactiveShooting;
import static frc.robot.Robot.m_robotContainer;

public class AutonomusCommand extends CommandBase{
    public static long boi;
    public ShootingCommand obj;

    @Override
    public void initialize() {
        addRequirements(m_robotContainer.m_Limelight, m_robotContainer.getDriverSubsystem(), m_robotContainer.getShootingSubSystem());
        boi = System.currentTimeMillis();
    }
    @Override
    public void execute() {
        if(m_robotContainer.m_Limelight.getTy() > -1) m_robotContainer.getDriverSubsystem().ArcadeDrive(0, 0.1);
        else if(System.currentTimeMillis() - boi <= 5000){
            m_robotContainer.getDriverSubsystem().ArcadeDrive(0, 0);
            obj = new ShootingCommand();            
        } else {
            obj.cancel();
            new Thread(() -> {
                Boolean b = false;
                
                if(!b){
                    InactiveShooting no = new InactiveShooting();
                    try {
                        Thread.sleep(500l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    no.end(true);
                }
                b = true;
                
            });
        }
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getDriverSubsystem().StopDrive();
    }
}
