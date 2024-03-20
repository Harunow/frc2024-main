package frc.robot.subsystems;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.XboxController;

public class LiftSubsystem {

    XboxController secondDriversController = new XboxController(Constants.ControllersConstants.SECOND_DRIVERS_CONTROLLER);
    public PWMSparkMax leftLiftCIM;
    public PWMSparkMax rightLiftCIM;

    public LiftSubsystem(){
       leftLiftCIM = new PWMSparkMax(Constants.LiftsConstant.LL_CIM_MOTOR); 
       rightLiftCIM = new PWMSparkMax(Constants.LiftsConstant.RL_CIM_MOTOR);
    }

    public void robotLift(PWMSparkMax leftLiftCIM ,PWMSparkMax rightLiftCIM){
        if(secondDriversController.getRightTriggerAxis() > 0){
            leftLiftCIM.set(secondDriversController.getRightTriggerAxis());
        }
        if(secondDriversController.getLeftTriggerAxis() > 0){
            rightLiftCIM.set(secondDriversController.getLeftTriggerAxis());
        }
    }
    
    public void liftUp(double lefCond, double rightCond, boolean reverse){
            if (reverse == false) {
                leftLiftCIM.set(lefCond);
                rightLiftCIM.set(rightCond);
            }

            if (reverse == true) {
                leftLiftCIM.set(-lefCond);
                rightLiftCIM.set(-rightCond);
            }
    }
}

