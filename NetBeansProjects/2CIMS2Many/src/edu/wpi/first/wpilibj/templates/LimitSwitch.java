/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author josh
 */
public class LimitSwitch {

    protected DigitalInput di;

    public LimitSwitch(int portNumber) {
        di = new DigitalInput(portNumber);
    }

    public boolean isOpen(){
        if (di.get() == false) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isClosed(){
        return (!isOpen());
    }

}
