package edu.ahs.frc.spaceraiders.zodiac.hook;

import edu.ahs.frc.spaceraiders.zodiac.Ports;
import edu.ahs.frc.spaceraiders.zodiac.input.HumanInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

//TODO: extend closes the hook, retract opens the hook.

/**
 * Hook Subsystem
 * 
 *@author Arden
 *@author Fahim
 *@author Josh
 */
public class HookSubsystem extends Subsystem {
	public static final int HOOK_MOVE_TIME = 1000;
	
	private DoubleSolenoid hookSolenoid = 
		new DoubleSolenoid(Ports.HOOK_DOUBLE_SOLENDOID_FWRD, Ports.HOOK_DOUBLE_SOLENDOID_RVRS);

	public void initDefaultCommand() {
	}

	/**
	 * extends hook
	 */
	public void extendHook() {
		hookSolenoid.set(DoubleSolenoid.Value.kForward);

	}

	/**
	 * retracts hook
	 */
	public void retractHook() {
		hookSolenoid.set(DoubleSolenoid.Value.kReverse);

	}

	/**
	 * stops hook
	 */
	public void stopHook() {
		hookSolenoid.set(DoubleSolenoid.Value.kOff);

	}

	/**
	 * registers 2 buttons to hookExtendCommand and hookRetractCommand
	 */
	public void registerButtons() {
		HumanInput.registerWhenPressedCommand(HumanInput.hookOpenButton,new HookExtendCommand() );
		HumanInput.registerWhenPressedCommand(HumanInput.hookCloseButton,new HookRetractCommand() );
	}
}
