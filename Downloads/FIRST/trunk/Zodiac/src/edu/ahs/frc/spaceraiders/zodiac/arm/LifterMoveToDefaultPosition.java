package edu.ahs.frc.spaceraiders.zodiac.arm;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * moves to default position
 * 
 * @author Arden
 */
public class LifterMoveToDefaultPosition extends Command {
	//default lifter height in inches
	public static final double DEFAULT_LIFTER_ANGLE = 45;
	//tolerance is how close the arm has to be to the default height in order to stop
	private static final double TOLERANCE = 2.5;
	private static final double LIFTER_MOVE_SPEED = 0.5;
	
	public LifterMoveToDefaultPosition(){
		requires(Robot.lifterSys);
	}
	
	@Override
	protected void initialize() {
		
			
	}

	@Override
	/**
	 * moves the lifter up/down towards the default position
	 */
	protected void execute() {
		if(!Robot.lifterSys.getSensorOverride() && Robot.lifterSys.getCurrentAngle() > DEFAULT_LIFTER_ANGLE + TOLERANCE){
			Robot.lifterSys.moveLifter(LIFTER_MOVE_SPEED);
		} else if(!Robot.lifterSys.getSensorOverride() && Robot.lifterSys.getCurrentAngle() < DEFAULT_LIFTER_ANGLE - TOLERANCE){
			Robot.lifterSys.moveLifter(-LIFTER_MOVE_SPEED);
		}else{
			System.out.println("[Lifter] (LifterMoveToDefaultPosition) stopping!");
		}
	}

	@Override
	/**
	 * finishes when default height +- tolerance is reached
	 */
	protected boolean isFinished() {
		if (Robot.lifterSys.getSensorOverride())
			return true;
		if(Robot.lifterSys.getCurrentAngle() >= DEFAULT_LIFTER_ANGLE - TOLERANCE
				&& Robot.lifterSys.getCurrentAngle() <= DEFAULT_LIFTER_ANGLE + TOLERANCE){
			System.out.println("[Lifter] (LifterMoveToDefaultPosition) finishing");
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
