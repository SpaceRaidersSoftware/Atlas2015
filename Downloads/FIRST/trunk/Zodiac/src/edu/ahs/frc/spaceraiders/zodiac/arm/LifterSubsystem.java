package edu.ahs.frc.spaceraiders.zodiac.arm;

import edu.ahs.frc.spaceraiders.zodiac.Ports;
import edu.ahs.frc.spaceraiders.zodiac.hook.HookExtendCommand;
import edu.ahs.frc.spaceraiders.zodiac.input.HumanInput;
import edu.ahs.frc.spaceraiders.zodiac.input.NineDegreesOfFreedom;
import edu.ahs.frc.spaceraiders.zodiac.input.SensorEnum;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Lifter Subsystem
 * 
 * @author Arden
 *
 */
public class LifterSubsystem extends Subsystem {
	private CANTalon lifterMotor = new CANTalon(Ports.ARM_CIM);
	private NineDegreesOfFreedom armNDOF = NineDegreesOfFreedom.getInstance();
	private static final double MAX_HEIGHT = 80;
	private static final double MAX_ANGLE = 157;
	private static final double MIN_HEIGHT = 0;
	private static final double MIN_ANGLE = 27;
	private double previousAngle = getCurrentAngle();
	private boolean overrideSensors = false;

	/**
	 * Constructor
	 */
	public LifterSubsystem() {
	}

	public void overrideSensors(boolean shouldOverride) {
		overrideSensors = shouldOverride;
	}

	public boolean getSensorOverride() {
		return overrideSensors;
	}

	@Override
	/**
	 * sets default command to LifterManCommand
	 */
	public void initDefaultCommand() {
		LifterManCommand lifterManual = new LifterManCommand();
		this.setDefaultCommand(lifterManual);
	}

	public void registerButtons() {
		HumanInput.registerWhenPressedCommand(HumanInput.moveToDefaultButton,
				new LifterMoveToDefaultPosition());
		HumanInput.registerWhenPressedCommand(HumanInput.overrideArmSensors,
				new LifterMoveTo(150));
		HumanInput.registerWhenPressedCommand(HumanInput.maxAngleButton,
				new LifterMoveTo(60));
		HumanInput.registerWhenPressedCommand(HumanInput.noodlingButton,
				new ToggleSensorOverride());
	}

	// TODO: Make names in a property file, not hardcoded

	/**
	 * Stops lifter motor
	 */
	public void stopLifter() {
		// debug
		System.out.println("[Lifter] (stop) Height: " + getCurrentHeight());
		System.out.println("[Lifter] (stop) Angle: " + getCurrentAngle());
		lifterMotor.set(0);
	}

	/**
	 * moves the lifter arm
	 * 
	 * @param speed
	 *            speed to move the arm
	 */
	public void moveLifter(double speed) {
		// debug
		System.out.println("[Lifter] (moveSpeed(" + speed + ")) Height: "
				+ getCurrentHeight());
		System.out.println("[Lifter] (moveSpeed(" + speed + ")) Angle: "
				+ getCurrentAngle());
		if (overrideSensors) {
			System.err.println("OVERRIDING SENSORS");
		}

		// if the arm moves below the default position, close the hook
		if (!overrideSensors
				&& previousAngle >= LifterMoveToDefaultPosition.DEFAULT_LIFTER_ANGLE
				&& getCurrentAngle() < LifterMoveToDefaultPosition.DEFAULT_LIFTER_ANGLE) {
			Scheduler.getInstance().add(new HookExtendCommand());
		}
		previousAngle = getCurrentAngle();

		// if (getCurrentHeight() >= MAX_HEIGHT){
		// lifterMotor.set(0);
		// System.out.println("[Lifter] (moveLifter(" + speed + ") max height: "
		// + MAX_HEIGHT + " reached");
		//
		// } else

		if (!overrideSensors && getCurrentAngle() >= MAX_ANGLE && speed < 0) {
			lifterMotor.set(0);
			System.out.println("[Lifter] (moveLifter(" + speed
					+ ") max angle: " + MAX_ANGLE + " reached");

			// }else if (getCurrentAngle() <= MIN_HEIGHT){
			// lifterMotor.set(0);
			// System.out.println("[Lifter] (moveLifter(" + speed +
			// ") min height: " + MIN_HEIGHT + " reached");
			//
		} else if (!overrideSensors && getCurrentAngle() <= MIN_ANGLE
				&& speed > 0) {
			lifterMotor.set(0);
			System.out.println("[Lifter] (moveLifter(" + speed
					+ ") min angle: " + MIN_ANGLE + " reached");

		} else {

			lifterMotor.set(speed);
		}
	}

	/**
	 * gets the height of the arm using the nine degrees of freedom sensor
	 * 
	 * @return arm height
	 */
	public double getCurrentHeight() {
		return armNDOF.getHeight(SensorEnum.ARM_NINE_DEGREES_OF_FREEDOM);
	}

	/**
	 * gets the angle of the arm using the nine degrees of freedom sensor
	 * 
	 * @return arm angle
	 */
	public double getCurrentAngle() {
		return armNDOF.getAngle(SensorEnum.ARM_NINE_DEGREES_OF_FREEDOM);
	}

}
