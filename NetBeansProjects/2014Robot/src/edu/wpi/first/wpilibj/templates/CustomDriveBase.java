/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Josh
 */
public class CustomDriveBase extends RobotDrive{
    DriveGearbox leftGearbox, rightGearbox;
    public CustomDriveBase(DriveGearbox leftMotor, DriveGearbox rightMotor) {
        super(leftMotor, rightMotor);
        leftGearbox = leftMotor;
        rightGearbox = rightMotor;
        
    }
    public void goInches(int left, int right){
        leftGearbox.moveDistance(left);
        rightGearbox.moveDistance(right);
    }
    
}
