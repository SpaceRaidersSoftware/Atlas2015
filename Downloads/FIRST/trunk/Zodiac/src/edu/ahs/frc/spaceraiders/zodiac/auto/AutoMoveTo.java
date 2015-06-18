package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.Encoders;
import edu.ahs.frc.spaceraiders.zodiac.input.Lidars;
import edu.ahs.frc.spaceraiders.zodiac.input.SensorEnum;
import edu.wpi.first.wpilibj.command.Command;

/**
 * moves robot to coordinate
 * 
 * @author Arden
 * @author Fahim
 *
 */
public class AutoMoveTo extends Command {
	private GPoint robotcoord;
	private GPoint destination;
	private double travelDistance;
	private double angle;
	private double changex;
	private double changey;

	private double autoDriveSpeed;
	private double rightEncoderTravelDistance;
	private double leftEncoderTravelDistance;
	private double lidarTravelDistance;
	private Encoders encoder = Encoders.getInstance();
	private Lidars lidar = Lidars.getInstance();
	private double startLidarDistance;
	private double leftEncoderStartDistance;
	private double rightEncoderStartDistance;
	private boolean finish = false;
	private boolean useLidar = false;
	private boolean rightFinish = false;
	private boolean leftFinish = false;
	private static final double MOVE_SPEED = 0.5;

	/**
	 * Constructor
	 * move to absolute point on x y grid
	 */
	public AutoMoveTo(GPoint destination){
		this.destination = destination;
	}
	
	@Override
	protected void initialize() {
		changex = destination.getx() - robotcoord.getx();
		changey = destination.gety() - robotcoord.gety();
		travelDistance = Math.sqrt(Math.pow(changex, 2) + Math.pow(changey, 2));
		angle = Math.atan(changey/changex);
		//TODO: Turn to angle
		
		startLidarDistance = lidar.getValue(SensorEnum.LIDAR);
		leftEncoderStartDistance = encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER);
		rightEncoderStartDistance = encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER);
		rightEncoderTravelDistance = travelDistance + rightEncoderStartDistance;
		leftEncoderTravelDistance = travelDistance + leftEncoderStartDistance;
		lidarTravelDistance = (travelDistance * 25.4) + startLidarDistance;
		
		if(travelDistance < 0){
			autoDriveSpeed = -MOVE_SPEED;
		} else if(travelDistance > 0){
			autoDriveSpeed = MOVE_SPEED;
		} else{
			finish = true;
		}
	}

	@Override
	protected void execute() {
		//right
		if(rightEncoderTravelDistance > rightEncoderStartDistance){
			if(encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER) < rightEncoderTravelDistance){
				setRightDriveSpeed(autoDriveSpeed);
				System.out.println("{Auto} [Drive] (AutoDrive) Right motor going");
			} else if(encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER) >= rightEncoderTravelDistance){
				setRightDriveSpeed(0);
				rightFinish = true;
				System.out.println("{Auto} [Drive] (AutoDrive) Right encoder distance reached");
			} else{
				System.out.println("{Auto} [Drive] (AutoDrive) Right execution error moving forward");
				rightFinish = true;
			}
		}else if(rightEncoderTravelDistance < rightEncoderStartDistance){
			if(encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER) > rightEncoderTravelDistance){
				setRightDriveSpeed(autoDriveSpeed);
				System.out.println("{Auto} [Drive] (AutoDrive) Right motor going");
			} else if(encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER) <= rightEncoderTravelDistance){
				setRightDriveSpeed(0);
				rightFinish = true;
				System.out.println("{Auto} [Drive] (AutoDrive) Right encoder distance reached");
			} else{
				System.out.println("{Auto} [Drive] (AutoDrive) Right execution error moving backwards");
				rightFinish = true;
			}
		}else{
			System.out.println("Right execution error moving in general");
			rightFinish = true;
		}

		//left
		if(leftEncoderTravelDistance > leftEncoderStartDistance){
			if(encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER) < leftEncoderTravelDistance){
				setLeftDriveSpeed(autoDriveSpeed);
				System.out.println("{Auto} [Drive] (AutoDrive) Left motor going");
			} else if(encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER) >= leftEncoderTravelDistance){
				setLeftDriveSpeed(0);
				leftFinish = true;
				System.out.println("{Auto} [Drive] (AutoDrive) Left encoder distance reached");
			} else{
				System.out.println("{Auto} [Drive] (AutoDrive) Left execution error moving forward");
				leftFinish = true;
			}
		}else if(leftEncoderTravelDistance < leftEncoderStartDistance){
			if(encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER) > leftEncoderTravelDistance){
				setLeftDriveSpeed(autoDriveSpeed);
				System.out.println("{Auto} [Drive] (AutoDrive) Left motor going");
			} else if(encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER) <= leftEncoderTravelDistance){
				setLeftDriveSpeed(0);
				leftFinish = true;
				System.out.println("{Auto} [Drive] (AutoDrive) Left encoder distance reached");
			} else{
				System.out.println("{Auto} [Drive] (AutoDrive) Left execution error moving backwards");
				leftFinish = true;
			}
		}else{
			System.out.println("Left execution error moving in general");
			leftFinish = true;
		}

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
//TODO change left/right travel distance
		//lidar
		if(useLidar == true 
				&& (rightEncoderTravelDistance > rightEncoderStartDistance 
						|| leftEncoderTravelDistance > leftEncoderStartDistance)
				&& lidar.getValue(SensorEnum.LIDAR) >= lidarTravelDistance){
			finish = true;
		} else if(useLidar == true 
				&& (rightEncoderTravelDistance < rightEncoderStartDistance
						|| leftEncoderTravelDistance < leftEncoderStartDistance)
				&& lidar.getValue(SensorEnum.LIDAR) <= lidarTravelDistance){
			finish = true;
		} else{}
		
		//encoders
		if (leftFinish && rightFinish){
			finish = true;
		}
 		
		return finish;
	}

	@Override
	protected void end() {
		setRightDriveSpeed(0);
		setLeftDriveSpeed(0);
		System.out.println("{Auto} [Drive] (AutoMoveTo) Finished! :D");
		robotcoord = destination;
	}

	@Override
	protected void interrupted() {
		setRightDriveSpeed(0);
		setLeftDriveSpeed(0);
		System.out.println("{Auto} [Drive] (AutoMoveTo) Interrupted! D:");
	}

		
	/**
	 * sets the drive speed of the right motors
	 * @param speed the speed (0-1)
	 */
	private void setRightDriveSpeed(double speed) {
		Robot.driveSys.set(speed, Robot.driveSys.talonFrontRight);
		Robot.driveSys.set(speed, Robot.driveSys.talonBackRight);

		// debug everything!!! :D
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Speed Front Right talon: "
				+ Robot.driveSys.get(Robot.driveSys.talonFrontRight));
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Speed Back Right talon: "
				+ Robot.driveSys.get(Robot.driveSys.talonBackRight));
	}
	
	/**
	 * sets the drive speed of the left motors
	 * @param speed the speed (0-1)
	 */
	private void setLeftDriveSpeed(double speed){
		Robot.driveSys.set(-speed, Robot.driveSys.talonFrontLeft);
		Robot.driveSys.set(-speed, Robot.driveSys.talonBackLeft);

		// debug everything!!! :D
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Speed Front Left talon: "
				+ Robot.driveSys.get(Robot.driveSys.talonFrontLeft));
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Speed Back Left talon: "
				+ Robot.driveSys.get(Robot.driveSys.talonBackLeft));
	}

}
