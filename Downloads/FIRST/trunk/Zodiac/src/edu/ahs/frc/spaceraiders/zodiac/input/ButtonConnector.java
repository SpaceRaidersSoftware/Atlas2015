package edu.ahs.frc.spaceraiders.zodiac.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class ButtonConnector extends JoystickButton{

	private Button buttonOne;
	private Button buttonTwo;

	public ButtonConnector(GenericHID joystick, int buttonNumber , Button buttonOne, Button buttonTwo) {
		super(joystick, 0);
		this.buttonOne=buttonOne;
		this.buttonTwo=buttonTwo;
		// TODO Auto-generated constructor stub
	}

	@Override 
	/**
	 * @return trigger value
	 */
	public boolean get() {
		
		return buttonOne.get() || buttonTwo.get();

	}
}