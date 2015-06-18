package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.Encoders;
import edu.ahs.frc.spaceraiders.zodiac.input.Lidars;
import edu.ahs.frc.spaceraiders.zodiac.input.SensorEnum;
import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command {
	private double autoDriveSpeed = 0.5; // magic numbers
	private double encoderTravelDistance;
	private double lidarTravelDistance;
	private Encoders encoder = Encoders.getInstance();
	private Lidars lidar = Lidars.getInstance();
	private double startLidarDistance = lidar.getValue(SensorEnum.LIDAR);

	/**
	 * Move the robot during auto a certain distance
	 * 
	 * @param distanceInches
	 * 		Distance to travel (in inches)
	 */
	public AutoDrive(double distanceInches){
		requires(Robot.driveSys);
		encoder.reset(SensorEnum.DRIVE_LEFT_ENCODER);

		encoder.reset(SensorEnum.DRIVE_RIGHT_ENCODER);
		if(distanceInches < 0){
			autoDriveSpeed = -autoDriveSpeed;
		}
		encoderTravelDistance = distanceInches;
		//convert inches (for encoders) to millimeters (for lidar)
		lidarTravelDistance = (distanceInches * 25.4) + startLidarDistance;
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		setDriveSpeed(autoDriveSpeed);
	}

	@Override
	protected boolean isFinished() {
		// debugs
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Left Encoder: "
				+ encoder.getCalculatedDistance(SensorEnum.DRIVE_LEFT_ENCODER));
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Right Encoder: "
				+ encoder.getCalculatedDistance(SensorEnum.DRIVE_RIGHT_ENCODER));
		System.out.println("{Auto} [Drive] (SimpleAuto) Lidar: "
				+ lidar.getValue(SensorEnum.LIDAR));

		if ((encoder.getCalculatedDistance(SensorEnum.DRIVE_LEFT_ENCODER) >= encoderTravelDistance)
				|| (encoder.getCalculatedDistance(SensorEnum.DRIVE_RIGHT_ENCODER) >= encoderTravelDistance)) {
			System.out.println("{Auto} [Drive] (SimpleAuto) Encoder Distance Reached");
			return true;
		}
		
		/*
		if (lidar.getValue(SensorEnum.LIDAR) >= lidarTravelDistance) {
			System.out.println("{Auto} [Drive] (SimpleAuto) Lidar Distance Reached");
			return true;
		}
		*/
		return false;
	}

	@Override
	protected void end() {
		setDriveSpeed(0);
		System.out.println("{Auto} [Drive] (SimpleAuto) Finished! :D");
	}

	@Override
	protected void interrupted() {
		setDriveSpeed(0);
		System.out.println("{Auto} [Drive] (SimpleAuto) Interrupted! D:");
	}

	private void setDriveSpeed(double speed) {
		Robot.driveSys.set(-speed, Robot.driveSys.talonFrontRight);
		Robot.driveSys.set(-speed, Robot.driveSys.talonBackRight);
		Robot.driveSys.set(speed, Robot.driveSys.talonFrontLeft);
		Robot.driveSys.set(speed, Robot.driveSys.talonBackLeft);

		// debug everything!!! :D
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Speed Front Right talon: "
				+ Robot.driveSys.get(Robot.driveSys.talonFrontRight));
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Speed Back Right talon: "
				+ Robot.driveSys.get(Robot.driveSys.talonBackRight));
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Speed Front Left talon: "
				+ Robot.driveSys.get(Robot.driveSys.talonFrontLeft));
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Speed Back Left talon: "
				+ Robot.driveSys.get(Robot.driveSys.talonBackLeft));
	}

}

