package edu.ahs.frc.spaceraiders.zodiac.grabber;

import edu.ahs.frc.spaceraiders.zodiac.Ports;
import edu.ahs.frc.spaceraiders.zodiac.input.HumanInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * grabber subsystem
 * 
 *@author Fahim
 */
public class GrabberSubsystem extends Subsystem {
	private DoubleSolenoid clawSolenoid1 = new DoubleSolenoid(Ports.CLAW_DOUBLE_SOLENOID_RIGHT_FWRD,Ports.CLAW_DOUBLE_SOLENOID_RIGHT_RVRS);
	private DoubleSolenoid clawSolenoid2 = new DoubleSolenoid(Ports.CLAW_DOUBLE_SOLENOID_LEFT_FWRD, Ports.CLAW_DOUBLE_SOLENOID_LEFT_RVRS);
	public static final int CLAW_MOVE_TIME = 1000;
    
	public void initDefaultCommand() {
    }

	/**
	 * Opens the grabber
	 */
    public void openGrabber(){
		clawSolenoid1.set(DoubleSolenoid.Value.kForward);
		clawSolenoid2.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Closes the grabber
     */
    public void closeGrabber(){
		clawSolenoid1.set(DoubleSolenoid.Value.kReverse);
		clawSolenoid2.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * stops the grabber
     */
    public void stopGrabber(){
		clawSolenoid1.set(DoubleSolenoid.Value.kOff);
		clawSolenoid2.set(DoubleSolenoid.Value.kOff);
    }

    /**
     * registers the grabber buttons to GrabberOpenCommand and GrabberCloseCommand
     */
    public void registerButtons() {
        HumanInput.registerWhenPressedCommand(HumanInput.clawOpenButton, new GrabberOpenCommand());
        HumanInput.registerWhenPressedCommand(HumanInput.clawCloseButton, new GrabberCloseCommand());
	}
}

