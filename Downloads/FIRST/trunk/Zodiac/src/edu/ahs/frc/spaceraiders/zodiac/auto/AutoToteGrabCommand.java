package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.Encoders;
import edu.ahs.frc.spaceraiders.zodiac.input.SensorEnum;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoToteGrabCommand extends Command {

	private double startPos;
	private Encoders encoder = Encoders.getInstance();
	private static final double MAX_AUTO_TRAVEL_DISTANCE = 10;//TODO get actual value for max
	private static final double TOLERANCE = 1;
	private static final double TOTE_GRAB_ANGLE = 4;//TODO get actual angle to grab tote at

	public AutoToteGrabCommand() {
		requires(Robot.lifterSys);
		requires(Robot.driveSys);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		startPos = ((getLeftEncoder() + getRightEncoder()) / 2);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// make sure hook is open to grab
		Robot.hookSys.extendHook();
		// lifter up to bin level
		if ((Robot.lifterSys.getCurrentAngle() >= TOTE_GRAB_ANGLE - TOLERANCE)
				|| (Robot.lifterSys.getCurrentAngle() <= TOTE_GRAB_ANGLE
						+ TOLERANCE)) {
			Robot.lifterSys.stopLifter();
		} else {
			Robot.lifterSys.moveLifter(0.5);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if ((getLeftEncoder() > MAX_AUTO_TRAVEL_DISTANCE + startPos)
				|| (getRightEncoder() > MAX_AUTO_TRAVEL_DISTANCE + startPos)) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.lifterSys.stopLifter();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	private double getLeftEncoder() {
		return encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER);
	}

	private double getRightEncoder() {
		return encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER);
	}
}
