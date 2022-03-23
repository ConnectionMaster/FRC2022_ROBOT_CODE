// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.CollectorCommand;
import frc.robot.commands.InactiveShooting;
import frc.robot.commands.ShootingCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.controller.PIDController;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public static RobotContainer m_robotContainer;
  public static long startTime;
  public static boolean shouldContinue = false;
  public static PIDController pid = new PIDController(0.4, 0, 0);
  public static PIDController pid_wheels = new PIDController(0.5, 0.5 , 0.1);
  public final static double shootPowerForMeter = 0.52023121387;
  public static double distanceFromLimelightToGoal;
  public static double leftSideDistance, rightSideDistance;
  public static WPI_TalonSRX motor = new WPI_TalonSRX(20);
  
  public static double targetOffsetAngle_Vertical;
  public static double limelightMountAngleDegrees = 43.0,limelightLensHeight = 0.68;
  public static double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
  public static double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

  int mode;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture(0);
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    targetOffsetAngle_Vertical = m_robotContainer.m_Limelight.getTy();
  }
  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
    m_robotContainer.getDriverSubsystem().feed();
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    startTime = System.currentTimeMillis();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // shoot to the hub from the auto line 
    if(System.currentTimeMillis() - startTime <= 3000) {m_robotContainer.getShootingSubSystem().shoot(/*0.8*/);}//need to change when the limelight is here
    if(System.currentTimeMillis()- startTime >= 2000 && System.currentTimeMillis() - startTime < 2500){m_robotContainer.getShootingSubSystem().open();}
    else if(System.currentTimeMillis() - startTime >= 3500 && System.currentTimeMillis() - startTime < 4000) {m_robotContainer.getShootingSubSystem().close();}
    else if(System.currentTimeMillis() - startTime >= 3750 && System.currentTimeMillis() - startTime < 4000) 
    {
      m_robotContainer.getShootingSubSystem().stopBlocker();
      m_robotContainer.getShootingSubSystem().stop();
    }
    else if(System.currentTimeMillis() - startTime >= 4000 && System.currentTimeMillis() - startTime < 7000) 
    {
      m_robotContainer.getDriverSubsystem().SPD_Forward.set(0.4);
      m_robotContainer.getDriverSubsystem().SPD_BackWard.set(0.4);
    }
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    //CommandScheduler.getInstance().setDefaultCommand(m_robotContainer.getDriverSubsystem(),m_robotContainer.getArcadeDriveCommand()); 
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_robotContainer.CommandStickButtons[1].whileHeld(new ShootingCommand());
    m_robotContainer.CommandStickButtons[1].whenInactive(new InactiveShooting());
    m_robotContainer.CommandStickButtons[2].whileHeld(new CollectorCommand());

    m_robotContainer.getDriverSubsystem().feed();
    m_robotContainer.teleopPeriodic();
    
    double targetOffsetAngle_Vertical = m_robotContainer.m_Limelight.getTy();
    double limelightMountAngleDegrees = 51.5, limelightLensHeight = 0.73;
    double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    distanceFromLimelightToGoal = (Constants.Shoot.goalHeight - limelightLensHeight)/Math.tan(angleToGoalRadians);
    System.out.println(distanceFromLimelightToGoal);
    // if(m_robotContainer.m_Limelight.getTv() > 0) Constants.Shoot.ShootPower = distanceFromLimelightToGoal * 0.52023121139;
    // else Constants.Shoot.ShootPower = 0.7;
    Constants.Shoot.ShootPower = 0.65;
    if(m_robotContainer.controller.getYButton())
    {
        m_robotContainer.autoAim();
    }

    if(m_robotContainer.controller.getXButton())
    {
      m_robotContainer.autoPosition(distanceFromLimelightToGoal);
    }

    if(!m_robotContainer.controller.getXButton() && !m_robotContainer.controller.getYButton())
    {
      Constants.Drive.rotation = m_robotContainer.controller.getRightX();
      Constants.Drive.movement = m_robotContainer.controller.getLeftY();
    }
}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
