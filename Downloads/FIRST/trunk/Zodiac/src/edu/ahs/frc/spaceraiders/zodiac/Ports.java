package edu.ahs.frc.spaceraiders.zodiac;

/**
 * This ports class is a mapping from the ports that sensors and actuators are
 * wired to a constant field. This provides flexibility changing wiring, makes
 * checking the wiring easier and significantly reduces the number of magic
 * numbers floating around.
 */
public class Ports {
	/*
	 * CAN Talons are numbered from 1-62. Analogs start from 0. Joysticks start
	 * from . Joystick buttons start from .
	 */

	public static final int

	// drive
			DRIVE_FRONT_RIGHT = 1,
			DRIVE_FRONT_LEFT = 2, DRIVE_BACK_RIGHT = 3, DRIVE_BACK_LEFT = 4,

			DRIVE_ENCODER_RIGHT_PORT_A = 0,
			DRIVE_ENCODER_RIGHT_PORT_B = 1,
			DRIVE_ENCODER_LEFT_PORT_A = 2, DRIVE_ENCODER_LEFT_PORT_B = 3,

			// arm
			ARM_CIM = 5,

			// claw
			CLAW_DOUBLE_SOLENOID_RIGHT_FWRD = 5, 
			CLAW_DOUBLE_SOLENOID_RIGHT_RVRS = 4,

			CLAW_DOUBLE_SOLENOID_LEFT_FWRD = 3,
			CLAW_DOUBLE_SOLENOID_LEFT_RVRS = 2,
			
			CLAW_ENCODER_PORT_A = 4,
			CLAW_ENCODER_PORT_B = 5,
			// hook
			HOOK_DOUBLE_SOLENDOID_FWRD = 0,
			HOOK_DOUBLE_SOLENDOID_RVRS = 1,

			// joystick
			LEFT_JOYSTICK = 0,
			RIGHT_JOYSTICK = 1,
			XBOX = 2;
	/*
	 * LEFT_JOYSTICK_TRIGGER = 0, LEFT_JOYSTICK_BTN_2 = 1, LEFT_JOYSTICK_BTN_3 =
	 * 2, LEFT_JOYSTICK_BTN_4 = 3, LEFT_JOYSTICK_BTN_5 = 4, LEFT_JOYSTICK_BTN_6
	 * = 5, LEFT_JOYSTICK_BTN_7 = 6, LEFT_JOYSTICK_BTN_8 = 7,
	 * LEFT_JOYSTICK_BTN_9 = 8, LEFT_JOYSTICK_BTN_10 = 9, LEFT_JOYSTICK_BTN_11 =
	 * 10, LEFT_JOYSTICK_BTN_12 = 11,
	 */

	/*
	 * RIGHT_JOYSTICK_TRIGGER = 0, RIGHT_JOYSTICK_BTN_2 = 1,
	 * RIGHT_JOYSTICK_BTN_3 = 2, RIGHT_JOYSTICK_BTN_4 = 3, RIGHT_JOYSTICK_BTN_5
	 * = 4, RIGHT_JOYSTICK_BTN_6 = 5, RIGHT_JOYSTICK_BTN_7 = 6,
	 * RIGHT_JOYSTICK_BTN_8 = 7, RIGHT_JOYSTICK_BTN_9 = 8, RIGHT_JOYSTICK_BTN_10
	 * = 9, RIGHT_JOYSTICK_BTN_11 = 10, RIGHT_JOYSTICK_BTN_12 = 11;
	 */
}