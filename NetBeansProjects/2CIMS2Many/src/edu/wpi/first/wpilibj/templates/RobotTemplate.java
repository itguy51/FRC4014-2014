/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    CustomDriveBase drive;
    DriveGearbox leftDrive, rightDrive;
    Joystick driveStick, operateStick;
    Shooter2 shooter;
    PrototypeArms arms;
    SecretPlan sp;
    boolean hasRun = false;
    boolean hasAble = true;
    boolean init = false;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() { 
        rightDrive = new DriveGearbox(constants.RIGHT_BOTTOM_JAGUAR, constants.RIGHT_TOP_JAGUAR);
        leftDrive = new DriveGearbox(constants.LEFT_BOTTOM_JAGUAR, constants.LEFT_TOP_JAGUAR);
        drive = new CustomDriveBase(leftDrive, rightDrive);
        driveStick = new Joystick(constants.DRIVER_JOYSTICK_PORT);
        operateStick = new Joystick(constants.OPERATOR_JOYSTICK_PORT);
        //sp = new SecretPlan(constants.SECRET_PLAN_LEFT, constants.SECRET_PLAN_RIGHT, constants.SECRET_PLAN_ROLLER);
        shooter = new Shooter2(constants.PULLBACK_MOTOR, constants.ROTATION_MOTOR, constants.POT_CHANNEL, constants.LATCH_BLOCKED, constants.FIRE_BLOCKED);
        arms = new PrototypeArms(constants.PROTOTYPE_ARMS_CONTROL, constants.PROTOTYPE_ARMS_ROLLER, constants.PROTOTYPE_ARMS_JAGUAR);
        drive.setExpiration(50000);
    }

    /**
     * This function is called periodically during autonomous
     */
    boolean auto_init = true;
     boolean auto_init2 = true;
    public void autonomousPeriodic() {
       
        /*
            //autonomous 1: drive forward
        if(auto_init){
            drive.arcadeDrive(-1.0, 0.0);
            Timer.delay(2);
            drive.arcadeDrive(0.0, 0.0);
            auto_init = false;
        }
        */
        //Comment everything after this out if you don't want to fire a ball
        
        //autonomous 2: drive forward, fire ball
         if(auto_init)
        {
            shooter.setFireRoutine();//start cocking back
            auto_init = false;
        }
        
        shooter.shooterPeriodic();
        
        if(!shooter.fireRountine && auto_init2) //Ready to fire
        {
            //drive
            drive.arcadeDrive(-1.0, 0.0);
            Timer.delay(2);
            drive.arcadeDrive(0.0, 0.0);
            auto_init2 = false;
            
            
            
            shooter.setTilt(0.350);
            Timer.delay(0.7);
            //just so dangerous
             shooter.setPullback(-0.8);
            Timer.delay(1.5);
            shooter.setPullback(0.0);
        }
        
        
            //while(true);
        
        
    }
    
    public void autonomousInit()
    {
        auto_init = true;
        auto_init2 = true;
    }
    
    public void teleopInit()
    {
        shooter.setFireRoutine(); //cock back shooter
    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopPeriodic() {
        /*
        if(!init){
          //  shooter.start();
            shooter.shooterPeriodic();
            init = true;
        }
        * */
        
        //just in case
        auto_init = true;
        auto_init2 = true;
        
        //arms.print();
        //drive.arcadeDrive(driveStick, true);
        drive.arcadeDrive(driveStick, true);
        //drive.tankDrive(driveStick.getRawAxis(2)*Math.abs(driveStick.getRawAxis(2)), driveStick.getRawAxis(5)*Math.abs(driveStick.getRawAxis(5)));
        /*shooter.setPullback(driveStick.getX());
        //shooter.update();
        if(driveStick.getRawButton(1)){
            arms.setAngle((float)0.5);
        }else if(driveStick.getRawButton(2)){
            arms.setAngle((float)-0.5);
        }else{
            arms.setAngle((float)0);
            
        }
        //arms.read();
        if(driveStick.getRawButton(3)){
            arms.setBottomRollers((float)0.8);
        }else if(driveStick.getRawButton(4)){
            arms.setBottomRollers((float)-0.8);
        }else{
            arms.setBottomRollers((float)0);
            
        }
        if(driveStick.getRawButton(5) && !hasRun){
            shooter.setPullback(-1.0);
            Timer.delay(1.6);
            while(shooter.pullback());
            hasRun = true;
        }
        if(hasRun && driveStick.getRawButton(6)){
            hasRun = false;
            shooter.setPullback(-0.4);
            Timer.delay(0.7);
            shooter.setPullback(1.0);
            Timer.delay(1.9);
            while(shooter.latch());
            
            
        }
        if(hasAble && driveStick.getRawButton(10)){
            hasAble = false;
            shooter.setTilt(0.420);
        }
        if(driveStick.getRawButton(8)){
            System.out.println("Fire");
            shooter.fire();
            System.out.println("Fire End, Go latch");
            shooter.setPullback(1.0);
            Timer.delay(1.5);
            System.out.println("Latching");
            while(shooter.latch());
            System.out.println("Latching End, pull back");
            shooter.setPullback(-1.0);
            Timer.delay(2);
            System.out.println("Arming");
            while(shooter.pullback());
            System.out.println("Arming end");
            
        }
        
        //System.out.println(shooter.latchSwitch.isClosed() + " - " + shooter.releaseSwitch.isClosed());
        */
        //Shooter Stuff Begin
        if(driveStick.getRawButton(1)){
            /*System.out.println("Fire");
            shooter.fire();
            System.out.println("Fire End, Go latch");
            shooter.setPullback(1.0);
            Timer.delay(1.5);
            System.out.println("Latching");
            while(shooter.latch());
            System.out.println("Latching End, pull back");
            shooter.setPullback(-1.0);
            Timer.delay(2);
            System.out.println("Arming");
            while(shooter.pullback());
            System.out.println("Arming end");*/
            shooter.setFireRoutine();
            
        }
        if(operateStick.getRawButton(9)){
            shooter.setTilt(0.420); //Slightly Up Flat
        }
        if(operateStick.getRawButton(8)){
            shooter.setTilt(0.350); //Shooting Pos.
        }
        if(operateStick.getRawButton(11)){
            shooter.setTilt(shooter.getTilt() + 0.01); //Go Up a nudge
        }
        if(operateStick.getRawButton(12)){
            shooter.setTilt(shooter.getTilt() - 0.01); //Go Down a nudge
        }
        if(operateStick.getRawButton(2)){
            shooter.setTilt(0.620); //Down
        }
        /*if(operateStick.getRawButton(7)){
            shooter.setTilt(0.300); //Up
            * }
        */
        //Shooter Stuff End
        //Launcher Begin
        //shooter.printOut();
        if(operateStick.getRawButton(1)){
            shooter.setFireRoutine();
            /*
            shooter.setPullback(1.0);
            Timer.delay(1.5);
            System.out.println("Latching");
            while(shooter.latch());
            shooter.setPullback(0.4);
            Timer.delay(0.150);
            shooter.setPullback(0.000);
            System.out.println("Latching End, pull back");
            shooter.setPullback(-1.0);
            Timer.delay(2);
            System.out.println("Arming");
            while(shooter.pullback());
            System.out.println("Arming end");
            * */
            
        }
        shooter.shooterPeriodic();//IMPORTANT!!!
        
        
    
        if(operateStick.getRawButton(6)){
            shooter.setPullback(0.6);
        }else if(operateStick.getRawButton(4)){
            shooter.setPullback(-1.0);
        }else{
            if(!shooter.fireRountine)
                shooter.setPullback(0.0);
        }
        //Launcher End
        //Arms Begin
        
        if(operateStick.getRawButton(3)){
            shooter.regainControl();
            arms.setAngle(0.270); //Middle
        }
        if(operateStick.getRawButton(5)){
            shooter.releaseControl();
            arms.setAngle(0.740); //Up
            
        }
/*        if(operateStick.getRawButton(2)){
            shooter.regainControl();
            arms.setAngle(0.050); //Down
        }*/
        if(operateStick.getY() > 0.2 || operateStick.getY() < -0.2){
            arms.setBottomRollers(operateStick.getY());
        }else{
            arms.setBottomRollers(0.0);
        }
        if(operateStick.getRawButton(7)){
            shooter.setTilt(shooter.getTilt() + 0.01); //Go Up a nudge
        }
        if(operateStick.getRawButton(10)){
            shooter.setTilt(shooter.getTilt() - 0.01); //Go Down a nudge
        }
       //Arms End
        
       /*if(operateStick.getRawButton(8)){
           shooter.setPullback(-1.0); //Button 8 
       }else{
           shooter.setPullback(0.0);
       }*/
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
