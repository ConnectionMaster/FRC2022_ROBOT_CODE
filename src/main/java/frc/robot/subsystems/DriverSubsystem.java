package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriverSubsystem extends SubsystemBase {
    private WPI_TalonSRX MotorRightFront , MotorRightRear;
    public static MotorControllerGroup SPD_Right;
    private WPI_TalonSRX MotorLeftFront , MotorLeftRear;
    public static MotorControllerGroup SPD_Left;
    public MotorControllerGroup SPD_Forward;
    public MotorControllerGroup SPD_BackWard;
    private DifferentialDrive diff;
    public  double x_speed = 0,y_speed = 0;

    public DriverSubsystem(){
        this.MotorRightFront =new WPI_TalonSRX(Constants.Drive.Right_Front_Motor);
        this.MotorRightRear = new WPI_TalonSRX(Constants.Drive.Right_Rear_Motor);
        SPD_Right = new MotorControllerGroup(this.MotorRightFront, this.MotorRightRear);
        this.MotorLeftFront = new WPI_TalonSRX(Constants.Drive.Left_Front_Motor);
        this.MotorLeftRear = new WPI_TalonSRX(Constants.Drive.Left_Rear_Motor);
        SPD_Left = new MotorControllerGroup(this.MotorLeftFront, this.MotorLeftRear);
        this.diff = new DifferentialDrive(SPD_Left, SPD_Right);
    }
    public DifferentialDrive getDiffDrive(){
        return this.diff;
    }
    public void feed(){
        this.diff.feedWatchdog();
    }
    public void ArcadeDrive(double stickY, double stickX){
        this.diff.arcadeDrive(stickY*0.75, 0.75*stickX, true);
    }
    public void TankDrive(double left, double right){
        this.diff.tankDrive(left*0.9, right*0.9);
    }
    public void StopDrive(){
        this.diff.stopMotor();
    }
}