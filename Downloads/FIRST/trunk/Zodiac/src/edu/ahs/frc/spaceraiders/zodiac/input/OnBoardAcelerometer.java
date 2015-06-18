package edu.ahs.frc.spaceraiders.zodiac.input;

import java.util.HashMap;
import java.util.Map;

import edu.ahs.frc.spaceraiders.zodiac.Ports;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;

/**
 * On board Accelerometer
 * 
 * @author Andrew
 */
public class OnBoardAcelerometer {
	private static OnBoardAcelerometer INSTANCE = new OnBoardAcelerometer();
	private BuiltInAccelerometer bia = new BuiltInAccelerometer();
	private double turnAngle =0;

	private OnBoardAcelerometer() {
		// list and values of encoder objects compared to sensor enum values
	}

	/**
	 * gets instance for the on board accelerometer
	 * 
	 * @return instance
	 */
	public static OnBoardAcelerometer getInstance() {
		return INSTANCE;
	}

	/**
	 * gets the angle
	 * 
	 * @return the angle
	 */
	public double getAngle(){
		return turnAngle;
		
	}

	/**
	 * sets the turn angle to 0
	 */
	public void reset(){
		turnAngle=0;
	}

	public void handleEvents() {
		
	}
}
