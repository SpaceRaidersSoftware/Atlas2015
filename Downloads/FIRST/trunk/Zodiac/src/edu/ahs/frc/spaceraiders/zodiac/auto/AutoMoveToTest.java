package edu.ahs.frc.spaceraiders.zodiac.auto;

import edu.ahs.frc.spaceraiders.zodiac.Robot;
import edu.ahs.frc.spaceraiders.zodiac.input.Encoders;
import edu.ahs.frc.spaceraiders.zodiac.input.Lidars;
import edu.ahs.frc.spaceraiders.zodiac.input.SensorEnum;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * moves robot to coordinate
 * 
 * @author Arden
 * @author Fahim
 * 
 */
public class AutoMoveToTest extends Command {
	// turning, preparation
	/*
	 * current robot coordinate assume knowledge of starting location
	 */
	private static GPoint robotcoord = new GPoint(0, 0);
	// xy coordinate of destination
	private GPoint destination;
	// distance to travel on a straight line to the destination
	private double travelDistance;
	// angle to turn to to face the destination
	private double angle;
	// change in x (delta x)
	private double changex;
	// change in y (delta y)
	private double changey;

	private AutoTurnCommand turn;

	// driving
	private Encoders encoder = Encoders.getInstance();
	private Lidars lidar = Lidars.getInstance();
	// travel distance for the lidar in mm
	private double lidarTravelDistance;
	// current distance the lidar is at
	private double startLidarDistance;
	private boolean finish = false;
	private boolean useLidar;
	// drive speed
	private static final double AUTO_DRIVE_SPEED = 0.5;

	// accel sideways (accidental turning/sliding)
	BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();
	// acceleration sideways (using the x axis of built in accelerometer)
	private double sidewaysAcceleration;
	/*
	 * how much to slow down the left and right motors to correct for accidental
	 * turning
	 */
	private double leftCorrection;
	private double rightCorrection;
	// deadzone to prevent overcorrection when using the built in accelerometer
	private static final double SIDEWAYS_DEADZONE = 0.1;
	// larger correction proportion = smaller correction. correction =
	// sidewaysAcceleration/CORRECTION_PROPORTION
	private static final double CORRECTION_PROPORTION = 5;

	// trusting sensors
	// whether the lidar value is trusted
	private boolean trustLidar = true;
	// which encoder we trust (left value, right value, or average between them)
	private double trustedEncoderValue;
	// how large a value the lidar can read before it becomes untrusted
	private static final double LIDAR_TRUST_DISTANCE = 30;
	// max difference between the left and right encoder values before we stop
	// trusting one encoder
	private static final double ENCODER_TRUST_DISTANCE = 10;

	// timer
	private long startTime;
	// how long this command can run before it force ends in milliseconds
	private static final long MAX_DRIVE_TIME = 8000;

	/**
	 * Constructor move to absolute point on x y grid
	 * 
	 * @param destination
	 *            destination on the x y grid
	 * 
	 * @param useLidar
	 *            whether to use lidar or not
	 * 
	 */
	public AutoMoveToTest(GPoint destination, boolean useLidar) {
		this.destination = destination;
		this.useLidar = useLidar;

	}

	@Override
	protected void initialize() {
		// sets changex and changey to delta x and y
		changex = destination.getx() - robotcoord.getx();
		changey = destination.gety() - robotcoord.gety();
		// distance formula
		travelDistance = Math.sqrt(Math.pow(changex, 2) + Math.pow(changey, 2));
		// angle to turn to
		angle = Math.atan(changey / changex);
		turn = new AutoTurnCommand(angle);

		// resets encoders to 0 and gets the starting value of the lidar
		encoder.reset(SensorEnum.DRIVE_LEFT_ENCODER);
		encoder.reset(SensorEnum.DRIVE_RIGHT_ENCODER);
		startLidarDistance = lidar.getValue(SensorEnum.LIDAR);
		// convert distance from inches to millimeters (25.4)
		lidarTravelDistance = (travelDistance * 25.4) + startLidarDistance;

		// gets the start time of the command
		startTime = System.currentTimeMillis();
	}

	@Override
	protected void execute() {
		if (turn.isFinished()) {
			// acceleration x axis (sideways)
			sidewaysAcceleration = accelerometer.getX();
			System.out
					.println("{Auto} (AutoMoveToTest) sideways acceleration: "
							+ sidewaysAcceleration);

			// resets corrections. assumes you're going straight
			leftCorrection = 0;
			rightCorrection = 0;

			// acceleration in x is towards the right
			// if positive, (moving right) increases left correction to slow
			// down
			// the left motors. opposite happens for right side
			if (sidewaysAcceleration > SIDEWAYS_DEADZONE) {
				leftCorrection = sidewaysAcceleration / CORRECTION_PROPORTION;
			} else if (sidewaysAcceleration < -SIDEWAYS_DEADZONE) {
				rightCorrection = -sidewaysAcceleration / CORRECTION_PROPORTION;
			}

			/*
			 * if one encoder is spinning (reading this large inaccurate value),
			 * use the smaller encoder value as the trusted value otherwise, the
			 * trusted encoder value is the average between the left and right
			 * encoder values
			 */
			if (Math.abs(encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER)
					- encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER)) > ENCODER_TRUST_DISTANCE) {
				System.out
						.println("{Auto} (AutoMoveToTest) one encoder spinning, using shorter encoder value");
				trustedEncoderValue = Math.min(
						encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER),
						encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER));
			} else {
				trustedEncoderValue = (encoder
						.getDistance(SensorEnum.DRIVE_LEFT_ENCODER) + encoder
						.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER)) / 2;
			}

			// debugs
			System.out.println("{Auto} (AutoMoveToTest) trustedEncoderValue: "
					+ trustedEncoderValue);

			// sets the drive speeds to the speed - the corrections
			setRightDriveSpeed(AUTO_DRIVE_SPEED - rightCorrection);
			setLeftDriveSpeed(AUTO_DRIVE_SPEED - leftCorrection);
		} else {
			turn.start();
		}
	}

	@Override
	protected boolean isFinished() {
		// debugs
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Left Encoder: "
				+ encoder.getDistance(SensorEnum.DRIVE_LEFT_ENCODER));
		System.out.println("{Auto} [Drive] (SimpleAuto) Drive Right Encoder: "
				+ encoder.getDistance(SensorEnum.DRIVE_RIGHT_ENCODER));
		System.out.println("{Auto} [Drive] (SimpleAuto) Lidar: "
				+ lidar.getValue(SensorEnum.LIDAR));

		// lidar. if the lidar is used, trusted, and reads a value that is still
		// trusted (not too large)
		if (useLidar && trustLidar
				&& (lidar.getValue(SensorEnum.LIDAR) <= LIDAR_TRUST_DISTANCE)) {
			// if the lidar says we finished, finish
			if (lidar.getValue(SensorEnum.LIDAR) >= lidarTravelDistance) {
				finish = true;
			}
		} else if (lidar.getValue(SensorEnum.LIDAR) > LIDAR_TRUST_DISTANCE) {
			// if the lidar reads too large a value, it isn't trusted anymore
			trustLidar = false;
		}

		// encoders. if the trusted encoder value says the distance is reached,
		// finish
		if (trustedEncoderValue >= travelDistance) {
			finish = true;
		}

		// timer. if this command has been executing for too long, it just ends
		if (System.currentTimeMillis() >= (startTime + MAX_DRIVE_TIME)) {
			return true;
		}

		return finish;
	}

	@Override
	protected void end() {
		// stops the robot, sets the current coordinate to the destination
		setRightDriveSpeed(0);
		setLeftDriveSpeed(0);
		System.out.println("{Auto} [Drive] (AutoMoveTo) Finished! :D");
		robotcoord = destination;
	}

	@Override
	protected void interrupted() {
		// stops the robot.
		setRightDriveSpeed(0);
		setLeftDriveSpeed(0);
		System.out.println("{Auto} [Drive] (AutoMoveTo) Interrupted! D:");
	}

	/**
	 * sets the drive speed of the right motors
	 * 
	 * @param speed
	 *            the speed (0-1)
	 */
	private void setRightDriveSpeed(double speed) {
		Robot.driveSys.set(speed, Robot.driveSys.talonFrontRight);
		Robot.driveSys.set(speed, Robot.driveSys.talonBackRight);

		// debugs
		System.out
				.println("{Auto} [Drive] (SimpleAuto) Drive Speed Front Right talon: "
						+ Robot.driveSys.get(Robot.driveSys.talonFrontRight));
		System.out
				.println("{Auto} [Drive] (SimpleAuto) Drive Speed Back Right talon: "
						+ Robot.driveSys.get(Robot.driveSys.talonBackRight));
	}

	/**
	 * sets the drive speed of the left motors
	 * 
	 * @param speed
	 *            the speed (0-1)
	 */
	private void setLeftDriveSpeed(double speed) {
		// sets the opposite because the left motors move the opposite direction
		Robot.driveSys.set(-speed, Robot.driveSys.talonFrontLeft);
		Robot.driveSys.set(-speed, Robot.driveSys.talonBackLeft);

		// debugs
		System.out
				.println("{Auto} [Drive] (SimpleAuto) Drive Speed Front Left talon: "
						+ Robot.driveSys.get(Robot.driveSys.talonFrontLeft));
		System.out
				.println("{Auto} [Drive] (SimpleAuto) Drive Speed Back Left talon: "
						+ Robot.driveSys.get(Robot.driveSys.talonBackLeft));
	}

}
