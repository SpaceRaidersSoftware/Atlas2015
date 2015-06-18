package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {
	public static SendableChooser autoChoice;
	public AutoChooser(){
		autoChoice= new SendableChooser();
        autoChoice.addDefault("Simple Autonomous", new AutoCommandGroup());
        autoChoice.addObject("Auto Turning", new AutoTurnCommand(30));
        autoChoice.addObject("Auto Turn Absolute", new AutoTurnToCommand(30));
        autoChoice.addObject("Point to Point Turning", new AutoMoveToTest(new GPoint(20,20), false));
        autoChoice.addObject("Bin Engage", new AutoBinGrabCommand());
        SmartDashboard.putData("autonomous MODE!!!!!",autoChoice);
	}
	public Command getAutoChoice(){
		return (Command) autoChoice.getSelected();
		
	}

}
