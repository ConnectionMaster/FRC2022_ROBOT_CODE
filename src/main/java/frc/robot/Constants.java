// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
s * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {
    public static class Drive{
        public static double rotation = 0;
        public static double movement = 0;
        final public static int Right_Rear_Motor = 3;
        final public static int Right_Front_Motor = 4;
        final public static int Left_Rear_Motor = 2;
        final public static int Left_Front_Motor = 1;
        public static double distanceFromLimelightToGoal;
    }
    public final class Climb {
        final public static int ClimbMotor = 9 , PullMotor = 6;
        final public static double ExpandPower = -0.5 ; 
        final public static double PullUpPower = 0.5 ;
        final public static double PullDownPower = -0.5;
        final public static double releasePower = 0.45 ; 
        final public static int LimitSwitchPort = 3 ;
        final public static int stopPower = 0 ;
    }
    public static class Shoot{
        final public static int ShootMotor = 7;
        final public static int BlockerMotor = 11;
        final public static int InactiveTime = 500;
        public static double ShootPower = 0.55;
        final public static int preparing_Time_for_shoot = 2000;
        final public static int Ready_to_shoot = 2500;
        final public static int stopPower = 0 ;
        final public static double CloseBlockerPower = 0.3 ;
        final public static double OpenBlockerPower = -0.3 ;
        final public static double goalHeight = 2.63;
        final public static double limelightMountAngleDegrees = 55.5;
        final public static double limelightLensHeight = 0.73;
    }
    public final class collect{
        final public static int CollectMotor = 10 ;

        final public static double CollectPower = 0.8 ;
        final public static int stopPower = 0 ;
    }
}
