package edu.ahs.frc.spaceraiders.zodiac.arm;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * moves to default position
 * 
 * @author Arden
 */
public class LifterMoveTo extends Command {
	//tolerance is how close the arm has to be to the angle in order to stop
	private static final double TOLERANCE = 2.5;
	private static final double LIFTER_MOVE_SPEED = 0.5;
	private double angle;
	
	public LifterMoveTo(double angle){
		requires(Robot.lifterSys);
		this.angle = angle;
	}
	
	@Override
	protected void initialize() {			
	}

	@Override
	/**
	 * moves the lifter up/down towards the default position
	 */
	protected void execute() {
		if(!Robot.lifterSys.getSensorOverride() && Robot.lifterSys.getCurrentAngle() > angle + TOLERANCE){
			Robot.lifterSys.moveLifter(LIFTER_MOVE_SPEED);
		} else if(!Robot.lifterSys.getSensorOverride() && Robot.lifterSys.getCurrentAngle() < angle - TOLERANCE){
			Robot.lifterSys.moveLifter(-LIFTER_MOVE_SPEED);
		}else{
			System.out.println("[Lifter] (LifterMoveTo(" + angle + ") stopping!");
		}
	}

	@Override
	/**
	 * finishes when angle +- tolerance is reached
	 */
	protected boolean isFinished() {
		if (Robot.lifterSys.getSensorOverride())
			return true;
		if(Robot.lifterSys.getCurrentAngle() >= angle - TOLERANCE
				&& Robot.lifterSys.getCurrentAngle() <= angle + TOLERANCE){
			System.out.println("[Lifter] (LifterMoveTo(" + angle + ") finishing");
			return true;
		}
		return false;
	}

	@Override
	/**
	 * stops lifter
	 */
	protected void end() {
		Robot.lifterSys.stopLifter();
	}

	@Override
	/**
	 * stops lifter
	 */
	protected void interrupted() {
		Robot.lifterSys.stopLifter();
	}

}
