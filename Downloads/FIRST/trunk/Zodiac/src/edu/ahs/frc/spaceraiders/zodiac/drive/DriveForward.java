package edu.ahs.frc.spaceraiders.zodiac.drive;

import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Command;
import edu.ahs.frc.spaceraiders.zodiac.Ports;
import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.HumanInput;

/**
 *
 */
public class DriveForward extends Command {

	private double joystickValue;

	public DriveForward() {
		requires(Robot.driveSys);
	}

	protected void initialize() {
	}

	protected void execute() {
	
		double leftJoy = HumanInput.getJoystickAxis(HumanInput.leftJoystick,
				AxisType.kY);
		double rightJoy = HumanInput.getJoystickAxis(HumanInput.rightJoystick,
				AxisType.kY);

		joystickValue = (leftJoy + rightJoy)/2;

		Robot.driveSys.set(joystickValue, Robot.driveSys.talonFrontRight);
		Robot.driveSys.set(joystickValue, Robot.driveSys.talonBackRight);
		Robot.driveSys.set(-joystickValue, Robot.driveSys.talonFrontLeft);
		Robot.driveSys.set(-joystickValue, Robot.driveSys.talonBackLeft);
		System.out.println("[Drive] Drive Straight Value: \t"+joystickValue + "\t\t" + leftJoy + "\t\t" + rightJoy);
		

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {

	}

	protected void interrupted() {
	}
}
