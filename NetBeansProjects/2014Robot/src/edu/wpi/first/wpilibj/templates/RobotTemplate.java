/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    CustomDriveBase drive;
    DriveGearbox leftDrive, rightDrive;
    Joystick driveStick, operateStick;
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        rightDrive = new DriveGearbox(constants.RIGHT_FRONT_JAGUAR, constants.RIGHT_BOTTOM_JAGUAR, constants.RIGHT_TOP_JAGUAR);
        leftDrive = new DriveGearbox(constants.LEFT_FRONT_JAGUAR, constants.LEFT_BOTTOM_JAGUAR, constants.LEFT_TOP_JAGUAR);
        drive = new CustomDriveBase(leftDrive, rightDrive);
        driveStick = new Joystick(constants.DRIVER_JOYSTICK_PORT);
        operateStick = new Joystick(constants.OPERATOR_JOYSTICK_PORT);
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        drive.arcadeDrive(driveStick, true);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
