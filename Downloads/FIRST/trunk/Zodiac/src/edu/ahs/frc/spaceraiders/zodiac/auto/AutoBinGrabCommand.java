package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.Encoders;
import edu.ahs.frc.spaceraiders.zodiac.input.LimitSwitches;
import edu.ahs.frc.spaceraiders.zodiac.input.SensorEnum;
import edu.wpi.first.wpilibj.command.Command;

public class AutoBinGrabCommand extends Command {
	private static final double BIN_GRAB_ANGLE = 25; // fix later
	private static final double TOLERANCE = 1; // same as in lifterAuto
	private static final double AUTO_BIN_DRIVE_SPEED = 0.15; // drive speed when
																// driving to
																// bin
	private static final double MAX_AUTO_TRAVEL_DISTANCE = 36; // fix later
	private double startPos;
	private boolean finish = false;
	private Encoders encoder = Encoders.getInstance();
	private LimitSwitches limitSwitch = LimitSwitches.getInstance();
	

	public AutoBinGrabCommand() {
		requires(Robot.lifterSys);
		requires(Robot.driveSys);
	}

	@Override
	protected void initialize() {
		startPos = ((getLeftEncoder() + getRightEncoder()) / 2);
	}

	@Override
	protected void execute() {
		// make sure hook is open to grab
		Robot.hookSys.extendHook();
		// lifter up to bin level
		if ((Robot.lifterSys.getCurrentAngle() >= BIN_GRAB_ANGLE - TOLERANCE)
				|| (Robot.lifterSys.getCurrentAngle() <= BIN_GRAB_ANGLE
						+ TOLERANCE)) {
			Robot.lifterSys.stopLifter();
		} else {
			Robot.lifterSys.moveLifter(0.5);
		}
		// TODO? Turn towards bin

		// drive until you ram the bin
		if (limitSwitch.getValue(SensorEnum.CLAW_PUSH_LIMIT_SWITCH) == false) {
			setAutoBinDriveSpeed(AUTO_BIN_DRIVE_SPEED);
		} else {
			setAutoBinDriveSpeed(0);
			// close hook, hopefully grabbing bin
			finish = true;
		}

	}

	@Override
	protected boolean isFinished() {
		if ((getLeftEncoder() > MAX_AUTO_TRAVEL_DISTANCE + startPos)
				|| (getRightEncoder() > MAX_AUTO_TRAVEL_DISTANCE + startPos)) {
			return true;
		}
		return finish;
	}

	@Override
	protected void end() {
		setAutoBinDriveSpeed(0);
		Robot.lifterSys.stopLifter();
	}

	@Override
	protected void interrupted() {
		setAutoBinDriveSpeed(0);
		Robot.lifterSys.stopLifter();
	}

	private void setAutoBinDriveSpeed(double speed) {
		Robot.driveSys.set(speed, Robot.driveSys.talonFrontRight);
		Robot.driveSys.set(speed, Robot.driveSys.talonBackRight);
		Robot.driveSys.set(-speed, Robot.driveSys.talonFrontLeft);
		Robot.driveSys.set(-speed, Robot.driveSys.talonBackLeft);
	}

	private double getLeftEncoder() {
		return encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER);
	}

	private double getRightEncoder() {
		return encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER);
	}
}
