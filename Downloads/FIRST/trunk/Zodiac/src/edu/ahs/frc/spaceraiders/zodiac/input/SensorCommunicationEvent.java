package edu.ahs.frc.spaceraiders.zodiac.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Sensor Communication event
 * @author Andrew
 */
public class SensorCommunicationEvent {
	private Map<String, Double> sensorVals = new HashMap<>();

	/**
	 * adds a pair
	 * 
	 * @param tag the tag
	 * @param val the value
	 */
	void addPair(String tag, double val) {
		sensorVals.put(tag, val);
  		System.out.println("[Sensors] " + tag + " " + val);
	}

	/**
	 * gets a pair
	 * @param tag the tag
	 * @return value the tag was paired with
	 */
	double getPair(String tag) {
  		System.out.println("[Sensors] " + tag + " " + sensorVals.get(tag));
		return sensorVals.get(tag) != null ? sensorVals.get(tag)
				: Double.POSITIVE_INFINITY;
	}
}
