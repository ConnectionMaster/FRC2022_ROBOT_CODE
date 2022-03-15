package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Constants;
public class ShootingSubsystem extends SubsystemBase{
    private WPI_TalonSRX shootingMotor;
    private WPI_TalonSRX blocker;
    //public Servo servo;

    public ShootingSubsystem(){
        this.shootingMotor = new WPI_TalonSRX(Constants.Shoot.ShootMotor);
        this.blocker = new WPI_TalonSRX(Constants.Shoot.BlockerMotor);
        //servo = new Servo(9);
        //servo.setAngle(200);
    }
    public void shoot(double power){
        this.shootingMotor.set(ControlMode.PercentOutput, power);
    }
    public void open(){
        this.blocker.set(ControlMode.PercentOutput, -0.3);
    }
    public void stopBlocker()
    {
        this.blocker.set(ControlMode.PercentOutput, 0);
    }
    public void close()
    {
        this.blocker.set(ControlMode.PercentOutput, 0.3);
    }

    public void stop(){
        this.shootingMotor.set(ControlMode.PercentOutput,0);
        
        
        //this.servo.setAngle(180);
    }
}
