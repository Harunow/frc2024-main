package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootingSubsystem extends SubsystemBase {
  public final PWMSparkMax leftCIMMotor;
  public final PWMSparkMax rightCIMMotor;
  public final RelativeEncoder encoder;
  public final PWMSparkMax intakeNEO = new PWMSparkMax(3);
  private final CANSparkMax liftIntakeNEO;

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
      double speed = (getPos - desiredPos) * 0.7;

    liftIntakeNEO.set(-speed);
  }

  public void encoderReadout(double yAxis) { //TestPeriodic Function
    double pos = encoder.getPosition();
    double rpm = encoder.getPositionConversionFactor();

    System.out.println("RPM = " + rpm);
    System.out.println("Pos = " + pos);

    liftIntakeNEO.set(yAxis * 0.3);
  }

  public void getPOVValues(int powValue){
    switch (powValue) {
      // Unpressed
      case -1 -> {
        intakeNEO.set(0.0);
        leftCIMMotor.set(0.0);
        rightCIMMotor.set(0.0);
      }
      // Pressed - 1 Part working
      case 0 -> {
        intakeNEO.set(0.0);
        leftCIMMotor.set(0.9);
        rightCIMMotor.set(0.9);
      }
      case 90 -> {
          intakeNEO.set(1);
        leftCIMMotor.set(0.0);
        rightCIMMotor.set(0.0);
      }
      case 180 -> {
        intakeNEO.set(0.0);
          leftCIMMotor.set(-0.5);
          rightCIMMotor.set(-0.5);
      }
      case 270 -> {
        intakeNEO.set(-0.3);
        leftCIMMotor.set(0.0);
        rightCIMMotor.set(0.0);
      }
      // Pressed - 2 Part Working
      case 45 -> {
        intakeNEO.set(0.7);
        leftCIMMotor.set(0.9);
        rightCIMMotor.set(0.9);
      }
      case 225 -> {
        intakeNEO.set(-0.3);
          leftCIMMotor.set(-0.5);
          rightCIMMotor.set(-0.5);
      }
    }
  }
}