// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public final class Drive{
        final public static int Right_Rear_Motor = 3;
        final public static int Right_Front_Motor = 11;
        final public static int Left_Rear_Motor = 2;
        final public static int Left_Front_Motor = 1;
    }
    public final class Climb {
        final public static int ClimbMotor = 4 , PullMotor = 6;
        final public static double ExpandPower = 0.7 ; 
        final public static double PullUpPower = 0.5 ;
        final public static double PullDownPower = -0.5;
        final public static double releasePower = -0.45 ; 
        final public static int LimitSwitchPort = 3 ;
        final public static int stopPower = 0 ;
    }
    public final class Shoot{
        final public static int ShootMotor = 5;
        final public static int BlockerMotor = 9;
        final public static int InactiveTime = 400;
        final public static double ShootPower = 0.9;
        final public static int preparing_Time_for_shoot = 2000;
        final public static int Ready_to_shoot = 2500;
        final public static int stopPower = 0 ;
        final public static double CloseBlockerPower = 0.3 ;
        final public static double OpenBlockerPower = -0.3 ;
    }
    public final class collect{
        final public static int CollectMotor = 8;
        final public static double CollectPower = 0.9 ;
        final public static int stopPower = 0 ;
    }
}
