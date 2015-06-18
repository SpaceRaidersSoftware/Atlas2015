package edu.ahs.frc.spaceraiders.zodiac.input;

import edu.ahs.frc.spaceraiders.zodiac.Ports;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author Andrew
 */
public class HumanInput {
	public static final int
	// LEFT IS POSITIVE AND RIGHT IS NEGATIVE
			XBOX_LEFT_STICK_X_AXIS = 0,
			// UP IS POSITIVE AND DOWN IS NEGATIVE
			XBOX_LEFT_STICK_Y_AXIS = 1,
			XBOX_LEFT_TRIGGER_AXIS = 2,
			// LEFT TRIGGER PRESS IS POSITIVE AND RIGHT TRIGGER PRESS IS
			// NEGATIVE
			XBOX_RIGHT_TRIGGER_AXIS = 3,
			// LEFT IS POSITIVE AND RIGHT IS NEGATIVE
			XBOX_RIGHT_STICK_X_AXIS = 4,
			XBOX_RIGHT_STICK_Y_AXIS = 5,
			XBOX_DIRECTIONAL_PAD = 6; // LEFT IS POSITIVE AND RIGHT IS NEGATIVE

	public static Joystick leftJoystick = new Joystick(Ports.LEFT_JOYSTICK);

	public static Joystick xboxController = new Joystick(Ports.XBOX);

	public static Joystick rightJoystick = new Joystick(Ports.RIGHT_JOYSTICK);

	public static Button lifterMoveUpButton = new JoystickButton(
			xboxController, 4);
	public static Button lifterMoveDownButton = new JoystickButton(
			xboxController, 1);
	public static Button clawCloseButton = new XboxTrigger(xboxController,
			XBOX_RIGHT_TRIGGER_AXIS);

	public static Button clawOpenButton = new XboxTrigger(xboxController,
			XBOX_LEFT_TRIGGER_AXIS);

	public static Button hookCloseButton = new JoystickButton(xboxController, 5);
	public static Button hookOpenButton = new JoystickButton(xboxController, 6);
	
	public static Button noodlingButton = new JoystickButton(xboxController, 1);
	public static Button maxAngleButton = new JoystickButton(xboxController, 3);
	
	public static Button overrideArmSensors = new JoystickButton(xboxController, 7);
	
	public static Button moveToDefaultButton = new JoystickButton(xboxController, 8);
	
	public static Button driveStraightButtonLeft= new JoystickButton(leftJoystick,1);
	public static Button driveStraightButtonRight= new JoystickButton(rightJoystick,1);
	
	public static Button driveStraightButtonConnector = new ButtonConnector(leftJoystick, 0, 
			driveStraightButtonLeft, driveStraightButtonRight);

	/*
	 * public static Button xboxButtonA = new JoystickButton(xboxController, 1);
	 * public static Button xboxButtonB = new JoystickButton(xboxController, 2);
	 * public static Button xboxButtonX = new JoystickButton(xboxController, 3);
	 * public static Button xboxButtonY = new JoystickButton(xboxController, 4);
	 * public static Button xboxLeftBumper = new JoystickButton(xboxController,
	 * 5); public static Button xboxRightBumper = new
	 * JoystickButton(xboxController, 6); public static Button xboxSelect = new
	 * JoystickButton(xboxController, 7); public static Button xboxStart = new
	 * JoystickButton(xboxController, 8); public static Button
	 * xboxRightStickButton = new JoystickButton( xboxController, 9); public
	 * static Button xboxLeftStickButton = new JoystickButton( xboxController,
	 * 10); public static Button xboxRightTrigger = new
	 * XboxTrigger(xboxController,XboxTrigger.TriggerConstant.RIGHT_TRIGGER);
	 * 
	 * public static Button xboxLeftTrigger = new
	 * XboxTrigger(xboxController,XboxTrigger.TriggerConstant.LEFT_TRIGGER);
	 */

	private static final double DEADZONE = 0.055;
	private static final double DEADZONE_STRETCH = 1.228333;
	private static final double XBOX_DEADZONE = 0.15;

	/**
	 * register a command for when the button is first pressed
	 * 
	 * @param b
	 *            the button to be registered found as a static Button in the
	 *            HumanInput class.
	 * @param cmd
	 *            the user provided Command to be registered
	 */
	public static void registerWhenPressedCommand(Button b, Command cmd) {

		((Button) b).whenPressed(cmd);

	}

	/**
	 * register a command for when the button is released
	 * 
	 * @param b
	 *            the button to be registered found as a static Button in the
	 *            HumanInput class.
	 * @param cmd
	 *            the user provided Command to be registered
	 */
	public static void registerWhenReleasedCommand(Button b, Command cmd) {
		b.whenReleased(cmd);
	}

	/**
	 * register a command to be run as long as the button is held
	 * 
	 * @param b
	 *            the button to be registered found as a static Button in the
	 *            HumanInput class.
	 * @param cmd
	 *            the user provided Command to be registered
	 */
	public static void registerWhileHeldCommand(Button b, Command cmd) {

		b.whileHeld(cmd);

	}

	/**
	 * This get the raw axis value from a particular axis which is defined by an
	 * axis number.
	 * 
	 * @param j
	 *            joystick to get axis from.
	 * @param i
	 *            static final value of axis defined in human input.
	 * @return value of axis
	 */
	public static double getXboxAxis(Joystick j, int i) {
		double val = j.getRawAxis(i);
		if (val < XBOX_DEADZONE && val > -XBOX_DEADZONE) {
			return 0.0;
		}
		return val;

	}

	/**
	 * gets joystick axis
	 * 
	 * @param j the joystick
	 * @param ax the axis type
	 * @return the joystick axis
	 */
	public static double getJoystickAxis(Joystick j, AxisType ax) {
		double val = j.getAxis(ax);
		if (val < DEADZONE && val > -DEADZONE) {
			return 0.0;
		}
		else{
			if(val>0.0){
				val=DEADZONE_STRETCH*Math.pow((val-DEADZONE),3);
				//equation for adjusted axis values
			}
			else{
				val=Math.abs(val);
				val=DEADZONE_STRETCH*Math.pow((val-DEADZONE),3);
				val=-val;
			}
		}
		return val;

	}
}