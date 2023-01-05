// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.ClimbingCommand;
import frc.robot.commands.CollectorCommand;
import frc.robot.commands.ReleasClimbingString;
import frc.robot.commands.ShootingCommand;
import frc.robot.commands.pullInCommand;
import frc.robot.commands.InactiveShooting;
import frc.robot.commands.OpenCloseBlocker;
import frc.robot.subsystems.ClimbingSubSystem;
import frc.robot.subsystems.CollectorSubsystem;
import frc.robot.subsystems.DriverSubsystem;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.ShootingSubsystem;
import frc.robot.commands.PullOutCommand;
import edu.wpi.first.math.controller.BangBangController;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public final Limelight m_Limelight = new Limelight();//creaing all of the commands and subsystem
  // The robot's subsystems and commands are defined here...
  
  private final ShootingSubsystem shootingSubsystem =  new ShootingSubsystem();
  private final CollectorSubsystem collectorSubsystem = new CollectorSubsystem();
  private final ClimbingSubSystem climbingSubSystem =new ClimbingSubSystem();
  // private final ControllerDriveSubsystem controllerDriveSubsystem = new ControllerDriveSubsystem();
  // private DriverControllerCommand driverControllerCommand;

  public static final DriverSubsystem driverSubsystem = new DriverSubsystem();
  public Joystick tankStick1,tankStick2,CommandStick;
  public JoystickButton CommandStickButtons[] = new JoystickButton[12], DriveStickButtons[] = new JoystickButton[12];
  public XboxController controller;
  public BangBangController bangController;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    CommandScheduler.getInstance().setDefaultCommand(driverSubsystem, new ArcadeDriveCommand());
    //driverSubsystem.getDiffDrive().setSafetyEnabled(false);
    //this.arcadeDriveCommand = new ArcadeDriveCommand();
    configureButtonBindings();
    //this.driverControllerCommand = new DriverControllerCommand();
  }//FRCTRP4661
  
  public ShootingSubsystem getShootingSubsytem(){//to much getters for the Commands and subsystems.
    return this.shootingSubsystem;
  }
  public DriverSubsystem getDriverSubsystem(){
    return driverSubsystem;
  }
  public ClimbingSubSystem getClimbingSubSystem(){
    return this.climbingSubSystem;
  }
  public CollectorSubsystem getCollectorSubsystem(){
    return this.collectorSubsystem;
  }
  public ShootingSubsystem getShootingSubSystem(){
    return this.shootingSubsystem;
  }
  // public Command getDriverControllerCommand(){
  //   return this.driverControllerCommand;
  // }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    this.CommandStick = new Joystick(0);//creating the joystick and the xbox controller
    this.controller = new XboxController(3);

    for(int i = 0; i < CommandStickButtons.length; i++){
      CommandStickButtons[i] = new JoystickButton(this.CommandStick, i);//setting up a value for each button in the joystick
    }
    //configering the buttons
    this.CommandStickButtons[1].whileHeld(new ShootingCommand());
    this.CommandStickButtons[1].whenInactive(new InactiveShooting());
    this.CommandStickButtons[2].whileHeld(new CollectorCommand());
    this.CommandStickButtons[2].whileHeld(new CollectorCommand());
    this.CommandStickButtons[1].whenInactive(new InactiveShooting());
    this.CommandStickButtons[4].whileHeld(new PullOutCommand());
    this.CommandStickButtons[5].whileHeld(new ReleasClimbingString());
    this.CommandStickButtons[3].whileHeld(new ClimbingCommand());
    this.CommandStickButtons[6].whileHeld(new pullInCommand());
    this.CommandStickButtons[9].whileHeld(new OpenCloseBlocker(false));
    this.CommandStickButtons[10].whileHeld(new OpenCloseBlocker(true));
  }
  
  public void autoAim(){//positioning the robot infront of the target
    double threshold = 0.2;
    
    if(this.m_Limelight.getTv() > 0){
      if(this.m_Limelight.getTx() <= threshold && this.m_Limelight.getTx() >= -threshold)
      {
        driverSubsystem.getDiffDrive().stopMotor();
        System.out.println("[debug] stopping aiming");
      }
      else if(!(this.m_Limelight.getTx() <= threshold && this.m_Limelight.getTx() >= -threshold))
      {
        System.out.println("in the loop");
        if((this.m_Limelight.getTx() >= threshold)) Constants.Drive.rotation = 0.7;
        if((this.m_Limelight.getTx() <= -threshold)) Constants.Drive.rotation = -0.7;
      }
    }
  }

  public void autoPosition(double distance){//positioning the robot at a correct distance from the target
    if(distance <= (1.73+0.1) && distance >= (1.73-0.1))
      driverSubsystem.getDiffDrive().stopMotor();
    else
    {
      if(distance >= (1.73+0.1)) Constants.Drive.movement = -0.7;
      if(distance <= (1.73-0.1)) Constants.Drive.movement = 0.7;
    }
  }

  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
}
