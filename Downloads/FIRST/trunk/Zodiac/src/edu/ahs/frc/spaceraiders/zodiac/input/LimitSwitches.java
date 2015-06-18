package edu.ahs.frc.spaceraiders.zodiac.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Limit switches
 * @author Andrew
 */
public class LimitSwitches extends Sensor {

	private static LimitSwitches INSTANCE = new LimitSwitches();

	/**
	 * Constructor
	 */
	private class LimitSwitch {
		public double state = -1;
	}

	private Map<SensorEnum, LimitSwitch> limits;

	/**
	 * creates limit switches
	 */
	private LimitSwitches() {
		limits = new HashMap<SensorEnum, LimitSwitch>();
		SerialCommunication.getInstance().register(this);
		limits.put(SensorEnum.LOW_LIMIT_SWITCH, new LimitSwitch());
		limits.put(SensorEnum.HIGH_LIMIT_SWITCH, new LimitSwitch());
		limits.put(SensorEnum.CLAW_PUSH_LIMIT_SWITCH, new LimitSwitch());
	}

	/**
	 * gets instance for limit switches
	 * @return instance
	 */
	public static LimitSwitches getInstance() {
		return INSTANCE;
	}

	/**
	 * gets value for the limit switches
	 * @param e sensorEnum for the limit switch
	 * @return value
	 */
	public boolean getValue(SensorEnum e) {
		
		return limits.get(e).state == 1? true: false;
	}

	@Override
	void handleEvents() {

	}

	@Override
	/**
	 * recieves value
	 */
	public void receiveValue(SensorCommunicationEvent e) {
		updateLimitMap(SensorEnum.HIGH_LIMIT_SWITCH, TagList.HIGH_LIMIT_SWITCH,
				e);
		updateLimitMap(SensorEnum.LOW_LIMIT_SWITCH, TagList.LOW_LIMIT_SWITCH, e);
		updateLimitMap(SensorEnum.CLAW_PUSH_LIMIT_SWITCH,
				TagList.PUSH_LIMIT_SWITCH_ON_FINGER_OF_CLAW, e);

	}

	/**
	 * updates limit map
	 * 
	 * @param e sensorEnum
	 * @param s string for pair
	 * @param event the event
	 */
	private void updateLimitMap(SensorEnum e, String s,
			SensorCommunicationEvent event) {
		LimitSwitch limitSwitchStorage = limits
				.get(e);

		limitSwitchStorage.state = event.getPair(s);

		limits.put(e, limitSwitchStorage);

	}
}