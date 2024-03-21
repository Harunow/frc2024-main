// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private XboxController firstDriverController;
  private XboxController secondDriverController;
  private DrivetrainSubsystem m_drivetrain_test;
  private ShootingSubsystem m_intake_encoder;
  private double pos;
  private int goal;
  private int povValue;
  //private VictorTest m_test;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    firstDriverController = new XboxController(Constants.ControllersConstants.FIRST_DRIVERS_CONTROLLER);
    secondDriverController = new XboxController(Constants.ControllersConstants.SECOND_DRIVERS_CONTROLLER);


    m_intake_encoder = new ShootingSubsystem();
    m_drivetrain_test = new DrivetrainSubsystem();

    pos = 0;
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}


  @Override
  public void autonomousInit() {
    // schedule the autonomous command (example)
    goal = 0;
    povValue = -1;
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    Timer timer = new Timer();

    timer.restart();
    m_intake_encoder.autoPos(pos);
    m_intake_encoder.getPOVValues(povValue);

    // Task Definers
    if (timer.hasElapsed(1)) {
      goal = 1;
    }
    if (timer.hasElapsed(6)) {
      goal = 2;
    }
    if (timer.hasElapsed(6.5)) {
      goal = 3;
    }
    if (timer.hasElapsed(11.5)) {
      goal = 4;
    }
    if (timer.hasElapsed(12)) {
      goal = 4;
    }
    System.out.println(goal);

    // Task Values
    switch (goal) {
      case 0 -> {povValue = 0; pos = -1.25;} // Shooter Extract - LiftIntake pos = ground
      case 1 -> {povValue = -1; m_drivetrain_test.driveBoth(-0.7, 0, 1);} // Stop Shooter - Drive towards 2nd Note
      case 2 -> povValue = 270; //Intake Insert
      case 3 -> {povValue = -1; m_drivetrain_test.driveBoth(0.7, 0, 1); pos = 0;} // Stop Intake - Drive towards Speaker - LiftIntake pos = shooter
      case 4 -> povValue = 45; // Intake & Shooter Extrude
      case 5 -> povValue = -1; // Stop Intake & Shooter
    }
  }

  @Override//
  public void teleopInit() {

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
   
    double spd =  firstDriverController.getLeftX();
    double rotation = firstDriverController.getLeftY();
    double drivetrainthrottle = firstDriverController.getRightTriggerAxis();
    
    povValue = secondDriverController.getPOV();
   
    m_drivetrain_test.driveBoth(spd, rotation, drivetrainthrottle);
    m_intake_encoder.autoPos(pos);
    // Kazim's Controller
    m_intake_encoder.getPOVValues(povValue);

    //Egemen's Controller
    if (firstDriverController.getBButton()) {
      pos = 0;
    }
    if (firstDriverController.getYButton()) {
      //Degeri kontrol et
      pos = -0.45;
    }
    if (firstDriverController.getXButton()){
      //Degeri kontrol et
      pos = -1.3;
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();

  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    double rotation = firstDriverController.getLeftY();

    m_intake_encoder.encoderReadout(rotation);
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}