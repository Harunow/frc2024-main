// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final class ControllersConstants {
    public static final int FIRST_DRIVERS_CONTROLLER = 0; // controller port for egemen's gamepad   
    public static final int SECOND_DRIVERS_CONTROLLER = 1;  // controller port for kazım's gamepad
  }

  
  public static final class LaunchersConstants {
      public static final int LEFT_CIM_MOTOR = 7; 
      public static final int RIGHT_CIM_MOTOR = 4; 
  }
  public static final class LiftsConstant{
      public static final int LL_CIM_MOTOR = 1; //Left Lift CIM Motor
      public static final int RL_CIM_MOTOR = 8; // Right Lift CIM Motor 
  }

  public static final int LF_MOTOR_CHANNEL = 5;
  public static final int RF_MOTOR_CHANNEL = 8;
  public static final int LR_MOTOR_CHANNEL = 14;
  public static final int RR_MOTOR_CHANNEL = 1;

  public static final WPI_VictorSPX LF_MOTOR = new WPI_VictorSPX(LF_MOTOR_CHANNEL);
  public static final WPI_VictorSPX RF_MOTOR = new WPI_VictorSPX(RF_MOTOR_CHANNEL);
  public static final WPI_VictorSPX LR_MOTOR = new WPI_VictorSPX(LR_MOTOR_CHANNEL);
  public static final WPI_VictorSPX RR_MOTOR = new WPI_VictorSPX(RR_MOTOR_CHANNEL);

  
  public static final DifferentialDrive FRONT_DRIVE = new DifferentialDrive(LF_MOTOR, RF_MOTOR);
  public static final DifferentialDrive REAR_DRIVE = new DifferentialDrive(LR_MOTOR, RR_MOTOR);
}