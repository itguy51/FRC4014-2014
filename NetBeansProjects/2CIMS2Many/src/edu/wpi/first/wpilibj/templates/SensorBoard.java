/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;

/**
 *
 * @author Josh
 */
public class SensorBoard {
    I2C inter;
    int addr = 0x53;
    private byte[] buffer = {0, 0, 0, 0, 0, 0, 0};
    private byte[] data = {0, 0, 0, 0, 0, 0, 0};
    public SensorBoard(){
        inter = new I2C(DigitalModule.getInstance(DigitalModule.getDefaultDigitalModule()), addr);
        inter.setCompatabilityMode(true);

    }
    public void transact(){
        //inter.write(1, 0);
        if(inter.transaction(data, 0, buffer, 7) == false){
            System.out.println(buffer);
        }else{
            System.err.println("No data!");
        }
        
    }
    
}
