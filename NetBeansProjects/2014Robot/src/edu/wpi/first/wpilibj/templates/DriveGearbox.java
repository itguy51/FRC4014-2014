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
    CANJaguar front_jag, bottom_jag, top_jag;
    int max_second_spinup_current = 30;
    int rampRate = 24; // 0.5s spinup
    public DriveGearbox(int front, int bottom, int top){
        try {
            front_jag = new CANJaguar(front);
            bottom_jag = new CANJaguar(bottom);
            top_jag = new CANJaguar(top);
            front_jag.configEncoderCodesPerRev(360);
            front_jag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
            front_jag.setVoltageRampRate(rampRate);
            bottom_jag.setVoltageRampRate(rampRate);
            top_jag.setVoltageRampRate(rampRate);
        } catch (CANTimeoutException ex) {
            //Hopefully we'll never hit here.
            ex.printStackTrace();
        }
    }
    void moveDistance(int inches){
        float counts = (float) (inches*14.33121);
        try {
            front_jag.changeControlMode(CANJaguar.ControlMode.kPosition);
            front_jag.setX(front_jag.getPosition() + Math.floor(counts));
            front_jag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    void startMotor2() throws CANTimeoutException{
        if(front_jag.getOutputCurrent() <= max_second_spinup_current){
            bottom_jag.setX(front_jag.getX());
        }
    }
    void startMotor3() throws CANTimeoutException{
        if(bottom_jag.getOutputCurrent() <= max_second_spinup_current && front_jag.getOutputCurrent() <= max_second_spinup_current){
            top_jag.setX(front_jag.getX());
        }
    }
    void checkStall() throws CANTimeoutException{
        if(front_jag.getSpeed() < 10){
            //We're going less than 10 RPM here. Something may be wrong.
            if(front_jag.getOutputCurrent() > 25){
                //We're shoving TWENTY-FIVE AMPS on this controller.
                //This generally would indicate a stall fault.
                System.err.println("ERR: STALL_FAULT");
                System.err.println("> Resolve issue and return control to < 0.1 to reset.");
            }
        }
    }
    void dropAll(){
        try {
            front_jag.disableControl();
            top_jag.disableControl();
            bottom_jag.disableControl();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public double get() {
        try {
            return front_jag.getX();
        } catch (CANTimeoutException ex) {
            return 0.0;
            //ex.printStackTrace();
        }
    }

    public void set(double d, byte b) {
        try {
            front_jag.setX(d);
            startMotor2();
            startMotor3();
            //checkStall();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public void disable() {
     dropAll();   
    }

    public void pidWrite(double d) {
    }

    public void set(double d) {
        set(d, (byte)0);
    }
}
