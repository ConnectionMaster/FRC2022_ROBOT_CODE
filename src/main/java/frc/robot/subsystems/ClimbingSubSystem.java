package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbingSubSystem extends SubsystemBase {
    public WPI_TalonSRX climbMotor ; 
    public WPI_TalonSRX expend_motor;
    private DigitalInput the_limit_switch;

    public ClimbingSubSystem(){
        expend_motor = new WPI_TalonSRX(Constants.Climb.PullMotor);//creating motors
        climbMotor = new WPI_TalonSRX(Constants.Climb.ClimbMotor/*port number [can change]*/);
        this.the_limit_switch =new DigitalInput(Constants.Climb.LimitSwitchPort);//creating a limit switch(DIO) so we could know when to stop expending
    }
    
    //the expand funcation cause the robot to open his climbing arm 
    public void expand(double speed){//expend the rod which holds our hook that we climb with
        expend_motor.set(ControlMode.PercentOutput,speed);
    }
    public boolean getSwitch(){//getting the value of the limit switch (was it pressed: true or false)
        return this.the_limit_switch.get();
    }
    public void pull(double speed){//pulling a string with the motor to lift up the robot
        climbMotor.set(ControlMode.PercentOutput , speed);
    }
    public void releaseString(double speed){//releasing the string for saftey
        expend_motor.set(ControlMode.PercentOutput, speed);
    }
    public void stop(){//stoping all of the motors from working.
        expend_motor.stopMotor();
        climbMotor.stopMotor();
    }
}
