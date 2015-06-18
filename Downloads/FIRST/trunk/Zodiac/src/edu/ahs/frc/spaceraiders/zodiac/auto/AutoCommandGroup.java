package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCommandGroup extends CommandGroup {
    
    public  AutoCommandGroup() {
        addSequential(new AutoDrive(6));
        /*
    	addSequential(new AutoBinGrabCommand());
    	addSequential(new AutoDrive(500));
    	addSequential(new AutoTurnCommand(25));
    	addSequential(new AutoDrive(120));
    	addSequential(new AutoHookCommand());
    	addSequential(new AutoDrive(-120));
    	addSequential(new AutoTurnCommand(-25));
    	addSequential(new AutoDrive(200));*/
   }
}
