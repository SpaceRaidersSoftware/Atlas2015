package edu.ahs.frc.spaceraiders.zodiac.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * @author Andrew
 */
public class XboxTrigger extends JoystickButton {
	private int triggerTypeValue = 0;
	
	/**
	 * 
	 * @param joystick to go on always xbox.
	 * @param triggerConstant which trigger to register pulled, from human input axis.
	 */	
	public XboxTrigger(GenericHID joystick, int triggerConstant) {
		super(joystick, 0);
		triggerTypeValue = triggerConstant;
	} 
	
	@Override 
	/**
	 * @return trigger value
	 */
	public boolean get() {
		
		double triggerVal = HumanInput.getXboxAxis(HumanInput.xboxController, triggerTypeValue);
		if (triggerVal == 1) {
			return true;
		}
		return false;
	}

}
