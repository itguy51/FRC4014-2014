/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Josh
 */
public class DriveGearbox implements SpeedController{
    double currentSpeed = 0.0;
    CANJaguar top, bottom;
    
    public DriveGearbox(int bottomCh, int topCh){
        try {
            top = new CANJaguar(topCh);
            bottom = new CANJaguar(bottomCh);
            top.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            bottom.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            top.setVoltageRampRate(24);
            bottom.setVoltageRampRate(12);
            top.configEncoderCodesPerRev(360);
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public double get() {
        return currentSpeed;
    }

    public void set(double d, byte b) {
        try {
            currentSpeed = d;
            bottom.setX(d);
            top.setX(d);
            if(d == 0.0){
                top.configNeutralMode(CANJaguar.NeutralMode.kBrake);
                bottom.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            }
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public void set(double d) {
        set(d, (byte)0);
    }

    public void disable() {
        set(0.0);
    }

    public void pidWrite(double d) {
    }
    
}
