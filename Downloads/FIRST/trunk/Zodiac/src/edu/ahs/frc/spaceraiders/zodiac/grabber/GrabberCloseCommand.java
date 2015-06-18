package edu.ahs.frc.spaceraiders.zodiac.grabber;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Closes grabber
 * 
 * @author Fahim
 *
 */
public class GrabberCloseCommand extends Command {
	private long start;
	
	/**
	 * Constructor
	 */
	public GrabberCloseCommand(){
		requires(Robot.grabberSys);
	}

	@Override
	/**
	 * gets the current system time
	 */
	protected void initialize() {
		start = System.currentTimeMillis();
	}

	@Override
	/**
	 * closes grabber
	 */
	protected void execute() {
		Robot.grabberSys.closeGrabber();
		
	}

	@Override
	/**
	 * finishes after one second
	 */
	protected boolean isFinished() {
		if(System.currentTimeMillis() >= (start + GrabberSubsystem.CLAW_MOVE_TIME)){
			return true;
		} else{
			return false;
		}
	}

	@Override
	/**
	 * stops grabber
	 */
	protected void end() {
		Robot.grabberSys.stopGrabber();
	}

	@Override
	/**
	 * stops grabber
	 */
	protected void interrupted() {
		Robot.grabberSys.stopGrabber();
	}

}
