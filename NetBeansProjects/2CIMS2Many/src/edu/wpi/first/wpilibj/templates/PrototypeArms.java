/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Josh
 */
public class PrototypeArms {
    Victor control, rollers;
    CANJaguar rotate;

    PrototypeArms(int i, int b, int can) {
        try {
            control = new Victor(i);
            rollers = new Victor(b);
            rotate = new CANJaguar(5);
            /*rotate.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            rotate.setPID(140.000, 0.090, 1.000);
            rotate.changeControlMode(CANJaguar.ControlMode.kPosition);
            rotate.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            rotate.configPotentiometerTurns(1);
            rotate.enableControl();*/
            rotate.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
            rotate.setX(0.001);
            Timer.delay(0.001);
            rotate.setX(0.000);
            
            rotate.changeControlMode(CANJaguar.ControlMode.kPosition);
            rotate.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
                    
            rotate.configPotentiometerTurns(1);
            rotate.setPID(170.000, 0.090, 2.400); //P used to be 140
            rotate.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            rotate.enableControl(0.000);
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public void setAngle(double value){
        try {
            rotate.setX(value);
            //read();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        
    }
    public void setBottomRollers(double value){
        rollers.set(value);
    }
    public void read(){
        try {
            System.out.println(rotate.getPosition());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public void print(){
        try {
            System.out.println(rotate.getPosition());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
