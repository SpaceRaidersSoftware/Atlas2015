package edu.ahs.frc.spaceraiders.zodiac.arm;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.HumanInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves lifter arm according to Joystick input
 * 
 * @author Arden
 *
 */
public class LifterManCommand extends Command {
	//moving, start time, and start angle are for unspooling detection
//	private long unspoolingStartTime; 
//	private double unspoolingStartAngle;
//	private static final double UNSPOOLING_TIME = 1000; //milliseconds
//	private boolean moving = false;
	// Tolerance is how far the arm has to move for the code to detect that it isn't unspooling
	// and how far the arm has to move to detect that it is not in the right spot	
	private static final double TOLERANCE = 1; 
	
	/**
	 * Constructor
	 */
	public LifterManCommand(){
		requires(Robot.lifterSys);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	/**
	 * Sets arm motor speed to joystick input
	 */
	protected void execute() {
//		if((getLifterJoystick() != 0) && (moving == false)){
//			unspoolingStartTime = System.currentTimeMillis();
//			unspoolingStartAngle = Robot.lifterSys.getCurrentAngle();
//			moving = true;
//		} else if ((getLifterJoystick() == 0) && (moving == true)){
//			moving = false;
//		} else{
//			System.out.println("[Lifter] (LifterManCommand) (execute()) Moving!");
//		}
		Robot.lifterSys.moveLifter(getLifterJoystick());
				
	}

	@Override
	/**
	 * Detects if the arm has hit an obstacle and is unspooling
	 */
	protected boolean isFinished() {
//		// same spot (unspooling)
//		if (((Robot.lifterSys.getCurrentAngle() >= unspoolingStartAngle - TOLERANCE)
//				|| (Robot.lifterSys.getCurrentHeight() <= unspoolingStartAngle + TOLERANCE))
//				&& (System.currentTimeMillis() >= unspoolingStartTime + UNSPOOLING_TIME)
//				&& (moving == true)) {
//			System.out.println("unspooling");
//			return true;
//		} else {
//			unspoolingStartTime = System.currentTimeMillis();
//			unspoolingStartAngle = Robot.lifterSys.getCurrentAngle();
//		}
		return false;
	}

	@Override
	/**
	 * stops arm motor
	 */
	protected void end() {
		Robot.lifterSys.stopLifter();
	}

	@Override
	/**
	 * stops arm motor
	 */
	protected void interrupted() {
		Robot.lifterSys.stopLifter();
	}

	/**
	 * gets joystick input
	 * @return joystick input
	 */
	private double getLifterJoystick(){
		return HumanInput.getXboxAxis(HumanInput.xboxController, HumanInput.XBOX_LEFT_STICK_Y_AXIS);
	}
}
