package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoHookCommand extends Command {

	private Timer time = new Timer();
	
    public AutoHookCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.hookSys);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(time.get()<2){
    		Robot.hookSys.extendHook();
    	}
    	else{
    		Robot.hookSys.retractHook();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(time.get()>4){
    		return true;
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	time.stop();
    	time.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	time.stop();
    	time.reset();
    }
}
