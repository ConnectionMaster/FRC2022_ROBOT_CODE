package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Constants;

public class ShootingSubsystem extends SubsystemBase{
    private WPI_TalonSRX shootingMotor;
    private WPI_TalonSRX blocker;
    
    public ShootingSubsystem(){
        this.shootingMotor = new WPI_TalonSRX(Constants.Shoot.ShootMotor);
        this.blocker = new WPI_TalonSRX(Constants.Shoot.BlockerMotor);
    }
    public void shoot(/* double shoot */){
        this.shootingMotor.set(ControlMode.PercentOutput, Constants.Shoot.ShootPower);
    }
    public void open(){
        this.blocker.set(ControlMode.PercentOutput, Constants.Shoot.OpenBlockerPower);
    }
    public void stopBlocker()
    {
        this.blocker.stopMotor();
    }
    public void close()
    {
        this.blocker.set(ControlMode.PercentOutput, Constants.Shoot.CloseBlockerPower);
    }

    public void stop(){
        this.shootingMotor.stopMotor();
    }
}
