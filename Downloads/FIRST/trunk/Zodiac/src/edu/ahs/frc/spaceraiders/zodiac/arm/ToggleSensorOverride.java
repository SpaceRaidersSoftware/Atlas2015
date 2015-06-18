package edu.ahs.frc.spaceraiders.zodiac.arm;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ToggleSensorOverride extends Command {

	@Override
	protected void initialize() {
		Robot.lifterSys.overrideSensors(!Robot.lifterSys.getSensorOverride());
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
