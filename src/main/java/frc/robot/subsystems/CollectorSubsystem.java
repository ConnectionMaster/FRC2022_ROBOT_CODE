package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CollectorSubsystem extends SubsystemBase {
    private WPI_TalonSRX collectingMotor;
    public CollectorSubsystem(){
        this.collectingMotor = new WPI_TalonSRX(Constants.collect.CollectMotor);//creating a motor
        
    }
    public void Collect(double power){//setting the motor at a correct amount of speed so we could collect balls
            this.collectingMotor.set(ControlMode.PercentOutput , power);
    }
    public void stop(){
        this.collectingMotor.stopMotor();
    }
}