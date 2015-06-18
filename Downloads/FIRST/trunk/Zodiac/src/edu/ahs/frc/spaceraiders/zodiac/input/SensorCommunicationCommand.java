package edu.ahs.frc.spaceraiders.zodiac.input;
import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@author Andrew
 */
public class SensorCommunicationCommand extends Command{

	/**
	 * constructor
	 */
	public SensorCommunicationCommand() {
        requires(Robot.sensorSys);
    }

    protected void initialize() {
    }

    /**
     * Handles events
     */
    protected void execute() {
    	Encoders.getInstance().handleEvents();
    	SerialCommunication.getInstance().handleEvents();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
