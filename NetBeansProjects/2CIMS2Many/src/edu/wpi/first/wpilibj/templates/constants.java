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
    static int DRIVE_WHEEL_DIAMETER = 6; //Wheel Diamter (IN INCHES)
    
    //Sensor Specs
    static int ENCODER_COUNTS_PER_REVOLUTION = 360; //Encoder counts/revolution
        
    //Configuration
    static int DRIVE_MOTOR_RAMPUP_VOLTS_SECOND = 24; //Ramp speed in volts/second
    static int DRIVE_MOTOR_CURRENT_CAP = 150; //Current cap to spin up another motor
    
    //Control Interface
    static int DRIVER_JOYSTICK_PORT = 1;
    static int OPERATOR_JOYSTICK_PORT = 2;
    
    //Motor Connections (CAN/Drive Base)
    static int LEFT_TOP_JAGUAR = 4;
    static int LEFT_BOTTOM_JAGUAR = 3;
    static int RIGHT_TOP_JAGUAR = 1;
    static int RIGHT_BOTTOM_JAGUAR = 2;
    
    
    
    static double NEW_PID_CONST =   150;
    //Motor Connections (PWM)
    
    
    //Digital Input (Switches?)
    
    
    
    //Stall checking values
    static int STALL_RPM_LESS = 10; //If RPM is < this, check current (Below)
    static int STALL_CURRENT_GREATER = 35; //If current > this, motor may be stalled
    
    //PID Stuff
    static double SHOOTER_KP_VALUE = 0.005;
    
    //Launcher Stuff
    static long LAUNCH_TIME = 100;
    static double LATCH_POWER = 0.5;
    static double PULL_POWER = 1.0;
    static int LATCH_BLOCKED = 1;
    static int LATCH_CLEAR = 2;
    static int FIRE_BLOCKED = 3;
    static int FIRE_CLEAR = 4;
    static int POT_CHANNEL = 8;
    static int PULLBACK_MOTOR = 2;
    static int ROTATION_MOTOR = 6;
    
    
    //Arm Stuff
    static int PROTOTYPE_ARMS_CONTROL = 8;
    static int PROTOTYPE_ARMS_ROLLER = 1;
    static int PROTOTYPE_ARMS_JAGUAR = 5;
    
    static float BUFFER_VALUE = 8;
}
