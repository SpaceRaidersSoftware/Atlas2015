package edu.ahs.frc.spaceraiders.zodiac.input;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Serial Subsystem
 * @author Andrew
 */
public class SerialSubsystem extends Subsystem {

	public static SerialPort serial; //Someone could change this (in theory).

	/**
	 * sets up serial
	 */
	public void initDefaultCommand() {
        
    	// baudrate = bits per second
		
		serial = new SerialPort(57600,Port.kMXP); // Initialize serial with given
		
		try {
			serial.flush(); 
//	    	String currentChar = ""; //why is this code still here?
//	    	while (serial.getBytesReceived() > 0) {
//	    			byte[] blah = serial.read(1);
//	    			try {
//	    				currentChar = new String(blah, "UTF-8");
//	    				System.out.print(currentChar);
//	    			}
//	    			catch(Exception e){
//	    				
//	    			}
//	    	}
			//TODO: Only catch exceptions that we except to get, print out any other exceptions
	        setDefaultCommand(new SensorCommunicationCommand());
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }
}