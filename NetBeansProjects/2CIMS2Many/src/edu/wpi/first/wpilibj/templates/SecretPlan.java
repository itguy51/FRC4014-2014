/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Josh
 */
public class SecretPlan {
    Victor left, right, roller;
    public SecretPlan(int leftMotor, int rightMotor, int rollerMotor){
        left = new Victor(leftMotor);
        right = new Victor(rightMotor);
        roller = new Victor(rollerMotor);
    }
    public void rotate(double speed){
        left.set(speed);
        right.set(-speed);
    }
    public void setRoller(double speed){
        roller.set(speed);
    }
}
