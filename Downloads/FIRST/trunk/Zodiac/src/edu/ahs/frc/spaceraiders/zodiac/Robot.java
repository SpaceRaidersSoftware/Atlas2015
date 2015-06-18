package edu.ahs.frc.spaceraiders.zodiac;

import edu.ahs.frc.spaceraiders.zodiac.arm.LifterSubsystem;
import edu.ahs.frc.spaceraiders.zodiac.auto.AutoChooser;
import edu.ahs.frc.spaceraiders.zodiac.drive.DriveSubsystem;
import edu.ahs.frc.spaceraiders.zodiac.grabber.GrabberSubsystem;
import edu.ahs.frc.spaceraiders.zodiac.hook.HookSubsystem;
import edu.ahs.frc.spaceraiders.zodiac.input.Encoders;
import edu.ahs.frc.spaceraiders.zodiac.input.HumanInput;
import edu.ahs.frc.spaceraiders.zodiac.input.Lidars;
import edu.ahs.frc.spaceraiders.zodiac.input.SensorEnum;
import edu.ahs.frc.spaceraiders.zodiac.input.SerialSubsystem;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
    
	public static SerialSubsystem sensorSys;
	public static DriveSubsystem driveSys;
    public static LifterSubsystem lifterSys;
    public static GrabberSubsystem grabberSys;
	public static HookSubsystem hookSys; 
	public static AutoChooser autoChoice;
	public Command autoCommand;
	
	
    /**
     * This method is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	System.out.println("Init");
        sensorSys=new SerialSubsystem();
        sensorSys.initDefaultCommand();
        
        driveSys = new DriveSubsystem();
        driveSys.initDefaultCommand();
        driveSys.registerButtons();
        
        lifterSys = new LifterSubsystem();
        lifterSys.initDefaultCommand();
        lifterSys.registerButtons();
        
        grabberSys = new GrabberSubsystem();
        grabberSys.registerButtons();
        
        hookSys = new HookSubsystem();
        hookSys.registerButtons();
        
        autoChoice= new AutoChooser();
        
        PrintDrawer.printRobotName();
        System.out.println("subsystem sucessfully initialized");
    }
    
    /**
     * This method is called at the start of autonomous only once
     */
    public void autonomousInit() {
    	autoCommand=autoChoice.getAutoChoice();
    	Encoders.getInstance().reset(SensorEnum.DRIVE_LEFT_ENCODER); //temp fix
    	Encoders.getInstance().reset(SensorEnum.DRIVE_RIGHT_ENCODER); //temp fix
    	Scheduler.getInstance().add(autoCommand);
    }
    
    /**
     * This method is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
    }
    
    /**
     * This method is called at the start of teleop only once
     */
    public void teleopInit() {
    	Scheduler.getInstance().add(new RobotInit());

    	System.out.println("Teleop Init");
    	
    	if (autoCommand!=null){
    		autoCommand.cancel();
    	}
    }
    
    /**
     * This method is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
        Scheduler.getInstance().run();
    }
    
    /**
     * This method is called once at the beginning of test mode
     */
    public void testInit() {
    	
    }
    
    /**
     * This method is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();

    }
    
    /**
     * This method is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {
        Scheduler.getInstance().removeAll();
    }
    
    /**
     * This method is called continuously during the disabled period
     */
    public void disabledPeriodic() {
    }
}
