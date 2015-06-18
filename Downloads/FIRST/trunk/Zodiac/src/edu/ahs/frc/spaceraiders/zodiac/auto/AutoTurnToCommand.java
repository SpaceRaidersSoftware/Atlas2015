package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.Encoders;
import edu.ahs.frc.spaceraiders.zodiac.input.NineDegreesOfFreedom;
import edu.ahs.frc.spaceraiders.zodiac.input.SensorEnum;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Gyro;

/**
 *
 */
public class AutoTurnToCommand extends Command {
	private double ToTurnTo=0;
	private static double current=0;
	private final double SPEED = 0.5;
	private final double ROBOT_RADIUS= 36;//in inches 
	private boolean stop = false;
	private AutoTurnCommand auto;

	/**
	 * 
	 * @param turn
	 * 		 to turn (counterclockwise) in degrees
	 * 
	 */
	public AutoTurnToCommand(double turn) {
		// Use requires() here to declare subsystem dependencies
		ToTurnTo=turn;
//		this.turnDistance = Math.toRadians(turn) * ROBOT_RADIUS
	}

	// Called just before this Command runs the first time
	protected void initialize() {

		auto = new AutoTurnCommand(getHeading(current,ToTurnTo));
		

	}
	public double getHeading(double current, double ToTurnTo) {
		double difference = ToTurnTo - current;
		if (current <= 180) {
			if (Math.abs(difference) <= 180) {
				return difference;
			} else {
				return difference - 360;
			}
		} else {
			if (Math.abs(difference) > 180) {
				return 360+difference;
			} else{
				return difference;
			}
		}
	}
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		current+= NineDegreesOfFreedom.getInstance().getYaw(SensorEnum.ARM_NINE_DEGREES_OF_FREEDOM);
		auto.start();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return auto.isFinished();
	}

	// Called once after isFinished returns true
	protected void end() {
		auto.end();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
