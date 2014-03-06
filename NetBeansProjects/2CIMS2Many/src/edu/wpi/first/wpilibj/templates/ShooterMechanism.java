/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Josh
 */
public class ShooterMechanism implements PIDSource {
    Victor pullback, rotation;
    AnalogChannel pot;
    PIDController pid;
    LimitSwitch latchSwitch, releaseSwitch;
    
    int shooterState; // 0 - go forward until latched, 1 - pulling backwards, 2 - ready to launch, 3 - launching
    static final int LATCHING = 0, PULLING = 1, LAUNCH_READY = 2, LAUNCHING = 3;
    long launchTime;
    
    public ShooterMechanism(int pullbackChannel, int rotationChannel, int potChannel, int latchPort, int releasePort){
        pullback = new Victor(pullbackChannel);
        rotation = new Victor(rotationChannel);
        latchSwitch = new LimitSwitch(latchPort);
        releaseSwitch = new LimitSwitch(releasePort);
        pot = new AnalogChannel(1);
        pid = new PIDController(constants.SHOOTER_KP_VALUE, 0, 0, this, rotation);
        pid.setInputRange(0, 300);
        pid.setOutputRange(-0.5, 0.5);
        pid.enable();
        shooterState = LATCHING;
    }

    public double pidGet() {
        double voltage = pot.getVoltage();
        if(voltage < 0) {
            voltage = 0;
        }
        
        return voltage*60;
    }
    
    public void launch()
    {
        if(shooterState == LAUNCH_READY)
        {
            shooterState = LAUNCHING;
            launchTime = System.currentTimeMillis();
        }
    }
    
    public void update()
    {
        switch(shooterState)
        {
            case LATCHING:
                if(latchSwitch.isClosed())
                {
                    //it has latched
                    pullback.set(0);
                    shooterState = PULLING;
                }
                else
                {
                    pullback.set(-constants.LATCH_POWER);
                }
                break;
            case PULLING:
                if(releaseSwitch.isClosed())
                {
                    //it has reached the point where it is ready
                    pullback.set(0);
                    shooterState = LAUNCH_READY;
                }
                else
                {
                    pullback.set(constants.PULL_POWER);
                }
                break;
            case LAUNCH_READY:
                break;
            case LAUNCHING:
                if(System.currentTimeMillis() - launchTime > constants.LAUNCH_TIME)
                {
                    shooterState = LATCHING;
                    pullback.set(0);
                }
                else
                {
                    pullback.set(constants.PULL_POWER);
                }
                break;
        }
    }
    
}
