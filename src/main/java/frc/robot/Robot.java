// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.InactiveShooting;
import frc.robot.commands.ShootingCommand;
import edu.wpi.first.cameraserver.CameraServer;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public static RobotContainer m_robotContainer;
  //public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  //public static DigitalInput sw = new DigitalInput(3);
  //public static BuiltInAccelerometer acc = new BuiltInAccelerometer();
  //public static DigitalInput _switch2 = new DigitalInput(1);
 // public static double angle = 0;
  public static long startTime, telepStartTime;
  public static int angle = 0;
  public static boolean shouldContinue = false;
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
    //m_robotContainer.getShootingSubsytem().servo.setAngle(200); 
    
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
    if(System.currentTimeMillis() - startTime <= 3000) {m_robotContainer.getShootingSubSystem().shoot(0.80);}//need to change when the limelight is here
    if(System.currentTimeMillis()- startTime >= 2000 && System.currentTimeMillis() - startTime < 2500){m_robotContainer.getShootingSubSystem().open();}
    else if(System.currentTimeMillis() - startTime >= 3500 && System.currentTimeMillis() - startTime < 4250) {m_robotContainer.getShootingSubSystem().close();}
    else if(System.currentTimeMillis() - startTime >= 3750 && System.currentTimeMillis() - startTime < 4000) {m_robotContainer.getShootingSubSystem().stopBlocker();}
    //this is it
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
    CommandScheduler.getInstance().setDefaultCommand(m_robotContainer.getDriverSubsystem(),m_robotContainer.getArcadeDriveCommand()); 
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_robotContainer.CommandStickButtons[1].whileHeld(new ShootingCommand());
    m_robotContainer.CommandStickButtons[1].whenInactive(new InactiveShooting());
    m_robotContainer.getDriverSubsystem().feed();
    m_robotContainer.teleopPeriodic();

    if(m_robotContainer.controller.getLeftY() != 0 && !shouldContinue || m_robotContainer.controller.getRightX() != 0 && !shouldContinue){
      m_robotContainer.getDriverSubsystem().y = m_robotContainer.controller.getLeftY();
      m_robotContainer.getDriverSubsystem().x = m_robotContainer.controller.getRightX();
    }
    else if(m_robotContainer.controller.getYButton()){
      if(m_robotContainer.m_Limelight.getTx() > 4) m_robotContainer.getDriverSubsystem().x += 0.1;
      else if(m_robotContainer.m_Limelight.getTx() < -4) m_robotContainer.getDriverSubsystem().x -= 0.1;
      else m_robotContainer.getDriverSubsystem().StopDrive();
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
