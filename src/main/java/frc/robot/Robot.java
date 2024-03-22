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
  private int task;
  private int povValue;
  private Timer timer;

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
    povValue = -1;
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

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}


  @Override
  public void autonomousInit() {
    // schedule the autonomous command (example)
    timer = new Timer();

    timer.reset();
    timer.start();

    task = -1;
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    System.out.println(timer.get());

    m_intake_encoder.autoPos(pos);
    m_intake_encoder.getPOVValues(povValue);

    // Task Definers
    if (0 < timer.get() && timer.get() < 1.5) { // 1.5 sec
      task = 1;
    }
    if (1.5 < timer.get() && timer.get() < 2) { // 0.5 sec
      task = 2;
    }
    if (2 < timer.get() && timer.get() < 3.5) { // 1.5 sec
      task = 3;
    }
    if (3.5 < timer.get() && timer.get() < 4.5) { // 1 sec
      task = 4;
    }
    if (4.5 < timer.get() && timer.get() < 6) { // 1.5 sec
      task = 5;
    }
    if (6.5 < timer.get() && timer.get() < 7) { // 0.5 sec
      task = 6;
    }
    if (7 < timer.get() && timer.get() < 8.5) { // 1.5 sec
      task = 7;
    }
    if (8.5 < timer.get()) { // Last Task
      task = 8;
    }
    System.out.println(task);

    // Task Values
    switch (task) {
      case 1 -> { // Intake Down & Shooter Extrude
        povValue = 0;
        pos = -1.1;
      }
      case 2 -> {
        povValue = -1;
        m_drivetrain_test.driveBoth(-0.7, 0, 1);
      }
      case 3 -> m_drivetrain_test.driveBoth(0, -0.7, 1);
      case 4 -> povValue = 270;
      case 5 -> {
        povValue = -1;
        m_drivetrain_test.driveBoth(0, 0.7, 1);
        pos = 0;
      }
      case 6 -> {
        m_drivetrain_test.driveBoth(0.7, 0, 1);
        pos = 0;
      }
      case 8 -> pos = 0;
      case 9 -> povValue = 45;
      case 10 -> {
        povValue = -1;
        System.out.println("Program Stopped");
      }
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
      pos = -1.1;
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