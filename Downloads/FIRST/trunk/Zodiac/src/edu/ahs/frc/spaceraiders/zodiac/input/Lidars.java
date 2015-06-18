package edu.ahs.frc.spaceraiders.zodiac.input;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Encoder;

/**
 * 
 * @author Andrew
 */
public class Lidars extends Sensor{
	
	private static Lidars INSTANCE = new Lidars();
	
	private  Map<SensorEnum, Lidar> lidars;

	/**
	 * Constructor
	 */
	private class Lidar {
		public double distanceOut=-1;//distance out from the something(will find out)
	}

	/**
	 * lidars
	 */
	private Lidars(){
		lidars = new HashMap<SensorEnum, Lidar>();
		lidars.put(SensorEnum.LIDAR,new Lidar());
		SerialCommunication.getInstance().register(this);
	}

	/**
	 * gets instance for lidars
	 * 
	 * @return instance
	 */
	public static Lidars getInstance(){
		return INSTANCE;
	}

	/**
	 * gets value of lidars
	 * 
	 * @param e sensorEnum for lidars
	 * @return lidar value
	 */
	public double getValue(SensorEnum e){
		if (lidars.get(e) != null) {
			return lidars.get(e).distanceOut;
		}
		return -1;
	}
	@Override
	void handleEvents(){

	}
	@Override
	/**
	 * receives value
	 */
	public void receiveValue(SensorCommunicationEvent e) {
		Lidar lidarStorage = lidars.get(SensorEnum.LIDAR);
		lidarStorage.distanceOut=e.getPair(TagList.LIDAR_DISTANCE);
		
		lidars.put(SensorEnum.LIDAR, lidarStorage);
	}
}
