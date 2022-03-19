package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Servo;

public class ShootingSubsystem extends SubsystemBase{
    private WPI_TalonSRX shootingMotor;
    private WPI_TalonSRX blocker;
    public Servo servo;

    public ShootingSubsystem(){
        this.shootingMotor = new WPI_TalonSRX(Constants.Shoot.ShootMotor);
        this.blocker = new WPI_TalonSRX(Constants.Shoot.BlockerMotor);
        servo = new Servo(9);
    }
    public void shoot(double power){
        this.shootingMotor.set(ControlMode.PercentOutput, power);
    }
    public void open(){
        this.blocker.set(ControlMode.PercentOutput, Constants.Shoot.OpenBlockerPower);
    }
    public void stopBlocker()
    {
        this.blocker.set(ControlMode.PercentOutput, Constants.Shoot.stopPower);
    }
    public void close()
    {
        this.blocker.set(ControlMode.PercentOutput, Constants.Shoot.CloseBlockerPower);
    }

    public void stop(){
        this.shootingMotor.set(ControlMode.PercentOutput,Constants.Shoot.stopPower);
    }
}
