/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Josh
 */
public class Shooter2{

    final Victor pullback;
    CANJaguar rotation;
    AnalogChannel pot;
  //  PIDController pid;
    LimitSwitch latchSwitch, releaseSwitch;
    DigitalInput flag;
    boolean change;
    boolean fireRountine = true, latched = false;
    
    int shooterState; // 0 - go forward until latched, 1 - pulling backwards, 2 - ready to launch, 3 - launching
    static final int LATCHING = 0, PULLING = 1, LAUNCH_READY = 2, LAUNCHING = 3;
    long launchTime;
    //questions for josh
    //is pot chsnnel 1?
    public Shooter2(int pullbackChannel, int rotationChannel, int potChannel, int latchPort, int releasePort){
        pullback = new Victor(pullbackChannel);
        try {
            flag = new DigitalInput(5);
            
            rotation = new CANJaguar(rotationChannel);
            latchSwitch = new LimitSwitch(latchPort);
            releaseSwitch = new LimitSwitch(releasePort);
           // pot = new AnalogChannel(1);
            shooterState = LATCHING;
            rotation.changeControlMode(CANJaguar.ControlMode.kPosition);
            rotation.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
                    
            rotation.configPotentiometerTurns(1);
            rotation.setPID(170.000, 0.210, 0.00); //correct
            //rotation.setPID(150.000,0,2.500);
            rotation.configNeutralMode(CANJaguar.NeutralMode.kBrake);
           // pid = new PIDController(150.000/constants.NEW_PID_CONST, 0/constants.NEW_PID_CONST, 2.5/constants.NEW_PID_CONST, this, )
            //rotation.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            rotation.enableControl();
            //rotation.setX(0.420);
            
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
        
        
        //System.out.println("pot volts: " + pot.getVoltage());
    }
    
    
    
    public void pullBack()
    {
            setPullback(1.0);
            Timer.delay(1.5);
            System.out.println("Latching");
            while(latch());
            setPullback(0.4);
            Timer.delay(0.150);
            setPullback(0.000);
            System.out.println("Latching End, pull back");
            setPullback(-1.0);
            Timer.delay(2);
            System.out.println("Arming");
            while(pullback());
            System.out.println("Arming end");
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
        
        //System.out.println("pot volts: " + pot.getVoltage());
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
        
       // return pid.getSetpoint();
    }
    public void releaseControl(){
        
        //if(pid.isEnable())
        //    pid.disable();
        try {
            rotation.disableControl();
            //rotation.changeControlMode(CANJaguar.ControlMode.kVoltage);
            rotation.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public void regainControl(){
      //  if(!pid.isEnable())
       //     pid.enable();
        try {
            //rotation.changeControlMode(CANJaguar.ControlMode.kPosition);
            //rotation.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
                    
            //rotation.configPotentiometerTurns(1);
            //rotation.setPID(150.000, 0.090, 2.500);
            
            rotation.enableControl();
            rotation.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    public boolean pullback(){
      //  synchronized(pullback)
        {
        if(latchSwitch.isClosed()){
            pullback.set(-0.9);
            return true;
        }else{
            pullback.set(0.0);
            return false;
        }
        }
    }
    
    public void moveToDeg(double deg){
        try {
            rotation.setX(deg);
            //pid.setSetpoint(deg);
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
        //synchronized(pullback)
        {
            pullback.set(x);
        }
    }

    boolean latch() {
       // synchronized(pullback)
        {
        if(releaseSwitch.isClosed()){
            pullback.set(0.8);
            return true;
        }else{
            pullback.set(0.0);
            change = releaseSwitch.isClosed();
            return false;
        }
        }
    }
    void setFireRoutine(){
        fireRountine = true;
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
        
       // regainControl();
        //id.setSetpoint(angle);
        
        try {
            regainControl();
            rotation.setX(angle);
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
           
        }
    }

/*
    public void pidWrite(double d) {
        try {
            rotation.setX(d);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    * */
    boolean check(){
        if(flag.get()){
            try {
                rotation.setX(0.420);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            //fire();
            
            return true;
        }else{
            return false;
        }
    }
    
   /*  public void start(){
        Thread p = new Thread(this, "thread");
        p.start();
    }
    * */
/*
    public double pidGet() {
        try {
           return rotation.getPosition();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    * */
     
     public void shooterPeriodic()
    {
       //  System.out.println("peridic");
        if(fireRountine)
        {
         //    System.out.println("firingInProgress");
            if(!latched)
            {
                if(!latch())
                {
                    
                    latched = true;
                    System.out.println("latched");
                    setPullback(0.0);
                }
                else
                    setPullback(1.0);
            }
            else
            {
                if(!pullback())
                {
                    latched = false;
                    fireRountine = false;
                     System.out.println("pulledback");
                     setPullback(0.0);
                }
                else
                    setPullback(-1.0);
            }
        }
        
        
            
    }
     /*
     public void run(){
         boolean inited = false;
        while(true)
        {
            if(!fireRountine)
                continue;
            

                if(inited)
                {
                    System.out.println("Fire");
                    fire();

                }
                inited = true;/*
                System.out.println("Fire End, Go latch");
                setPullback(1.0);
                Timer.delay(1.5);
                //System.out.println("Latching");
                //while(latch());
                
                System.out.println("Latching End, pull back");
                setPullback(-1.0);
                Timer.delay(2);
                
                System.out.println("Arming");
                while(pullback());
                System.out.println("Arming end");
                fireRountine = false;
                
         
        }
    }
    * */
}
    
   
