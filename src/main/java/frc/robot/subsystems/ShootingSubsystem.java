package frc.robot.subsystems;
import frc.robot.Constants;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootingSubsystem extends SubsystemBase {
  public XboxController firstDriversController = new XboxController(Constants.ControllersConstants.SECOND_DRIVERS_CONTROLLER);
  public PWMSparkMax leftCIMMotor; 
  public PWMSparkMax rightCIMMotor;
  public RelativeEncoder encoder;
  public PWMSparkMax intakeNEO = new PWMSparkMax(3);
  private CANSparkMax liftIntakeNEO;


  LinearFilter smoothFilter = LinearFilter.movingAverage(25);

  public ShootingSubsystem() {
    liftIntakeNEO = new CANSparkMax(26, CANSparkLowLevel.MotorType.kBrushless);
    leftCIMMotor = new PWMSparkMax(Constants.LaunchersConstants.LEFT_CIM_MOTOR);  // CIM motor left.
    rightCIMMotor = new PWMSparkMax(Constants.LaunchersConstants.RIGHT_CIM_MOTOR); // CIM motor right.

    encoder = liftIntakeNEO.getEncoder();
    encoder.setPosition(0);
    encoder.setPositionConversionFactor(0.035);
  }

  public void autoPos(double desiredPos){
    double getPos = encoder.getPosition();
    double speed = (getPos - desiredPos) / 3;

    liftIntakeNEO.set(-speed);
  }

  public void encoderReadout(double yAxis) { //TestPeriodic Function
    double pos = encoder.getPosition();
    double rpm = encoder.getPositionConversionFactor();

    System.out.println("RPM = " + rpm);
    System.out.println("Pos = " + pos);

    liftIntakeNEO.set(yAxis * 0.3);
  }

  public void intakeInsert(){
    Timer timer = new Timer();

    timer.start();
    do {
      intakeNEO.set(-0.5);
    }
    while (timer.get() < 0.3);
    intakeNEO.set(0);
  }

  public void intakeExtrude(){
    Timer timer = new Timer();

    timer.start();
    do {
      intakeNEO.set(0.8);
    }
    while (timer.get() < 0.3);
    intakeNEO.set(0);
  }

  public void shooterExtrude(){
    Timer timer = new Timer();

    timer.start();
    do {
      leftCIMMotor.set(1);
      rightCIMMotor.set(1);
    }
    while (timer.get() < 0.5);
    leftCIMMotor.set(0);
      rightCIMMotor.set(0);
  }

  public void shooterInsert(){
    Timer timer = new Timer();

    timer.start();
    do {
      leftCIMMotor.set(-0.4);
      rightCIMMotor.set(-0.4);
    }
    while (timer.get() < 0.2);
    leftCIMMotor.set(0);
      rightCIMMotor.set(0);
  }
}

  