package edu.ahs.frc.spaceraiders.zodiac.hook;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Extends the hook
 * 
 *@author Arden
 *@author Fahim
 *@author Josh
 */
public class HookExtendCommand extends Command {
	private long hookStartTime;
	
	/**
	 * Constructor
	 */
	public HookExtendCommand() {
        requires(Robot.hookSys);
    }

	/**
	 * gets the current system time
	 */
	protected void initialize() {
        hookStartTime = System.currentTimeMillis();
    }

	/**
	 * extends hook
	 */
	protected void execute() {
    	Robot.hookSys.extendHook();
    }

	/**
	 * finishes after the hook move time (seconds)
	 */
	protected boolean isFinished() {
    	if(System.currentTimeMillis() >= hookStartTime + HookSubsystem.HOOK_MOVE_TIME){
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
