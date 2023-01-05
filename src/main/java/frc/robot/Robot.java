// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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
  public static long startTime;
  final public static double precentPowerPerMeter = 0.3368208092485549;
  static boolean b = false;
  public static boolean shouldContinue = false;

  public final Compressor pcmComp = new Compressor(0, PneumaticsModuleType.CTREPCM);
  public final DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0,1);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    solenoid.set(Value.kReverse);

    CameraServer.startAutomaticCapture(0);
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    m_robotContainer.m_Limelight.getTy();
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
    
    //m_robotContainer.getDriverSubsystem().feed();
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
    
    Constants.Shoot.ShootPower = 0.55;
    //Using timers to execute commands on the robot in autonomous mode (We only missed onces).
    if(System.currentTimeMillis()- startTime <= 3000) m_robotContainer.getShootingSubSystem().shoot();
    if(System.currentTimeMillis() - startTime <= 2000) m_robotContainer.autoPosition(m_robotContainer.m_Limelight.getDistance());
    if(System.currentTimeMillis() - startTime < 2500 && startTime > 2000) Constants.Drive.movement = 0;
    else if(System.currentTimeMillis()- startTime >= 2500 && System.currentTimeMillis() - startTime < 3000)m_robotContainer.getShootingSubSystem().open();
    else if(System.currentTimeMillis()- startTime >= 3000 && System.currentTimeMillis() - startTime < 3500) m_robotContainer.getShootingSubSystem().stopBlocker() ;
    else if(System.currentTimeMillis()- startTime >= 4000 && System.currentTimeMillis() - startTime < 4500)m_robotContainer.getShootingSubSystem().close();
    else if(System.currentTimeMillis()- startTime >= 4500 && System.currentTimeMillis() - startTime < 5000) {
      m_robotContainer.getShootingSubSystem().stopBlocker();     
      m_robotContainer.getShootingSubSystem().stop();
    }
    else if(System.currentTimeMillis() - startTime  >= 5000 && System.currentTimeMillis() - startTime < 6500) Constants.Drive.movement = 0.7;
    else Constants.Drive.movement = 0;
  }
  @Override
  public void teleopInit() {
    pcmComp.enableDigital();
    Constants.Shoot.ShootPower = 0.6;//am
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
    //getting updates on the pressure in the compressor
    System.out.println(pcmComp.getPressure());

    m_robotContainer.getDriverSubsystem().feed();//feeding information to the diff object
    m_robotContainer.getDriverSubsystem().feed();

    Constants.Drive.distanceFromLimelightToGoal = m_robotContainer.m_Limelight.getDistance();//updating the distance from the target to a global variable.

    //Xbox Controller Button Config
    if(m_robotContainer.controller.getAButtonPressed()) solenoid.set(Value.kForward);//controling a solenoids
    if(m_robotContainer.controller.getRightStickButton()) solenoid.set(Value.kReverse);
    if(m_robotContainer.controller.getBButtonPressed()) b = !b;//updating the shooting power of the robot
    if(m_robotContainer.controller.getYButton()) m_robotContainer.autoAim();
    if(m_robotContainer.controller.getXButton()) m_robotContainer.autoPosition(Constants.Drive.distanceFromLimelightToGoal);
    Constants.Shoot.ShootPower = b ? precentPowerPerMeter * m_robotContainer.m_Limelight.getDistance(): 0.6;

    if(!m_robotContainer.controller.getXButton() && !m_robotContainer.controller.getYButton()) {//checking if the auto aim/position functions are running so we don't create a problem
      Constants.Drive.rotation = m_robotContainer.controller.getRightX();//updating the rotation and the movement power of the robot.
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
