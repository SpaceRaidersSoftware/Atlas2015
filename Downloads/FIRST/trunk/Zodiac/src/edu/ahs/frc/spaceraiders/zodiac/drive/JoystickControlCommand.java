package edu.ahs.frc.spaceraiders.zodiac.drive;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.HumanInput;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses the Joysticks to control the driving
 * 
 *@author Josh
 */
public class JoystickControlCommand extends Command {

	/**
	 * Constructor
	 */
	public JoystickControlCommand() {
		requires(Robot.driveSys);
	}

	protected void initialize() {
	}

	/**
	 * Sets speed of drive motors according to joystick input
	 */
	protected void execute() {
		//System.out.println("Hello");
		double rightVal = HumanInput.getJoystickAxis(HumanInput.rightJoystick,
				AxisType.kY);
		
		Robot.driveSys.set(rightVal, Robot.driveSys.talonFrontRight);
		Robot.driveSys.set(rightVal, Robot.driveSys.talonBackRight);

		

		double leftVal = HumanInput.getJoystickAxis(HumanInput.leftJoystick, AxisType.kY);
		
		Robot.driveSys.set(-leftVal, Robot.driveSys.talonFrontLeft);
		Robot.driveSys.set(-leftVal, Robot.driveSys.talonBackLeft);
		
	}

	protected boolean isFinished() {
		return false;
	}

	/**
	 * stops drive motors
	 */
	protected void end() {
		Robot.driveSys.set(0, Robot.driveSys.talonFrontRight);
		Robot.driveSys.set(0, Robot.driveSys.talonBackRight);
		Robot.driveSys.set(0, Robot.driveSys.talonFrontLeft);
		Robot.driveSys.set(0, Robot.driveSys.talonBackLeft);
	}

	/**
	 * stops drive motors
	 */
	protected void interrupted() {
		Robot.driveSys.set(0, Robot.driveSys.talonFrontRight);
		Robot.driveSys.set(0, Robot.driveSys.talonBackRight);
		Robot.driveSys.set(0, Robot.driveSys.talonFrontLeft);
		Robot.driveSys.set(0, Robot.driveSys.talonBackLeft);
	}
}
