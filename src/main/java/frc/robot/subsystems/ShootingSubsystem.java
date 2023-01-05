package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Constants;

public class ShootingSubsystem extends SubsystemBase{
    private WPI_TalonSRX shootingMotor;
    private WPI_TalonSRX blocker;
    
    public ShootingSubsystem(){
        this.shootingMotor = new WPI_TalonSRX(Constants.Shoot.ShootMotor);//creating a motor
        this.blocker = new WPI_TalonSRX(Constants.Shoot.BlockerMotor);
    }
    public void shoot(/* double shoot */){
        this.shootingMotor.set(ControlMode.PercentOutput, Constants.Shoot.ShootPower);
    }
    public void open(){//opening the blocker so balls can come through
        this.blocker.set(ControlMode.PercentOutput, Constants.Shoot.OpenBlockerPower);
    }
    public void stopBlocker(){ //stoping the blocker so he would stay in place (thank you WPILib)
        this.blocker.stopMotor();
    }
    public void close(){ //closing the blocker so no more balls can come through.
        this.blocker.set(ControlMode.PercentOutput, Constants.Shoot.CloseBlockerPower);
    }

    public void stop(){//stoping the shooting motor (thank you WPILib)
        this.shootingMotor.stopMotor();
    }
}
