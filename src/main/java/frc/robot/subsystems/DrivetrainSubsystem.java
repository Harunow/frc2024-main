package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;

public class DrivetrainSubsystem {
    public static DifferentialDrive driveTrain;
    private DifferentialDrive frontDrive = Constants.FRONT_DRIVE;
    private DifferentialDrive rearDrive = Constants.REAR_DRIVE;

    public void driveHalt(){
        driveTrain.tankDrive(0, 0);
    }
    
    public void arcadeDrv(double spd, double rotation, double drivethrottle, DifferentialDrive drive){
        spd *= drivethrottle;   
        rotation *= drivethrottle;
        drive.arcadeDrive(spd, rotation, true);
    }

    public void driveBoth(double spd, double rotation, double drivethrottle){
        arcadeDrv(spd,rotation,drivethrottle,frontDrive);
        arcadeDrv(spd,rotation,drivethrottle,rearDrive);
    }

    public static void executeTankDrive(double leftSpeed, double rightSpeed, DifferentialDrive drive){
        drive.tankDrive(leftSpeed,rightSpeed);
    }
}