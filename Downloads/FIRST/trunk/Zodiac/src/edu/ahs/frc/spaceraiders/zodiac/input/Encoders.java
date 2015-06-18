package edu.ahs.frc.spaceraiders.zodiac.input;

import java.util.HashMap;
import java.util.Map;

import edu.ahs.frc.spaceraiders.zodiac.Ports;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Encoders
 * 
 * @author Andrew
 *
 */
public class Encoders extends Sensor {
	private static Encoders INSTANCE = new Encoders();

	private Map<SensorEnum, Encoder> encoders;

	private final double WHEEL_DIAMETER = 6.0;

	// diameter of drive wheel is necessary for calculating distances.

	/**
	 * constructor
	 */
	private Encoders() {

		// list and values of encoder objects compared to sensor enum values
		encoders = new HashMap<SensorEnum, Encoder>();

		setup(new Encoder(Ports.DRIVE_ENCODER_LEFT_PORT_A,
				Ports.DRIVE_ENCODER_LEFT_PORT_B, true,
				CounterBase.EncodingType.k4X), SensorEnum.DRIVE_LEFT_ENCODER);

		setup(new Encoder(Ports.DRIVE_ENCODER_RIGHT_PORT_A,
				Ports.DRIVE_ENCODER_RIGHT_PORT_B, true,
				CounterBase.EncodingType.k4X), SensorEnum.DRIVE_RIGHT_ENCODER);

	}

	/**
	 * sets up encoders
	 * 
	 * @param ec the encoder
	 * @param e the sensorEnum
	 */
	private void setup(Encoder ec, SensorEnum e) {

		ec.setMaxPeriod(.1);// time out before stopping
		ec.setDistancePerPulse(5);// distance traveled per hole sensed in the
									// encoder
		ec.setReverseDirection(true);// tells the encoder object which direction
										// is forward
		ec.setSamplesToAverage(127);// number of ticks to average into a value
		encoders.put(e, ec);

	}

	/**
	 * gets instance for encoders
	 * 
	 * @return instance
	 */
	public static Encoders getInstance() {
		return INSTANCE;
	}

	/**
	 * gets distance from the encoder
	 * 
	 * @param e the sensorEnum of the encoder
	 * @return the distance traveled by the encoder
	 */
	public double getDistance(SensorEnum e) {
		return encoders.get(e).getDistance();
	}

	/**
	 * gets calculated distance from the encoder
	 * 
	 * @param e the encoder enum
	 * @return the calculated distance
	 */
	public double getCalculatedDistance(SensorEnum e) {
		return encoders.get(e).getRaw() / 1582 * Math.PI * WHEEL_DIAMETER / 12;
	}

	/**
	 * gets encoder rate
	 * 
	 * @param e sensorEnum of the encoder
	 * @return the rate of the encoder
	 */
	public double getRate(SensorEnum e) {
		return encoders.get(e).getRate();// distance traveled per second
	}

	/**
	 * gets direction of the encoder
	 * 
	 * @param e sensorEnum 
	 * @return the direction
	 */
	public boolean getDirection(SensorEnum e) {
		return encoders.get(e).getDirection();// direction the motor is moving
	}

	/**
	 * gets the raw encoder value
	 * 
	 * @param e encoder enum
	 * @return raw value of the encoder
	 */
	public double getRawEncoderValue(SensorEnum e) {
		return encoders.get(e).getRaw();
	}

	public void handleEvents() {
	}

	public void reset(SensorEnum e) {
		// TODO Auto-generated method stub
		encoders.get(e).reset();
	}

}
