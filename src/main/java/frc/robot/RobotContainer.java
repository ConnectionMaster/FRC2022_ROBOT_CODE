// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
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
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public final Limelight m_Limelight = new Limelight();
  // The robot's subsystems and commands are defined here...
  
  private final ShootingSubsystem shootingSubsystem =  new ShootingSubsystem();
  private ShootingCommand shootingCommand;
  private InactiveShooting inactiveShooting;
  
  private final CollectorSubsystem collectorSubsystem = new CollectorSubsystem();
  private CollectorCommand collectorCommand;
  
  private final ClimbingSubSystem climbingSubSystem =new ClimbingSubSystem();
  private ClimbingCommand climbingCommand;
  // private final ControllerDriveSubsystem controllerDriveSubsystem = new ControllerDriveSubsystem();
  // private DriverControllerCommand driverControllerCommand;

  public static final DriverSubsystem driverSubsystem = new DriverSubsystem();
  private ArcadeDriveCommand arcadeDriveCommand;


  private PullOutCommand pullOutCommand;
  
  public Joystick tankStick1,tankStick2,CommandStick;
  public JoystickButton CommandStickButtons[] = new JoystickButton[12], DriveStickButtons[] = new JoystickButton[12];
  public XboxController controller;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    
    //this.shootingCommand = new ShootingCommand();
    this.inactiveShooting = new InactiveShooting();
    this.collectorCommand = new CollectorCommand();
    this.pullOutCommand = new PullOutCommand();
    this.arcadeDriveCommand = new ArcadeDriveCommand();
    configureButtonBindings();
    //this.driverControllerCommand = new DriverControllerCommand();
  }//FRCTRP4661
  public void teleopPeriodic(){
    driverSubsystem.feed();
  }
  public ShootingSubsystem getShootingSubsytem(){
    return this.shootingSubsystem;
  }
  public Command getShootingCommand(){
    return this.shootingCommand;
  }
  public Command getInactiveShooting()
  {
    return this.inactiveShooting;
  }
  public Joystick getStick(){
    return this.tankStick1;
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
  public Joystick getTankStick(){
    return this.tankStick2;
  }
  public ArcadeDriveCommand getArcadeDriveCommand(){
    return this.arcadeDriveCommand;
  }
  public Command getCollectorCommand(){
    return this.collectorCommand;
  }
  public Command getclimbingCommand(){
    return this.climbingCommand;
  }
  public Command getPullOutCommand(){
    return this.pullOutCommand;
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
    this.CommandStick = new Joystick(0);
    this.controller = new XboxController(3);
    for(int i = 0; i < CommandStickButtons.length; i++){
      CommandStickButtons[i] = new JoystickButton(this.CommandStick, i);
    }
    this.CommandStickButtons[2].whileHeld(this.getCollectorCommand());
    this.CommandStickButtons[1].whenInactive(this.getInactiveShooting());
    this.CommandStickButtons[3].whileHeld(this.getPullOutCommand());
    this.CommandStickButtons[6].whileHeld(new ReleasClimbingString());
    this.CommandStickButtons[4].whileHeld(new ClimbingCommand());
    this.CommandStickButtons[11].whileHeld(new pullInCommand());
    this.CommandStickButtons[9].whileHeld(new OpenCloseBlocker(false));
    this.CommandStickButtons[10].whileHeld(new OpenCloseBlocker(true));
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
}
