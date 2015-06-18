package edu.ahs.frc.spaceraiders.zodiac.drive;

import edu.ahs.frc.spaceraiders.zodiac.Ports;
import edu.ahs.frc.spaceraiders.zodiac.input.HumanInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;

/**
 *Drive Subsystem
 *
 *@author Josh
 */
public class DriveSubsystem extends Subsystem {


	public CANTalon talonFrontLeft;
	public CANTalon talonFrontRight;
	public CANTalon talonBackRight;
	public CANTalon talonBackLeft;

	/**
	 * assigns CANTalons
	 */
	public DriveSubsystem() {
		talonFrontLeft = new CANTalon(Ports.DRIVE_FRONT_LEFT);
		talonFrontRight = new CANTalon(Ports.DRIVE_FRONT_RIGHT);
		talonBackLeft = new CANTalon(Ports.DRIVE_BACK_LEFT);
		talonBackRight = new CANTalon(Ports.DRIVE_BACK_RIGHT);

	}

	/**
	 * Gets the value of a CANTalon
	 * 
	 * @param talon CANTalon to get value from
	 * 
	 * @return value of the CANTalon
	 */
	public double get(CANTalon talon) {
		return talon.get();
	}

	/**
	 * Sets the speed of a CANTalon
	 * 
	 * @param speed Speed to set the CANTalon
	 * 
	 * @param talon The CANTalon to set the speed on
	 * 
	 */
	public void set(double speed, CANTalon talon) {
		talon.set(speed);
	}

	public void registerButtons(){
		HumanInput.registerWhileHeldCommand(HumanInput.driveStraightButtonConnector, new DriveForward());
	}
	/**
	 * sets the default command to JoystickControlCommand
	 */
	public void initDefaultCommand() {
		//System.out.println("initDefaultCommand()");
		JoystickControlCommand JCC = new JoystickControlCommand();
		this.setDefaultCommand(JCC);
	}

}