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
    float countsPerInch = (float) (1/((constants.DRIVE_WHEEL_DIAMETER*Math.PI)/360));
    public DriveGearbox(int front, int bottom, int top){
        try {
            front_jag = new CANJaguar(front);
            bottom_jag = new CANJaguar(bottom);
            top_jag = new CANJaguar(top);
            front_jag.configEncoderCodesPerRev(360);
            front_jag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
            front_jag.setVoltageRampRate(constants.DRIVE_MOTOR_RAMPUP_VOLTS_SECOND);
            bottom_jag.setVoltageRampRate(constants.DRIVE_MOTOR_RAMPUP_VOLTS_SECOND);
            top_jag.setVoltageRampRate(constants.DRIVE_MOTOR_RAMPUP_VOLTS_SECOND);
        } catch (CANTimeoutException ex) {
            //Hopefully we'll never hit here.
            ex.printStackTrace();
        }
    }
    void moveDistance(int inches){
        float counts = countsPerInch*inches;
        try {
            front_jag.changeControlMode(CANJaguar.ControlMode.kPosition);
            front_jag.setX(front_jag.getPosition() + Math.floor(counts));
            front_jag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    void startMotor2() throws CANTimeoutException{
        if(front_jag.getOutputCurrent() <= constants.DRIVE_MOTOR_CURRENT_CAP){
            bottom_jag.setX(front_jag.getX());
        }
    }
    void startMotor3() throws CANTimeoutException{
        if(bottom_jag.getOutputCurrent() <= constants.DRIVE_MOTOR_CURRENT_CAP && front_jag.getOutputCurrent() <= constants.DRIVE_MOTOR_CURRENT_CAP){
            top_jag.setX(front_jag.getX());
        }
    }
    void checkStall() throws CANTimeoutException{
        if(front_jag.getSpeed() < constants.STALL_RPM_LESS){
            //We're going less than 10 RPM here. Something may be wrong.
            if(front_jag.getOutputCurrent() > constants.STALL_CURRENT_GREATER){
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
