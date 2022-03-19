package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.lang.Number;
public class Limelight extends SubsystemBase{
    private NetworkTableInstance limelightNT;
    public Limelight(){
        limelightNT = NetworkTableInstance.getDefault();
    }

    public int getTv(){
        return this.limelightNT.getTable("limelight").getEntry("tv").getNumber(9999).intValue();
    }

    public double getTx(){
        return this.limelightNT.getTable("limelight").getEntry("tx").getNumber(9999).doubleValue();
    }

    public double getTy(){
        return this.limelightNT.getTable("limelight").getEntry("ty").getNumber(9999).doubleValue();
    }

    public double getTa(){
        return this.limelightNT.getTable("limelight").getEntry("ta").getNumber(9999).doubleValue();
    }
    public void logData(){
        System.out.println("***limelight - output***");
        System.out.println("[*] Do we have a target? - " + (getTv() == 1 ? "yes":"no"));
        System.out.println("[*] Horizontal offset of target - " + getTx());
        System.out.println("[*] Vertical offset of target - " + getTy());
        System.out.println("[*] Area of target - " + getTa());
    }
}
