package edu.ahs.frc.spaceraiders.zodiac.input;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Encoder;

/**
 * nine degrees of freedom sensor
 * 
 * @author Andrew
 */
public class NineDegreesOfFreedom extends Sensor {

	private static NineDegreesOfFreedom INSTANCE = new NineDegreesOfFreedom();

	private Map<SensorEnum, NDOF> NDOFs;

	/**
	 *this class holds all the various values that the nine degrees of freedom sensor can hold/provide 
	 */
	private class NDOF {
		// height is relative to base
		public double height;
		// angle of arm is relative to starting position
		public double angle;
		// turning angle of the robot
		public double yaw;
		
		
	}

	/**
	 * Constructor. creates hashmap and registers the nine degrees of freedom sensor
	 */
	private NineDegreesOfFreedom() {
		NDOFs = new HashMap<SensorEnum, NDOF>();
		NDOFs.put(SensorEnum.ARM_NINE_DEGREES_OF_FREEDOM, new NDOF());

		SerialCommunication.getInstance().register(this);

	}

	/**
	 * gets the instance for the nine degrees of freedom sensor
	 * 
	 * @return instance
	 */
	public static NineDegreesOfFreedom getInstance() {
		return INSTANCE;
	}

	/**
	 * gets the height of the arm
	 * 
	 * @param e the sensor enum for the nine degrees of freedom sensor
	 * @return the height of the arm
	 */
	public double getHeight(SensorEnum e) {
		if (NDOFs.get(e) != null) {
			return NDOFs.get(e).height;
		}
		return -1;
	}

	/**
	 * gets the angle of the arm
	 * 
	 * @param e the sensor enum for the nine degrees of freedom sensor
	 * @return angle of the arm
	 */
	public double getAngle(SensorEnum e) {
		return NDOFs.get(e).angle;
	}

	/**
	 * gets the distance out from something
	 * 
	 * @param e the sensor enum
	 * @return the distance out
	 */
	public double getYaw(SensorEnum e) {
		if (NDOFs.get(e) != null) {
			return NDOFs.get(e).yaw;
		}
		return -1;
	}

	@Override
	void handleEvents() {

	}

	@Override
	/**
	 * receives values
	 */
	public void receiveValue(SensorCommunicationEvent e) {
		NDOF nineDegreesOfFreedom = NDOFs
				.get(SensorEnum.ARM_NINE_DEGREES_OF_FREEDOM);

		nineDegreesOfFreedom.height = e.getPair(TagList.NDOF_HEIGHT);

		nineDegreesOfFreedom.yaw = e.getPair(TagList.YAW);

		nineDegreesOfFreedom.angle = e.getPair(TagList.NDOF_ANGLE);
		
		NDOFs.put(SensorEnum.ARM_NINE_DEGREES_OF_FREEDOM, nineDegreesOfFreedom);

	}
}