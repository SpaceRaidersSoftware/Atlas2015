package edu.ahs.frc.spaceraiders.zodiac.hook;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.wpi.first.wpilibj.command.Command;


/**
 * Retracts the hook
 * 
 *@author Arden
 *@author Fahim
 *@author Josh
 */
public class HookRetractCommand extends Command {
	private long hookStartTime;
	
	/**
	 * constructor
	 */
	public HookRetractCommand() {
		requires(Robot.hookSys);
	}

	/**
	 * gets the current system time
	 */
	protected void initialize() {
		hookStartTime = System.currentTimeMillis();
	}

	/**
	 * retracts hook
	 */
	protected void execute() {
		Robot.hookSys.retractHook();
	}

	/**
	 * stops after hook move time (seconds)
	 */
	protected boolean isFinished() {
		if (System.currentTimeMillis() >= hookStartTime + HookSubsystem.HOOK_MOVE_TIME){
			return true;
		}
		return false;
	}

	/**
	 * stops hook
	 */
	protected void end() {
		Robot.hookSys.stopHook();
	}

	/**
	 * stops hook
	 */
	protected void interrupted() {
		Robot.hookSys.stopHook();
	}
}
