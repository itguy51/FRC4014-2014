/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Josh
 */
public class constants {
    //Physical Definitions (Hardware!)
    static int DRIVE_WHEEL_DIAMETER = 8; //Wheel Diamter (IN INCHES)
    
    //Sensor Specs
    static int ENCODER_COUNTS_PER_REVOLUTION = 360; //Encoder counts/revolution
        
    //Configuration
    static int DRIVE_MOTOR_RAMPUP_VOLTS_SECOND = 24; //Ramp speed in volts/second
    static int DRIVE_MOTOR_CURRENT_CAP = 30; //Current cap to spin up another motor
    
    //Control Interface
    static int DRIVER_JOYSTICK_PORT = 1;
    static int OPERATOR_JOYSTICK_PORT = 2;
    
    //Motor Connections (CAN/Drive Base)
    static int LEFT_FRONT_JAGUAR = 1;
    static int LEFT_TOP_JAGUAR = 2;
    static int LEFT_BOTTOM_JAGUAR = 3;
    static int RIGHT_FRONT_JAGUAR = 4;
    static int RIGHT_TOP_JAGUAR = 5;
    static int RIGHT_BOTTOM_JAGUAR = 6;
    
    //Motor Connections (PWM)
    
    
    //Digital Input (Switches?)
    
    
    
    //Stall checking values
    static int STALL_RPM_LESS = 10; //If RPM is < this, check current
    static int STALL_CURRENT_GREATER = 35; //If current > this, motor may be stalled
}
