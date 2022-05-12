// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
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
  public static double distanceFromLimelightToGoal;
  public static double targetOffsetAngle_Vertical;
  public static double limelightMountAngleDegrees = 51.5,limelightLensHeight = 0.73;
  public static double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
  public static double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
  final public static double precentPowerPerMeter = 0.3368208092485549;
  static boolean b = false;
  //public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  //public static DigitalInput sw = new DigitalInput(3);
  //public static BuiltInAccelerometer acc = new BuiltInAccelerometer();
  //public static DigitalInput _switch2 = new DigitalInput(1);
 // public static double angle = 0;
  public static int angle = 0;
  public static boolean shouldContinue = false;
  public static Encoder leftEncoder = new Encoder(Constants.Drive.Left_Rear_Motor, Constants.Drive.Left_Front_Motor, true, EncodingType.k4X);
  public static Encoder rightEncoder = new Encoder(Constants.Drive.Right_Rear_Motor, Constants.Drive.Right_Front_Motor, true, EncodingType.k4X);
  public static PIDController pid = new PIDController(0.5, 0.5, 0.1);

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
    NetworkTableInstance in = NetworkTableInstance.getDefault();

    targetOffsetAngle_Vertical = in.getTable("limelight").getEntry("ty").getNumber(0).doubleValue();
    limelightMountAngleDegrees = 51.5;
    limelightLensHeight = 0.73;
    angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    Constants.Drive.distanceFromLimelightToGoal = (Constants.Shoot.goalHeight - limelightLensHeight)/Math.tan(angleToGoalRadians);
    Constants.Shoot.ShootPower = 0.55;
    if(System.currentTimeMillis()- startTime <= 3000) m_robotContainer.getShootingSubSystem().shoot();
    if(System.currentTimeMillis() - startTime <= 2000) m_robotContainer.autoPosition(distanceFromLimelightToGoal);
    if(System.currentTimeMillis() - startTime < 2500 && startTime > 2000) Constants.Drive.movement = 0;
    if(System.currentTimeMillis()- startTime >= 2500 && System.currentTimeMillis() - startTime < 3000)m_robotContainer.getShootingSubSystem().open();
    if(System.currentTimeMillis()- startTime >= 3000 && System.currentTimeMillis() - startTime < 3500) m_robotContainer.getShootingSubSystem().stopBlocker() ;
    if(System.currentTimeMillis()- startTime >= 4000 && System.currentTimeMillis() - startTime < 4500)m_robotContainer.getShootingSubSystem().close();
    if(System.currentTimeMillis()- startTime >= 4500 && System.currentTimeMillis() - startTime < 5000)
    {
      m_robotContainer.getShootingSubSystem().stopBlocker();     
      m_robotContainer.getShootingSubSystem().stop();
    }
    if(System.currentTimeMillis() - startTime  >= 5000 && System.currentTimeMillis() - startTime < 6500) Constants.Drive.movement = 0.7;
    else Constants.Drive.movement = 0;
  }
  @Override
  public void teleopInit() {
    Constants.Shoot.ShootPower = 0.6;
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
    m_robotContainer.getDriverSubsystem().feed();
    m_robotContainer.teleopPeriodic();

    targetOffsetAngle_Vertical = m_robotContainer.m_Limelight.getTy();
    limelightMountAngleDegrees = 51.5;
    limelightLensHeight = 0.73;
    angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    Constants.Drive.distanceFromLimelightToGoal = (Constants.Shoot.goalHeight - limelightLensHeight)/Math.tan(angleToGoalRadians);

    if(m_robotContainer.controller.getYButton()) m_robotContainer.autoAim();
    if(m_robotContainer.controller.getXButton()) m_robotContainer.autoPosition(Constants.Drive.distanceFromLimelightToGoal);

    if(!m_robotContainer.controller.getXButton() && !m_robotContainer.controller.getYButton())
    {
      Constants.Drive.rotation = m_robotContainer.controller.getRightX();
      Constants.Drive.movement = m_robotContainer.controller.getLeftY();

    //this next: if, else if and else are for the auto aim in the X axis.
    if(m_robotContainer.controller.getLeftY() != 0 && !shouldContinue || m_robotContainer.controller.getRightX() != 0 && !shouldContinue){
      m_robotContainer.getDriverSubsystem().y_speed = m_robotContainer.controller.getLeftY();
      m_robotContainer.getDriverSubsystem().x_speed = m_robotContainer.controller.getRightX();
    }
    if(m_robotContainer.controller.getBButtonPressed())
     b = !b;
    if(b)
      Constants.Shoot.ShootPower = precentPowerPerMeter * distanceFromLimelightToGoal;
    else
      Constants.Shoot.ShootPower = 0.6;
    }
    else {
      m_robotContainer.getDriverSubsystem().StopDrive();
    }

    //this is for testing the PID
    if(m_robotContainer.controller.getXButton()){
      //we need to get the distance from the hub and the limelight and use the example below to affect the power of the motors.
      //motor.set(pid.calculate(encoder.getDistance(), setpoint));
      //the setpoint is the distance from the hub and the camera
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
