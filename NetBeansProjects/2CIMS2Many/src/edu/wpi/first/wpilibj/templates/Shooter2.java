/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Josh
 */
public class Shooter2 {
    Victor pullback;
    CANJaguar rotation;
    AnalogChannel pot;
    PIDController pid;
    LimitSwitch latchSwitch, releaseSwitch;
    DigitalInput flag;
    boolean change;
    
    int shooterState; // 0 - go forward until latched, 1 - pulling backwards, 2 - ready to launch, 3 - launching
    static final int LATCHING = 0, PULLING = 1, LAUNCH_READY = 2, LAUNCHING = 3;
    long launchTime;
    
    public Shooter2(int pullbackChannel, int rotationChannel, int potChannel, int latchPort, int releasePort){
        try {
            flag = new DigitalInput(5);
            pullback = new Victor(pullbackChannel);
            rotation = new CANJaguar(rotationChannel);
            latchSwitch = new LimitSwitch(latchPort);
            releaseSwitch = new LimitSwitch(releasePort);
            pot = new AnalogChannel(1);
            shooterState = LATCHING;
            rotation.changeControlMode(CANJaguar.ControlMode.kPosition);
            rotation.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
                    
            rotation.configPotentiometerTurns(1);
            rotation.setPID(150.000, 0.090, 2.500);
            //rotation.setPID(150.000,0,2.500);
            rotation.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            //rotation.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            rotation.enableControl();
            rotation.setX(0.420);
            
            //rotation.configSoftPositionLimits(0.2, 0.7);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public void print(){
        
        try {
            System.out.println(rotation.getPosition());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public boolean setFlat(){
        moveToDeg(0.420);
        
        return false;
        
    }
    public void printOut(){
        try {
            System.out.println(rotation.getPosition());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public void setDown(){
        moveToDeg(90);
    }
    public double getTilt(){
        try {
            return rotation.getX();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    public void releaseControl(){
        try {
            rotation.changeControlMode(CANJaguar.ControlMode.kVoltage);
            rotation.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public void regainControl(){
        try {
            rotation.changeControlMode(CANJaguar.ControlMode.kPosition);
            rotation.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
                    
            rotation.configPotentiometerTurns(1);
            rotation.setPID(150.000, 0.090, 2.500);
            rotation.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            rotation.enableControl();
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public boolean pullback(){
        if(latchSwitch.isClosed()){
            pullback.set(-0.8);
            return true;
        }else{
            pullback.set(0.0);
            return false;
        }
    }
    
    public void moveToDeg(double deg){
        try {
            rotation.setX(deg);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public double getDegrees(){
        return pot.getVoltage()*60;
    }
    public boolean compare(double value, double compareTo){
        if(value > compareTo - constants.BUFFER_VALUE && value < compareTo + constants.BUFFER_VALUE){
            return true;
        }else{
            return false;
        }
    }

    void setPullback(double x) {
        pullback.set(x);
    }

    boolean latch() {
        if(releaseSwitch.isClosed()){
            pullback.set(0.4);
            return true;
        }else{
            pullback.set(0.0);
            change = releaseSwitch.isClosed();
            return false;
        }
    }
    void fire(){
            while(!hasReleaseChanged()){
                pullback.set(-0.6);
                System.out.println(releaseSwitch.isClosed());
                //return true;
            }
            pullback.set(0.0);
    }
    boolean hasReleaseChanged(){
        if(releaseSwitch.isClosed() == change){
            change = releaseSwitch.isClosed();
            return false;
        }else{
            change = releaseSwitch.isClosed();
            return true;
        }
        
        
    }
    void setTilt(double angle){
        try {
            regainControl();
            rotation.setX(angle);
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    boolean check(){
        if(flag.get()){
            try {
                rotation.setX(0.420);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            fire();
            
            return true;
        }else{
            return false;
        }
    }
}
