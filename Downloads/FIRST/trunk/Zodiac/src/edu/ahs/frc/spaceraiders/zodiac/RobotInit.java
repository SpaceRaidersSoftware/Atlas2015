package edu.ahs.frc.spaceraiders.zodiac;

import edu.ahs.frc.spaceraiders.zodiac.grabber.GrabberCloseCommand;
import edu.ahs.frc.spaceraiders.zodiac.hook.HookRetractCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RobotInit extends CommandGroup {
	public RobotInit(){
		addParallel(new HookRetractCommand());
		addParallel(new GrabberCloseCommand());
	}
}
