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
public class AutoTurnCommand extends Command {

	private double endAngle;
	private double angle=0;
	private final double SPEED = 0.5;
//in inches 
	private boolean stop = false;

	/**
	 * 
	 * @param turnAngle
	 * 		Angle to turn (counterclockwise) in degrees
	 * 
	 */
	public AutoTurnCommand(double turnAngle) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSys);
		endAngle=turnAngle;
//		this.turnDistance = Math.toRadians(turnAngle) * ROBOT_RADIUS
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!stop) {
			Robot.driveSys.set(SPEED, Robot.driveSys.talonBackLeft);
			Robot.driveSys.set(SPEED, Robot.driveSys.talonFrontLeft);
			Robot.driveSys.set(SPEED, Robot.driveSys.talonBackRight);
			Robot.driveSys.set(SPEED, Robot.driveSys.talonFrontRight);
		}
		else{

			Robot.driveSys.set(0, Robot.driveSys.talonBackLeft);
			Robot.driveSys.set(0, Robot.driveSys.talonFrontLeft);
			Robot.driveSys.set(0, Robot.driveSys.talonBackRight);
			Robot.driveSys.set(0, Robot.driveSys.talonFrontRight);
		}

		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		angle=NineDegreesOfFreedom.getInstance().getYaw(SensorEnum.ARM_NINE_DEGREES_OF_FREEDOM);
		if (angle > endAngle) {
			stop = true;
		}
		if(stop){
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
//
//		Robot.driveSys.set(0, Robot.driveSys.talonBackLeft);
//		Robot.driveSys.set(0, Robot.driveSys.talonFrontLeft);
//
//		Robot.driveSys.set(0, Robot.driveSys.talonBackRight);
//		Robot.driveSys.set(0, Robot.driveSys.talonFrontRight);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

		Robot.driveSys.set(0, Robot.driveSys.talonBackLeft);
		Robot.driveSys.set(0, Robot.driveSys.talonFrontLeft);

		Robot.driveSys.set(0, Robot.driveSys.talonBackRight);
		Robot.driveSys.set(0, Robot.driveSys.talonFrontRight);
	}
}
