package frc.robot.commands;

import static frc.robot.Robot.m_robotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class ClimbingCommand extends CommandBase {
    @Override
    public void initialize() {
        addRequirements(m_robotContainer.getClimbingSubSystem());
    }

    @Override
    public void execute() {
        m_robotContainer.getClimbingSubSystem().expand(Constants.Climb.ExpandPower);//expending the rod which contains the hook that we use to climb with
    }
    @Override
    public void end(boolean interrupted) {
        m_robotContainer.getClimbingSubSystem().stop();;

    }
}
