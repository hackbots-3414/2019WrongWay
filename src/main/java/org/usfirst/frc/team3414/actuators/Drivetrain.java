package org.usfirst.frc.team3414.actuators;

import java.awt.Robot;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.actuators.DoubleMotor;
//import org.usfirst.frc.team3414.actuators.TripleMotor;
import org.usfirst.frc.team3414.actuators.SpeedPlan;
import org.usfirst.frc.team3414.actuators.MoveCorrection;
import org.usfirst.frc.team3414.autonomous.AutonStatus;
import org.usfirst.frc.team3414.sensor.HBJoystick;
import org.usfirst.frc.team3414.sensor.NavX;
//import org.usfirst.frc.team3414.sensor.NavXThread;
import org.usfirst.frc.team3414.sensor.SensorConfig;
import org.usfirst.frc.team3414.util.Status;
import org.usfirst.frc.team3414.util.TurnDirection;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team3414.robot.RobotStatus;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain implements IDriveTrain {
	
	private DoubleMotor rightMotor;
	private DoubleMotor leftMotor;
	
	

//???  3 motors, or triple motors	
//	private TripleMotor rightMotor;
//	private TripleMotor leftMotor;
	
	double currentHeading;
	
	private HBJoystick rightJoystick;
	private HBJoystick leftJoystick;
	
	private double startYaw;
	private double endYaw;
	
	private boolean isSwitched = false;
	
	private final int maxEncoderRecordsCount = 100;
	private Samplings robotVelocityXSamplings = new Samplings();
	private Samplings leftEncoderSamplings = new Samplings();
	private Samplings rightEncoderSamplings = new Samplings();
	
	private Samplings liftEncoderSamplings = new Samplings();
	
	public boolean turnRadiusCancel = false;
	
	public SpeedPlan turnRadiusSpeedPlan = new SpeedPlan();
	public SpeedPlan moveStraightSpeedPlan = new SpeedPlan();
	public SpeedPlan liftSpeedPlan = new SpeedPlan();

	public MoveCorrection moveCorrection = new MoveCorrection(); 
	
	public Drivetrain(DoubleMotor rightMotor, DoubleMotor leftMotor)
	{
		this.rightMotor = rightMotor;
		this.leftMotor = leftMotor;
	}

//  3 motors, or triple motors	
//	public Drivetrain(TripleMotor rightMotor, TripleMotor leftMotor)
//	{
//		this.rightMotor = rightMotor;
//		this.leftMotor = leftMotor;
//	}

	public void setSpeed(double speed)
	{
		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
	}

	public void setSpeed(double leftSpeed, double rightSpeed)
	{
		leftMotor.setSpeed(leftSpeed);
		rightMotor.setSpeed(rightSpeed);
	}

	public void stop()
	{
		leftMotor.stop();
		rightMotor.stop();
	}

	public void stopIntake()
	{
		if(ActuatorConfig.getInstance().getOnlyDriveTrain())
			return;
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
	}

	public void stopIntakeAngler()
	{
		if(ActuatorConfig.getInstance().getOnlyDriveTrain())
			return;
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
	}

	public void stopLift()
	{
		if(ActuatorConfig.getInstance().getOnlyDriveTrain())
			return;
		ActuatorConfig.getInstance().getLift().setSpeed(0);
	}

	public void stopWings()
	{
		if(ActuatorConfig.getInstance().getOnlyDriveTrain())
			return;
		if(ActuatorConfig.getInstance().getWingDisabled())
			return;
		
		ActuatorConfig.getInstance().getDoubleMotorWings().setSpeed(0);
	}
	
	/**
	 * Stop all actuators
	 * 
	 * @param none
	 */
	public void stopActuators()
	{
		// stop drive train
		stop();
		
		// stop actuators in sub-system
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
		{
			stopIntake();
			stopIntakeAngler();
			stopLift();
			if(!ActuatorConfig.getInstance().getWingDisabled())
				stopWings();
		}
	}
	
	public void setInitialServoPosition()
	{
		//The wings were removed from Alpha
		if(ActuatorConfig.getInstance().getWingDisabled())
			return;

		ActuatorConfig.getInstance().getServoWingOne().disengage();
		ActuatorConfig.getInstance().getServoWingTwo().setAngle(130);
	}

	public DoubleMotor getRightMotor()
	{
		return rightMotor;
	}

	public DoubleMotor getLeftMotor()
	{
		return leftMotor;
	}

/*	
//  3 motors, or triple motors	
	public TripleMotor getRightMotor()
	{
		return rightMotor;
	}
	
	public TripleMotor getLeftMotor()
	{
		return leftMotor;
	}
*/
	public void liftToScale()
	{
		int encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
		while (encoderPos < 27000 && RobotStatus.isAuto())
		{
			ActuatorConfig.getInstance().getLift().setSpeed(-.70);//40
			encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Elevator Encoder", encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
//		ActuatorConfig.getInstance().getLiftTalonTwo().set(ControlMode.MotionMagic, 27000);
		ActuatorConfig.getInstance().getLift().setSpeed(0);
	}
	public void liftToBottom()
	{
    	SmartDashboard.putBoolean("Bottom Limit Switch Lift", ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("Top Limit Switch Lift", ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isFwdLimitSwitchClosed());
		while (!ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isRevLimitSwitchClosed()
				// && RobotStatus.isAuto()
				)
		{
			ActuatorConfig.getInstance().getLift().setSpeed(.50);//.40, .70

			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		stopLift();
		//reset lift encoder value to 0
		ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().setQuadraturePosition(0, 0);
    	SmartDashboard.putBoolean("Bottom Limit Switch Lift", ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("Top Limit Switch Lift", ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isFwdLimitSwitchClosed());
	}
	public void liftToScaleAutonPhillip()
	{
//		liftToBottom();
		int encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
		double encoderStart = encoderPos;
//		double distance = 18500-encoderStart;
		double distance = 27000;
		double currentSpeed = 0.5;
		double currentPosition = 0;
//		while (encoderPos < 18500 /*&& RobotStatus.isAuto()*/)
		while (Math.abs(encoderPos - encoderStart) < distance //&& 
				//&& RobotStatus.isAuto() 
				&&
				!(ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isFwdLimitSwitchClosed())
				) 
		{
//			currentSpeed = liftSpeedPlan.getSpeed(distance, 0.7, encoderPos - encoderStart);
			currentSpeed = liftSpeedPlan.getSpeed(distance, 0.7, Math.abs(encoderPos - encoderStart));
			System.out.println("currentSpeed: " + currentSpeed);
			//ActuatorConfig.getInstance().getLift().setSpeed(-.70);//40
			// why set speed to negative value?  
//			ActuatorConfig.getInstance().getLift().setSpeed(-0.7);//40
//			ActuatorConfig.getInstance().getLift().setSpeed(-0.85);//40
			ActuatorConfig.getInstance().getLift().setSpeed(-currentSpeed);
			
			encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
//			SmartDashboard.putNumber("Elevator Encoder", encoderPos);
//			SmartDashboard.putNumber("encoderPos - encoderStart", encoderPos - encoderStart);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		stopLift();
		SmartDashboard.putNumber("Elevator Encoder", encoderPos);
//		ActuatorConfig.getInstance().getLiftTalonTwo().set(ControlMode.MotionMagic, 27000);
	}
	
	public void liftToSwitch()
	{
		int encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
		while (encoderPos < 9000 && RobotStatus.isAuto())
		{
			ActuatorConfig.getInstance().getLift().setSpeed(-.70);
			encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Elevator Encoder", encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		ActuatorConfig.getInstance().getLift().setSpeed(0);
	}
	
	public void liftTo(double distance)
	{
		int encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
		while (encoderPos < distance)
		{
			ActuatorConfig.getInstance().getLift().setSpeed(-.70);//40, 55
			encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Elevator Encoder", encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		ActuatorConfig.getInstance().getLift().setSpeed(0);
	}
	
	public void lowerLiftTo(double distance)
	{
		int encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
		while (encoderPos> distance)
		{
			ActuatorConfig.getInstance().getLift().setSpeed(.50);//40, 55
			encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Elevator Encoder", encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		ActuatorConfig.getInstance().getLift().setSpeed(0);
	}
	
	public void liftToScaleTeleop()
	{			ActuatorConfig.getInstance().getLift().setSpeed(-.70);//4
			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getLift().setSpeed(0);
	}
		/*
	}
		int encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
		while (encoderPos < 27000 && RobotStatus.isTeleop())
		{
			ActuatorConfig.getInstance().getLift().setSpeed(-.70);//40
			encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Elevator Encoder", encoderPos);
		}
//		ActuatorConfig.getInstance().getLiftTalonTwo().set(ControlMode.MotionMagic, 27000);
		ActuatorConfig.getInstance().getLift().setSpeed(0);
	}*/
	
	public void liftToSwitchTeleop()
	{
		int encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
		while (encoderPos < 10000 && RobotStatus.isTeleop())
		{
			ActuatorConfig.getInstance().getLift().setSpeed(-.60);
			encoderPos = ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Elevator Encoder", encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		ActuatorConfig.getInstance().getLift().setSpeed(0);
	}
	
	public void lowerAnglerSwitch()
	{
		int encoderPos = ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition();
		while (encoderPos > -1000 && RobotStatus.isAuto())
		{
			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-.40);
			encoderPos = ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Angler Encoder", encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
	}
	
	public void lowerAnglerScale()
	{
		int encoderPos = ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition();
//		while (encoderPos > -500 && RobotStatus.isAuto())
		while (encoderPos > -450 && RobotStatus.isAuto())
		{
			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-.40);
			encoderPos = ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition();
			//System.out.println(encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
	}
	
	public void lowerAnglerTo(double distance)
	{
		int encoderPos = ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition();
		System.out.println("encoderPos: " + encoderPos + "  distance: " + distance);
		while (encoderPos > -distance)
		{
			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-.40);
			encoderPos = ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Angler Encoder", encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
	}
	
	public void raiseAnglerTo(double distance)
	{
		int encoderPos = ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition();
		int startPos = encoderPos;
		//while (encoderPos < -distance)
		while (encoderPos < startPos-distance)
		{
			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(.40);
			encoderPos = ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Angler Encoder", encoderPos);
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
	}

	public void turnRightRadius(double speed, double angle, double radius, double width)
	{
		turnRadius( speed,  angle,  radius,  width);
	}
	
	public void turnLeftRadius(double speed, double angle, double radius, double width)
	{
		turnRadius( speed,  -angle,  radius,  width);
	}

	public void turnRadius(double speed, double angle, double radius, double width)
	{
		rightJoystick = new HBJoystick(0);
		leftJoystick = new HBJoystick(1);

		isSwitched = false;
		
		NavX navX = SensorConfig.getInstance().getNavX();
		navX.resetLastRawYaw();
		double InnerSpeed = speed*((radius-width/2)/(radius+width/2));
		double currentYaw = navX.getTrueYaw();
		double endAngle = currentYaw + angle;
		System.out.println("+------------------------------+");
		System.out.println("turnRadius: speed =" + speed + "; angle=" + angle);
		System.out.println("Start Angle: " + currentYaw);
		System.out.println("End Angle: " + endAngle);
		System.out.println("InnerSpeed: " + InnerSpeed);
//		if(angle > 0) // up right
//			ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, InnerSpeed);
//		else // up left
//			ActuatorConfig.getInstance().getDrivetrain().setSpeed(InnerSpeed, speed);
		
		// down right
		//  ActuatorConfig.getInstance().getDrivetrain().setSpeed(-InnerSpeed, -speed);
		// down left
		//	ActuatorConfig.getInstance().getDrivetrain().setSpeed(-speed, -InnerSpeed);

		//???		ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed/2, -speed/2);

double lastYaw = currentYaw;
double startYaw = currentYaw;
double currentSpeed = 0;
double turnedAngle = 0;
			if(Math.abs(angle) <= 1)
			{
				System.out.println("Hard to rotate such a small angle: " + angle);
				return;
			}
			while ((angle > 0 && currentYaw < endAngle)|| 
			(angle < 0 && currentYaw > endAngle))
			{
				currentYaw = navX.getTrueYaw();
				turnedAngle = Math.abs(currentYaw - startYaw);
				currentSpeed = turnRadiusSpeedPlan.getSpeed(Math.abs(angle), speed, turnedAngle);
				InnerSpeed = currentSpeed*((radius-width/2)/(radius+width/2));
				if(angle > 0) // up right
					ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, InnerSpeed);
				else 
					ActuatorConfig.getInstance().getDrivetrain().setSpeed(-currentSpeed, -InnerSpeed);

				//if (Math.abs(currentYaw - lastYaw) > 10) //NavX has a bug: Yaw values suddenly change signs
				//	currentYaw = -currentYaw;
				//if(lastYaw < currentYaw - 0.5 || lastYaw > currentYaw + 0.5)
				//	   System.out.println("current Angle: " + currentYaw);
				if (RobotStatus.isAuto() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
				{
					break;
				}
				if (RobotStatus.isTeleop() && turnRadiusCancel)
				{
					break;
				}
				lastYaw = currentYaw;

			}
//		}
		System.out.println("After turning: " + currentYaw);
		ActuatorConfig.getInstance().getDrivetrain().stop();
		currentYaw = navX.getTrueYaw();
		System.out.println("After stopped angle: " + currentYaw);
		System.out.println("--------------------");

	}
	
	public void turnRightRadiusCorrection(double speed, double angle, double radius, double width, boolean doCorrection, boolean toStop)
	{
		System.out.println("turn Forward Right");
		turnRadiusCorrection( speed,  TurnDirection.FORWARD_RIGHT, angle,  radius,  width, doCorrection, toStop);
	}
	
	public void turnLeftRadiusCorrection(double speed, double angle, double radius, double width, boolean doCorrection, boolean toStop)
	{
		System.out.println("turn Forward Left");
		turnRadiusCorrection( speed,  TurnDirection.FORWARD_LEFT,  -angle,  radius,  width, doCorrection, toStop);
	}

	public void turnBackRightRadiusCorrection(double speed, double angle, double radius, double width, boolean doCorrection, boolean toStop)
	{
		System.out.println("turn Backward Right");
		turnRadiusCorrection( speed,  TurnDirection.BACKWARD_RIGHT, -angle,  radius,  width, doCorrection, toStop);
	}
	
	public void turnBackLeftRadiusCorrection(double speed, double angle, double radius, double width, boolean doCorrection, boolean toStop)
	{
		System.out.println("turn Backward Left");
		turnRadiusCorrection( speed,  TurnDirection.BACKWARD_LEFT,  angle,  radius,  width, doCorrection, toStop);
	}

	public void turnRadiusCorrection(double speed, TurnDirection turnDirection, double angle, double radius, double width, boolean doCorrection, boolean toStop)
	{
		rightJoystick = new HBJoystick(0);
		leftJoystick = new HBJoystick(1);

		isSwitched = false;
		
		NavX navX = SensorConfig.getInstance().getNavX();
		navX.resetLastRawYaw();

//		NavXThread navXThread = SensorConfig.getInstance().getNavXThread();
//		navXThread.resetLastRawYaw();
		
		MoveCorrection moveCorrection = ActuatorConfig.getInstance().getDrivetrain().moveCorrection;

		double wheelDistance = ActuatorConfig.getInstance().getWheelDistance();
		double wheelDiameter = ActuatorConfig.getInstance().getWheelDiameter();
		// normally radius equals 0.0, so innerOuterRatio will be -1 (one side forward, the other side backward)
		double innerOuterRatio = (radius - wheelDistance / 2) / (radius + wheelDistance / 2);

//		double InnerSpeed = speed*((radius-width/2)/(radius+width/2));
		// here radius is turning radius, from turning pivot to robot center point
//		double InnerSpeed = speed*((radius-wheelDistance/2)/(radius+wheelDistance/2));
		double InnerSpeed = speed*innerOuterRatio;
//		double currentYaw = navX.getTrueYaw();
//		double currentYaw = navXThread.readZAngle();
		double currentYaw = navX.getZAngle();
		
		double endAngle = currentYaw + angle;
		
		boolean isRightComplete = false;
		boolean isLeftComplete = false;
		// must assign temporary values here, avoid compile error "may not have been initialized".
		double targetPositionRight = Math.PI * Math.toRadians(angle) * (wheelDistance / 2);
		double targetPositionLeft = Math.PI * Math.toRadians(angle) * (wheelDistance / 2);
		double rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
				.getQuadraturePosition() / (2048.0);// 8192 is the ppr of the
													// encoder x4
		double leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
				.getQuadraturePosition() / (-2048.0);
		double rightEncoderStartValue = rightEncoderValue;
		double leftEncoderStartValue = leftEncoderValue;
//outer, inner wheels, To do, need to make sure unit
		double outerArcLength = Math.PI * Math.toRadians(Math.abs(angle)) * (radius + wheelDistance / 2);
		double innerArcLength = Math.PI * Math.toRadians(Math.abs(angle)) * (radius - wheelDistance / 2);
		
		
		System.out.println("+------------------------------+");
		System.out.println(String.format("turnRadius: speed = %s  angle=%s", speed, angle));
		System.out.println("Start Angle: " + String.format("%.3f",currentYaw));
		System.out.println("End Angle: " + String.format("%.3f",endAngle));
		System.out.println("Left Encoder Start Value: " + String.format("%.3f",leftEncoderValue));
		System.out.println("Right Encoder Start Value: " + String.format("%.3f",rightEncoderValue));
		System.out.println("Inner to Outer wheel ratio: " + String.format("%.3f",innerOuterRatio));
		System.out.println("Outer Planed Encoder to move: " + String.format("%.3f",outerArcLength) + "inches");
		System.out.println("Inner Planed Encoder to move: " + String.format("%.3f",innerArcLength) + "inches");
		System.out.println("Outer Speed (setting speed): " + String.format("%.3f",speed));
		System.out.println("InnerSpeed: " + String.format("%.3f",InnerSpeed));
		System.out.println("Two sides wheels distance: " + String.format("%.3f",wheelDistance) + "inches");
		System.out.println("Turning (arc) radius: " + radius);

		// wrong, units are different
		// before unify units, we can only check ratio of left and right encoder values
		switch(turnDirection)
		{
			case FORWARD_LEFT:
				targetPositionLeft = leftEncoderValue + innerArcLength;
				targetPositionRight = rightEncoderValue + outerArcLength;
				break;
			case FORWARD_RIGHT:
				targetPositionLeft = leftEncoderValue + outerArcLength;
				targetPositionRight = rightEncoderValue + innerArcLength;
				break;
			case BACKWARD_LEFT:
				targetPositionLeft = leftEncoderValue - innerArcLength;
				targetPositionRight = rightEncoderValue - outerArcLength;
				break;
			case BACKWARD_RIGHT:
				targetPositionLeft = leftEncoderValue - innerArcLength;
				targetPositionRight = rightEncoderValue - outerArcLength;
				break;
			default:
				//error
				break;
		}
		
		System.out.println("Target Position Left: " + String.format("%.3f",targetPositionLeft));
		System.out.println("Target Position Right: " + String.format("%.3f",targetPositionRight));

		if(doCorrection)
		{
			endAngle += moveCorrection.getDeltaYaw();
			System.out.println("Adjusted End Angle: " + String.format("%.3f",endAngle));
			// ingore delta distance
			// ignore delta left and right distance caused by delta angle (yaw)
		}
//		if(angle > 0) // up right
//			ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, InnerSpeed);
//		else // up left
//			ActuatorConfig.getInstance().getDrivetrain().setSpeed(InnerSpeed, speed);
		
		// down right
		//  ActuatorConfig.getInstance().getDrivetrain().setSpeed(-InnerSpeed, -speed);
		// down left
		//	ActuatorConfig.getInstance().getDrivetrain().setSpeed(-speed, -InnerSpeed);

		//???		ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed/2, -speed/2);

double lastYaw = currentYaw;
double startYaw = currentYaw;
double currentSpeed = 0;
double turnedAngle = 0;
double leftEncoderPlanedValue=0, rightEncoderPlanedValue=0;
double leftEncoderMovedValue, rightEncoderMovedValue;
double movingDirection;
			if(Math.abs(angle) <= 1)
			{
				System.out.println("Hard to rotate such a small angle: " + String.format("%.3f",angle));
				return;
			}
			
			resetDriveTrainEncoderSamplings();
			double estimateTime = estimateTimeUsed(0, angle, speed);
			
			while ((angle > 0 && currentYaw < endAngle)|| 
			(angle < 0 && currentYaw > endAngle))
			{
//				currentYaw = navX.getTrueYaw();
//				currentYaw = navXThread.readZAngle();
				currentYaw = navX.getZAngle();
				turnedAngle = Math.abs(currentYaw - startYaw);

				if(Math.abs(turnedAngle) > Math.abs(angle) / 3)
				{
					robotVelocityXSamplings.addSample(navX.getVelocityX());
					leftEncoderSamplings.addSample(leftEncoderValue);
					rightEncoderSamplings.addSample(rightEncoderValue);
					if(robotBlockedByObstacle(estimateTime))
					{
						System.out.println("Robot is blocked by obstacle(s), quit turning.");
						break;
					}
				}

				currentSpeed = turnRadiusSpeedPlan.getSpeed(Math.abs(angle), speed, turnedAngle);
				// this ideal ratio of inner wheel speed and outer wheel speed. Here, speed is actually motor power.
				// it's necessary to check wheels encoders to see they really moved in same ratio.
//				InnerSpeed = currentSpeed*((radius-width/2)/(radius+width/2));
				InnerSpeed = currentSpeed*((radius-wheelDistance/2)/(radius+wheelDistance/2));
				
				// check turning curve is on track (arc) or not
//				leftEncoderPlanedValue = Math.PI * Math.toRadians(Math.abs(turnedAngle)) * (radius + wheelDistance / 2);
//				rightEncoderPlanedValue = Math.PI * Math.toRadians(Math.abs(turnedAngle)) * (radius - wheelDistance / 2);

				rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
						.getQuadraturePosition() / (2048.0);
				leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
						.getQuadraturePosition() / (-2048.0);

				double realInnerOuterRatio = innerOuterRatio; 
				double innerSpeedAdjust = 0;
				
				leftEncoderMovedValue = Math.abs(leftEncoderValue - leftEncoderStartValue);
				rightEncoderMovedValue = Math.abs(rightEncoderValue - rightEncoderStartValue);

				switch(turnDirection)
				{
					case FORWARD_LEFT:
						if(Math.abs(rightEncoderMovedValue) > 0.3)
							realInnerOuterRatio = leftEncoderMovedValue / rightEncoderMovedValue;
						else
							realInnerOuterRatio = innerOuterRatio;
						innerSpeedAdjust = -1 * (Math.abs(realInnerOuterRatio - innerOuterRatio));
						if(Math.abs(innerSpeedAdjust) > 0.3)
							innerSpeedAdjust = 0.3 * Math.signum(innerSpeedAdjust);
						ActuatorConfig.getInstance().getDrivetrain().setSpeed(InnerSpeed*(1+innerSpeedAdjust), currentSpeed);
						break;
					case FORWARD_RIGHT:
						if(Math.abs(leftEncoderMovedValue) > 0.3)
							realInnerOuterRatio = rightEncoderMovedValue / leftEncoderMovedValue;
						else
							realInnerOuterRatio = innerOuterRatio;
						innerSpeedAdjust = -1 * (Math.abs(realInnerOuterRatio - innerOuterRatio));
						if(Math.abs(innerSpeedAdjust) > 0.3)
							innerSpeedAdjust = 0.3 * Math.signum(innerSpeedAdjust);
						ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, InnerSpeed*(1+innerSpeedAdjust));
						break;
					case BACKWARD_LEFT:
						if(Math.abs(rightEncoderMovedValue) > 0.3)
							realInnerOuterRatio = leftEncoderMovedValue / rightEncoderMovedValue;
						else
							realInnerOuterRatio = innerOuterRatio;
						innerSpeedAdjust = -1 * (Math.abs(realInnerOuterRatio - innerOuterRatio));
						if(Math.abs(innerSpeedAdjust) > 0.3)
							innerSpeedAdjust = 0.3 * Math.signum(innerSpeedAdjust);
						ActuatorConfig.getInstance().getDrivetrain().setSpeed(-InnerSpeed*(1+innerSpeedAdjust), -currentSpeed);
						break;
					case BACKWARD_RIGHT:
						if(Math.abs(leftEncoderMovedValue) > 0.3)
							realInnerOuterRatio = rightEncoderMovedValue / leftEncoderMovedValue;
						else
							realInnerOuterRatio = innerOuterRatio;
						innerSpeedAdjust = -1 * (Math.abs(realInnerOuterRatio - innerOuterRatio));
						if(Math.abs(innerSpeedAdjust) > 0.3)
							innerSpeedAdjust = 0.3 * Math.signum(innerSpeedAdjust);
						ActuatorConfig.getInstance().getDrivetrain().setSpeed(-currentSpeed, -InnerSpeed*(1+innerSpeedAdjust));
						break;
					default:
						//error
						break;
				}
				
				//if (Math.abs(currentYaw - lastYaw) > 10) //NavX has a bug: Yaw values suddenly change signs
				//	currentYaw = -currentYaw;
				//if(lastYaw < currentYaw - 0.5 || lastYaw > currentYaw + 0.5)
				//	   System.out.println("current Angle: " + currentYaw);
				if (RobotStatus.isAuto() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
				{
					break;
				}
				if (RobotStatus.isTeleop() && turnRadiusCancel)
				{
					break;
				}
				lastYaw = currentYaw;

				// a little break
				try
				{
					TimeUnit.MILLISECONDS.sleep(10);
				}
				catch(Exception e)
				{
					
				}

			}
//		}
		rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
				.getQuadraturePosition() / (2048.0);
		leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
				.getQuadraturePosition() / (-2048.0);
		System.out.println("After turning: " + String.format("%.3f",currentYaw));
		System.out.println("Left Encoder Planed Value: " + String.format("%.3f",leftEncoderPlanedValue));
		System.out.println("Right Encoder Planed Value: " + String.format("%.3f",rightEncoderPlanedValue));
		System.out.println("Left Encoder End Value: " + String.format("%.3f",leftEncoderValue));
		System.out.println("Right Encoder End Value: " + String.format("%.3f",rightEncoderValue));
		System.out.println("Left Encoder Moved Value: " + String.format("%.3f",leftEncoderValue - leftEncoderStartValue));
		System.out.println("Right Encoder Moved Value: " + String.format("%.3f",rightEncoderValue- rightEncoderStartValue));
		if(toStop)
		{
			ActuatorConfig.getInstance().getDrivetrain().stop();
//			currentYaw = navX.getTrueYaw();
//			currentYaw = navXThread.readZAngle();
			currentYaw = navX.getZAngle();
			rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
					.getQuadraturePosition() / (2048.0);
			leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
					.getQuadraturePosition() / (-2048.0);
			System.out.println("After stopped angle: " + String.format("%.3f",currentYaw));
			System.out.println("Left Encoder Moved Value after stop: " + String.format("%.3f",leftEncoderValue - leftEncoderStartValue));
			System.out.println("Right Encoder Moved Value after stop: " + String.format("%.3f",rightEncoderValue- rightEncoderStartValue));

		}
		
		//if(doCorrection)
		//{
			double overAngle = currentYaw - endAngle;
			System.out.println("Over turned: " + String.format("%.3f",overAngle));
			moveCorrection.setDeltaYaw(-overAngle);
			// ingore over distance
		//}
		double movedX = navX.getDispacementX();
		double movedY = navX.getDispacementY();
		System.out.println("Moved: X=" + String.format("%.3f", movedX) + "; Y=" + String.format("%.3f", movedY));
		
		System.out.println("Over turned: " + String.format("%.3f",overAngle));

		System.out.println("Estimated Time: " + String.format("%.3f", estimateTime));
		System.out.println("Time used: " + String.format("%.3f", (double)((currentTimeMillis - startTimeMillis)/1000)));
	
		System.out.println("--------------------");
		

	}

	public void turnRight(double speed, double angle)
	{
		rightJoystick = new HBJoystick(0);
		leftJoystick = new HBJoystick(1);

		isSwitched = false;

		NavX navX = SensorConfig.getInstance().getNavX();

		float currentYaw = navX.getYaw();
		float endAngle = currentYaw + (float) angle;
	//	System.out.println("Start Angle: " + currentYaw);
	//	System.out.println("End Angle: " + endAngle);
		ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, -speed);


		if (endAngle > 360)
		{
			endAngle = endAngle - 360;
			System.out.println("Adjusted End Angle: " + endAngle);
			// Turn right until passing 0
			while ((currentYaw + angle) > 360)
			{
				currentYaw = navX.getYaw();
				if (RobotStatus.isTeleop() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
				{
					break;
				}
			}
			// Continue turning right until target is reached
			while (currentYaw < endAngle)
			{
				currentYaw = navX.getYaw();
				if (RobotStatus.isTeleop() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
				{
					break;
				}
			}
		} else
		{
			while (currentYaw < endAngle)
			{
				currentYaw = navX.getYaw();
				if (RobotStatus.isTeleop() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
				{
					break;
				}
			}
		}
		ActuatorConfig.getInstance().getDrivetrain().stop();
	}

	public void turnLeft(double speed, double angle)
	{
		rightJoystick = new HBJoystick(0);
		leftJoystick = new HBJoystick(1);

		isSwitched = false;

		NavX navX = SensorConfig.getInstance().getNavX();

		float currentYaw = navX.getYaw();
		float endAngle = currentYaw - (float) angle;
		System.out.println("Start Angle: " + currentYaw);
		System.out.println("End Angle: " + endAngle);
		ActuatorConfig.getInstance().getDrivetrain().setSpeed(-speed, speed);

		
		if (endAngle < 0)
		{
			endAngle = 360 + endAngle;
			System.out.println("Adjusted End Angle: " + endAngle);
			// Loop until we pass 0
			while ((currentYaw + angle) <= 360)
			{
				currentYaw = navX.getYaw();
				//System.out.println("Current Angle: " + currentYaw);
				if (RobotStatus.isTeleop() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
				{
					break;
				}
				try
				{
					TimeUnit.MILLISECONDS.sleep(10);
				}
				catch(Exception e)
				{
					
				}
				//SensorConfig.getInstance().getTimer().waitTimeInMillis(10);
			}
			// Now loop until we reach the target angle
			while (currentYaw > endAngle)
			{
				currentYaw = navX.getYaw();
				//System.out.println("Current Angle: " + currentYaw);
				if (RobotStatus.isTeleop() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
				{
					break;
				}
				try
				{
					TimeUnit.MILLISECONDS.sleep(10);
				}
				catch(Exception e)
				{
					
				}
				//SensorConfig.getInstance().getTimer().waitTimeInMillis(10);
			}
		} else
		{
			while (currentYaw > endAngle)
			{
				currentYaw = navX.getYaw();
				if (RobotStatus.isTeleop() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
				{
					break;
				}
				try
				{
					TimeUnit.MILLISECONDS.sleep(10);
				}
				catch(Exception e)
				{
					
				}
				//SensorConfig.getInstance().getTimer().waitTimeInMillis(10);
			}
		}
		ActuatorConfig.getInstance().getDrivetrain().stop();
	}


	private void move(double distance, double startSpeed, boolean isReversed)
	{
		if (RobotStatus.isTeleop() && (AutonStatus.getInstance().getStatus() == Status.CANCELED))
		{
			return;
		}

		double rightEncoderValue = Math
				.abs(ActuatorConfig.getInstance().getRightEncoder().getSensorCollection().getQuadraturePosition()
						* (0.000122)); //??? = 1/8192, should be 2028???
		double leftEncoderValue = Math
				.abs(ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection().getQuadraturePosition()
						* (-0.000122));

		double speed = 0;
		//??? here endYaw has another meaning, target yaw.
		// since move is heading straight, target yaw, startYaw are final endYaw are same.
		// ??? endYaw need to be initialized, otherwise, this value will be kept to next call of this function
		endYaw = SensorConfig.getInstance().getNavX().getRawYaw();
		startYaw = SensorConfig.getInstance().getNavX().getRawYaw();

		while (true)
		{
			rightEncoderValue = Math
					.abs(ActuatorConfig.getInstance().getRightEncoder().getSensorCollection().getQuadraturePosition()
							* (0.000122));
			leftEncoderValue = Math
					.abs(ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection().getQuadraturePosition()
							* (-0.000122));

			// System.out.println("Forwarding");

			if (RobotStatus.isTeleop() && (AutonStatus.getInstance().getStatus() == Status.CANCELED)) //???
			{
				break;
			}

			currentHeading = SensorConfig.getInstance().getNavX().getRawYaw();//??? assigned value, but not used later

			if (isReversed)
			{
				speed = Math.log(((leftEncoderValue + rightEncoderValue) / 2) + (distance));//???
			} else
			{
				speed = Math.log(-((leftEncoderValue + rightEncoderValue) / 2) + (distance));// distance
																								// +
																								// 1
			}

			if (speed < 0.10)//??? sign doesn't matter?
			{
				ActuatorConfig.getInstance().getDrivetrain().stop();
				System.err.println("Stopping Drivetrain");
				break;
			} else if (speed > 0.25)  //??? maximum speed is 0.25?
			{
				speed = 0.25;
			}

			if (isReversed)
			{
				speed = speed * -1;
			}

			startYaw = SensorConfig.getInstance().getNavX().getRawYaw();
			double leftCorrect = 0;
			double rightCorrect = 0;

			if (endYaw > (startYaw)) //??? endYaw is not initialized
			{

				// drivetrain.setSpeed((leftJoystick.getYAxis() / 2),
				// (rightJoystick.getYAxis() / 2) + 0.2);//Add Gyro
				System.out.println("Veering Right Telop");
				rightCorrect = 0.2;
			} else if (endYaw < (startYaw))
			{
				// drivetrain.setSpeed((leftJoystick.getYAxis() / 2) + 0.2,
				// (rightJoystick.getYAxis() / 2));//Add Gyro
				System.out.println("Veering Left Telop");
				leftCorrect = 0.2;
			} else
			{
				leftCorrect = 0;
				rightCorrect = 0;
			}

			// drivetrain.setSpeed((leftJoystick.getYAxis() / 2) + leftCorrect,
			// (rightJoystick.getYAxis() / 2) + rightCorrect);//Add Gyro
			ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed + leftCorrect, speed + rightCorrect);
			endYaw = SensorConfig.getInstance().getNavX().getRawYaw();
		}
	}

	/**
	 * Go forward number of rotations (1 Rot ~ 4pi ~ 1ft)
	 *
	 * @param distance
	 */
	public void goForward(double distance, double speed)
	{
		move(distance, speed, false);
	}

	public void goBackward(double distance, double speed)
	{
		move(distance, speed, true);
	}

	// public void moveGyro(double distance, double speed, boolean isReversed)
	// {
	// double startYaw = SensorConfig.getInstance().getNavX().getRawYaw();
	// double leftCorrect = 0;
	// double rightCorrect = 0;
	//
	// double deviationDistance =0 ;
	// double theta = 0;
	//
	// boolean isDone = false;
	//
	// while(RobotStatus.isAuto())
	// {
	//
	// theta = endYaw - startYaw;
	//
	// deviationDistance = Math.sin(Math.abs(theta));
	//
	// if(endYaw > (startYaw + .5))
	// {
	// System.out.println("Veering Right Auton");
	// leftCorrect =+ 0.09; //.1
	// rightCorrect =-0.05;
	// }
	// else if (endYaw < (startYaw - .5))
	// {
	// System.out.println("Veering Left Auton");
	// rightCorrect =+ 0.05; //.1
	// leftCorrect =- 0.09;
	// }
	// else
	// {
	// leftCorrect = 0;
	// rightCorrect = 0;
	// }
	//
	// setSpeed(speed + leftCorrect, speed + rightCorrect);
	// endYaw = SensorConfig.getInstance().getNavX().getRawYaw();
	//
	// SmartDashboard.putNumber("Statrt Yaw Tle", startYaw);
	// SmartDashboard.putNumber("End Yaw Tle", endYaw);
	// SmartDashboard.putNumber("Left Speed",
	// ActuatorConfig.getInstance().getDrivetrain().getLeftMotor().getSpeed());
	// SmartDashboard.putNumber("Right Speed",
	// ActuatorConfig.getInstance().getDrivetrain().getRightMotor().getSpeed());
	//
	// SmartDashboard.putNumber("Deviation Distance", deviationDistance);
	// SmartDashboard.putNumber("Theta", theta);
	// }
	// }

	public void moveGyro(double distance, double angle, double speed,  boolean isReversed)
	{
		NavX navx = SensorConfig.getInstance().getNavX();

		boolean isRightComplete = false;
		boolean isLeftComplete = false;
		double distanceRight;
		double distanceLeft;
		double rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
				.getQuadraturePosition() / (2048.0);// 8192 is the ppr of the
													// encoder x4
		double leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
				.getQuadraturePosition() / (-2048.0);
		double rightEncoderStartValue = rightEncoderValue;
		double leftEncoderStartValue = leftEncoderValue;
		
//		double deaccIdealDistance
//		double 
		
		System.out.println("Left Encoder Start Value: " + leftEncoderValue);
		System.out.println("Right Encoder Start Value: " + rightEncoderValue);

		if (isReversed)
		{
			speed = speed * -1;
			distanceRight = rightEncoderValue - distance;
			distanceLeft = leftEncoderValue - distance;
		} else
		{
			distanceRight = rightEncoderValue + distance;
			distanceLeft = leftEncoderValue + distance;
		}

		double currentYaw;
		double startYaw = navx.getRawYaw();
		double targetYaw = startYaw + angle;
		 SmartDashboard.putNumber("Start Yaw: ", startYaw);
		ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed);
		System.out.println("Start Yaw: " + startYaw);
		System.out.println("Target Yaw: " + targetYaw);
		// SmartDashboard.putNumber("Right Distance To ", distanceRight);
		// SmartDashboard.putNumber("Left Distance To ", distanceLeft);
		// SmartDashboard.putNumber("Start Left Enoder Value ",
		// leftEncoderValue);
		// SmartDashboard.putNumber("Start Right Encoder Value",
		// rightEncoderValue);

		//??? following condition let left and right stop to move at same time,
		//??? any of them complete, then the other one also stops moving.
		//??? we need both completes, i.e., in front of switch, let robot touch switch by whole front frame.
		double movedDistance = 0;
		double currentSpeed = 0;
		resetDriveTrainEncoderSamplings();
		double estimateTime = estimateTimeUsed(distance, 0, speed);

		//while ((!isRightComplete || !isLeftComplete) && RobotStatus.isAuto() && RobotStatus.isRunning())
		while (!isRightComplete && !isLeftComplete && RobotStatus.isAuto() && RobotStatus.isRunning())
		{

			rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
					.getQuadraturePosition() / (2048.0);
			leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
					.getQuadraturePosition() / (-2048.0);

//			SmartDashboard.putNumber("Left Encoder Value ", leftEncoderValue);
//			SmartDashboard.putNumber("Right Encoder Value", rightEncoderValue);

			movedDistance = Math.abs(rightEncoderValue - rightEncoderStartValue);
			if(Math.abs(movedDistance) > Math.abs(distance) / 3)
			{
				robotVelocityXSamplings.addSample(navx.getVelocityX());
				leftEncoderSamplings.addSample(leftEncoderValue);
				rightEncoderSamplings.addSample(rightEncoderValue);
				if(robotBlockedByObstacle(estimateTime))
				{
					System.out.println("Robot is blocked by obstacle(s), quit moving.");
					break;
				}
			}

			// used wrong speed plan during MARC event; low speed caused the robot to be stuck during auton
//			currentSpeed = turnRadiusSpeedPlan.getSpeed(Math.abs(distance), speed, movedDistance);
			currentSpeed = this.moveStraightSpeedPlan.getSpeed(Math.abs(distance), speed, movedDistance);
			if (isReversed)
			{
				currentSpeed = currentSpeed * -1;
			}
			// This Kill Switch will only work once Teleop begins and Joysticks
			// start working
			// This is a safety in case loop cannot complete for some reason
			// while running Auton.
			// To test: Run Auton and hit disable before lop completes. Then
			// start teleop and press kill switch buttons
			/*
			 * if(rightJoystick.getRawButton(1) || leftJoystick.getRawButton(1))
			 * { System.out.println("Kill Switch"); break; }
			 */

			if (isReversed)
			{
				
				if (rightEncoderValue <= distanceRight)
				{
					isRightComplete = true;
					// ActuatorConfig.getInstance().getDrivetrain().getLeftMotor().stop();
					
				}
				if (leftEncoderValue <= distanceLeft)
				{
					isLeftComplete = true;
					// ActuatorConfig.getInstance().getDrivetrain().getLeftMotor().stop();
				}
				currentYaw = navx.getRawYaw();
//				SmartDashboard.putNumber("Current Yaw ", currentYaw);
				if (currentYaw > (targetYaw + 1))
				{
					// Veering left, so slow down right
					// System.out.println("Veering left");
//					ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, (speed+ .15));
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, (speed + Math.abs(currentYaw - targetYaw)/30));
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, (currentSpeed + Math.abs(currentYaw - targetYaw)/30));
					ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, (currentSpeed + Math.abs(currentSpeed)/10));
				}
			 //???else if (currentYaw < (startYaw + 0.5))
				else if (currentYaw < (targetYaw - 1))
				{
					// Veering right, so slow down left
					// System.out.println("Veering right");
//					ActuatorConfig.getInstance().getDrivetrain().setSpeed((speed+ .15), speed);
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed((currentSpeed + Math.abs(currentYaw - targetYaw)/30), currentSpeed);
					ActuatorConfig.getInstance().getDrivetrain().setSpeed((currentSpeed + Math.abs(currentSpeed)/10), currentSpeed);
				}
				else 
					ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed);
			} else
			{
				if (rightEncoderValue >= distanceRight)
				{
					isRightComplete = true;
					// ActuatorConfig.getInstance().getDrivetrain().getLeftMotor().stop();
					//System.out.println("Left Finished First");
				}
				if (leftEncoderValue >= distanceLeft)
				{
					isLeftComplete = true;
					
				}
				currentYaw = navx.getRawYaw();
//				SmartDashboard.putNumber("Current Yaw ", currentYaw);
				if (currentYaw > (targetYaw + 1))
				{
					// Veering left, so slow down right
					// System.out.println("Veering left");

//					ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, (speed + .12));// 0.05,
																								// +.16
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, (currentSpeed + Math.abs(currentYaw - targetYaw)/30));
					ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, (currentSpeed + Math.abs(currentSpeed)/10));

				//???} else if (currentYaw < (startYaw + 1))
			    } else if (currentYaw < (targetYaw - 1))
				{
					// Veering right, so slow down left
					// System.out.println("Veering right");

//					ActuatorConfig.getInstance().getDrivetrain().setSpeed((speed + .12), speed);// 0.05,
																								// +.16
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed((currentSpeed + Math.abs(currentYaw - targetYaw)/30), currentSpeed);
					ActuatorConfig.getInstance().getDrivetrain().setSpeed((currentSpeed + Math.abs(currentSpeed)/10), currentSpeed);
				}
			    else
			    	ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, currentSpeed);
			}
		}
		rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
				.getQuadraturePosition() / (2048.0);
		leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
				.getQuadraturePosition() / (-2048.0);
		currentYaw = navx.getRawYaw();
		
		System.out.println("End Yaw: " + currentYaw);
		System.out.println("Left Encoder End Value: " + leftEncoderValue);
		System.out.println("Right Encoder End Value: " + rightEncoderValue);
		
		ActuatorConfig.getInstance().getDrivetrain().stop();
		
		rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
				.getQuadraturePosition() / (2048.0);
		leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
				.getQuadraturePosition() / (-2048.0);
		
	
		System.out.println("Left Encoder Moved Value: " + (leftEncoderValue - leftEncoderStartValue));
		System.out.println("Right Encoder Moved Value: " + (rightEncoderValue- rightEncoderStartValue));
		System.out.println("Estimated Time: " + String.format("%.3f", estimateTime));
		System.out.println("Time used: " + String.format("%.3f", (double)((currentTimeMillis - startTimeMillis)/1000)));

//		SensorConfig.getInstance().getTimer().waitTimeInMillis(200); //???
	}

	/**
	 * checks if the robot is blocked by (a) obstacle(s).
	 * case 1: suddenly completely stuck
	 * case 2: left or right side firstly stuck, the other side continue to move or rotate as a result
	 * case 3: overall robot stopped, but wheels on one or two sides still rotate, and cause robot keeping 
	 * little oscillating. And navx.isMoving() and navx.isRotating() might keep true.
	 * So we need observe actuator "last second's" consecutive movement steps, to judge it is stopped or not.
	 * @param ???
	*/
	public boolean robotBlockedByObstacle(double estimateTime)
	{
		NavX navx = SensorConfig.getInstance().getNavX();
		// this method doesn't work when robot has small oscillation
		if(!(navx.isMoving() || navx.isRotating()))
			return true;
		
		if(robotVelocityXSamplings.valuesOrDifferencesAreSmall(false))
		{
			System.out.println("No velocity.");
			return true;
		}
		
		if(leftEncoderSamplings.valuesOrDifferencesAreSmall(true) &&
		   rightEncoderSamplings.valuesOrDifferencesAreSmall(true)
		  )
		{
			System.out.println("Encoders not changing.");
			return true;
		}
		// if timeout
    	currentTimeMillis = System.currentTimeMillis();
    	estimateTimeUsedMillis = (long)(estimateTime * 1000);
    	if(currentTimeMillis > startTimeMillis + estimateTimeUsedMillis)
    	{
			System.out.println("Time out.");
    		//	return true;
    	}
		
		return false;
	}

	/**
	 * Estimates time to be used based on distance and speed.
	 * Currently, this is simplified version, ignores factors such as 
	 * speed ramps, turning radius, robot/chassis differences, etc
	 * @param ???
	*/
	public double estimateTimeUsed(double distance, double degree, double speed)
	{
		double timeSeconds = 1;
		
		// validation
		if((Math.abs(speed) < 0.01) && (Math.abs(degree) < 0.01))
		{
			// invalid value
			// To do: handle error
			return timeSeconds;
		}

		if((Math.abs(distance) > 0.5)  && (Math.abs(degree) < 0.01))
		{
			// forward or backward
			timeSeconds = 2 + distance / speed / 4;
		}
		else // turning
		{
			timeSeconds = 2 + degree / speed / 10;
		}
		
		return timeSeconds;
	}
	
	/**
	 * checks if encoder reading of robot is not increasing or decreasing as expected.
	 * @param ???
	*/
	public boolean motorStuck()
	{
//		if(!leftEncoderSamplings.valuesOrDifferencesAreSmall(true) ||
//		   !rightEncoderSamplings.valuesOrDifferencesAreSmall(true)
//		  )
//			return false;
		
		return true;
	}
	
	private long startTimeMillis;
	private long currentTimeMillis;
	private long estimateTimeUsedMillis;
	public void resetDriveTrainEncoderSamplings()
	{
		startTimeMillis = System.currentTimeMillis();
		// needs set to real value after reset
		estimateTimeUsedMillis = startTimeMillis + 2;
		
		robotVelocityXSamplings.reset();
		leftEncoderSamplings.reset();
		rightEncoderSamplings.reset();;
	}
	
	public void resetLiftEncoderSamplings()
	{
		liftEncoderSamplings.reset();
	}
	
	/**
	 * move 'straight' forward or backward with target corrections, a distance and/or angles.
	 * @param distance Planed moving distance
	 * @param anlge Deprecated. Correction angle to target. This value moved to DriveTrain's moveCorrection parameter
	 * @param speed Moving speed
	 * @param isReversed It is true when moving backward
	 * @param doCorrection If it is true, then moving target distance and angle(s) will be corrected according DriveTrain's moveCorrection parameter
	 * @param toStop If it is true, then run Stop() function after finished planed distance
	*/
	public void moveGyroCorrection(double distance, double angle, double speed,  boolean isReversed, boolean doCorrection, boolean toStop)
	{
		NavX navx = SensorConfig.getInstance().getNavX();
//		NavXThread navxThread = SensorConfig.getInstance().getNavXThread();

		boolean isRightComplete = false;
		boolean isLeftComplete = false;
		double targetPositionRight;
		double targetPositionLeft;
		double rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
				.getQuadraturePosition() / (2048.0);// 8192 is the ppr of the
													// encoder x4
		double leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
				.getQuadraturePosition() / (-2048.0);
		double rightEncoderStartValue = rightEncoderValue;
		double leftEncoderStartValue = leftEncoderValue;
		
		MoveCorrection moveCorrection = ActuatorConfig.getInstance().getDrivetrain().moveCorrection;
		
		System.out.println("Left Encoder Start Value: " + String.format("%.3f",leftEncoderValue));
		System.out.println("Right Encoder Start Value: " + String.format("%.3f",rightEncoderValue));

		if (isReversed)
		{
			speed = speed * -1;
			targetPositionLeft = leftEncoderValue - distance;
			targetPositionRight = rightEncoderValue - distance;
		} else
		{
			targetPositionLeft = leftEncoderValue + distance;
			targetPositionRight = rightEncoderValue + distance;
		}

		double currentYaw;
//		double startYaw = navx.getRawYaw();
//		double startYaw = navxThread.readZAngle();
		double startYaw = navx.getZAngle();
		double targetYaw = startYaw + angle;
		
		System.out.println("Target Position Left: " + String.format("%.3f",targetPositionLeft));
		System.out.println("Target Position Right: " + String.format("%.3f",targetPositionRight));
		System.out.println("Start Yaw: " + String.format("%.3f",startYaw));
		System.out.println("Target Yaw: " + String.format("%.3f",targetYaw));
		
		if(doCorrection)
		{
			System.out.println("To adjust position on both sides: " + String.format("%.3f",moveCorrection.getDeltaDistance()));
			System.out.println("To adjust yaw: " + String.format("%.3f",moveCorrection.getDeltaYaw()) + " degrees.");
			targetPositionLeft += moveCorrection.getDeltaDistance();
			targetPositionRight += moveCorrection.getDeltaDistance();
			targetYaw += moveCorrection.getDeltaDistance();

			System.out.println("Adjusted Target Position Left: " + String.format("%.3f",targetPositionLeft));
			System.out.println("Adjusted Target Position Right: " + String.format("%.3f",targetPositionRight));
			System.out.println("Adjusted Target Yaw: " + String.format("%.3f",targetYaw));
		}	
		
		 SmartDashboard.putNumber("Start Yaw: ", startYaw);
		ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed);
		// SmartDashboard.putNumber("Right Distance To ", distanceRight);
		// SmartDashboard.putNumber("Left Distance To ", distanceLeft);
		// SmartDashboard.putNumber("Start Left Enoder Value ",
		// leftEncoderValue);
		// SmartDashboard.putNumber("Start Right Encoder Value",
		// rightEncoderValue);

		//??? following condition let left and right stop to move at same time,
		//??? any of them complete, then the other one also stops moving.
		//??? we need both completes, i.e., in front of switch, let robot touch switch by whole front frame.
		double movedDistance = 0;
		double currentSpeed = 0;
		resetDriveTrainEncoderSamplings();
		double estimateTime = estimateTimeUsed(distance, 0, speed);
		//while ((!isRightComplete || !isLeftComplete) && RobotStatus.isAuto() && RobotStatus.isRunning())
		while (!isRightComplete && !isLeftComplete && RobotStatus.isAuto() && RobotStatus.isRunning())
		{

			rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
					.getQuadraturePosition() / (2048.0);
			leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
					.getQuadraturePosition() / (-2048.0);
			
			// add detection of blocked by obstacle (wall, fence, limit of an actuator movement, etc)
			// when 
//			SmartDashboard.putNumber("Left Encoder Value ", leftEncoderValue);
//			SmartDashboard.putNumber("Right Encoder Value", rightEncoderValue);

			movedDistance = Math.abs(rightEncoderValue - rightEncoderStartValue);
			
			if(Math.abs(movedDistance) > Math.abs(distance) / 3)
			{
				robotVelocityXSamplings.addSample(navx.getVelocityX());
				leftEncoderSamplings.addSample(leftEncoderValue);
				rightEncoderSamplings.addSample(rightEncoderValue);
				if(robotBlockedByObstacle(estimateTime))
				{
					System.out.println("Robot is blocked by obstacle(s), quit moving.");
					break;
				}
			}
					
			currentSpeed = turnRadiusSpeedPlan.getSpeed(Math.abs(distance), speed, movedDistance);
			if (isReversed)
			{
				currentSpeed = currentSpeed * -1;
			}
			// This Kill Switch will only work once Teleop begins and Joysticks
			// start working
			// This is a safety in case loop cannot complete for some reason
			// while running Auton.
			// To test: Run Auton and hit disable before lop completes. Then
			// start teleop and press kill switch buttons
			/*
			 * if(rightJoystick.getRawButton(1) || leftJoystick.getRawButton(1))
			 * { System.out.println("Kill Switch"); break; }
			 */

			if (isReversed)
			{
				
				if (rightEncoderValue <= targetPositionRight)
				{
					isRightComplete = true;
					// ActuatorConfig.getInstance().getDrivetrain().getLeftMotor().stop();
					
				}
				if (leftEncoderValue <= targetPositionLeft)
				{
					isLeftComplete = true;
					// ActuatorConfig.getInstance().getDrivetrain().getLeftMotor().stop();
				}
//				currentYaw = navx.getRawYaw();
//				currentYaw = navxThread.readZAngle();
				currentYaw = navx.getZAngle();
//				SmartDashboard.putNumber("Current Yaw ", currentYaw);
				if (currentYaw > (targetYaw + 1))
				{
					// Veering left, so slow down right
					// System.out.println("Veering left");
//					ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, (speed+ .15));
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, (speed + Math.abs(currentYaw - targetYaw)/30));
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, (currentSpeed + Math.abs(currentYaw - targetYaw)/30));
					ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, (currentSpeed + Math.abs(currentSpeed)/10));
				}
			 //???else if (currentYaw < (startYaw + 0.5))
				else if (currentYaw < (targetYaw - 1))
				{
					// Veering right, so slow down left
					// System.out.println("Veering right");
//					ActuatorConfig.getInstance().getDrivetrain().setSpeed((speed+ .15), speed);
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed((currentSpeed + Math.abs(currentYaw - targetYaw)/30), currentSpeed);
					ActuatorConfig.getInstance().getDrivetrain().setSpeed((currentSpeed + Math.abs(currentSpeed)/10), currentSpeed);
				}
				else 
					ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed);
			} else
			{
				if (rightEncoderValue >= targetPositionRight)
				{
					isRightComplete = true;
					// ActuatorConfig.getInstance().getDrivetrain().getLeftMotor().stop();
					//System.out.println("Left Finished First");
				}
				if (leftEncoderValue >= targetPositionLeft)
				{
					isLeftComplete = true;
					
				}
//				currentYaw = navx.getRawYaw();
//				currentYaw = navxThread.readZAngle();
				currentYaw = navx.getZAngle();
//				SmartDashboard.putNumber("Current Yaw ", currentYaw);
				if (currentYaw > (targetYaw + 1))
				{
					// Veering left, so slow down right
					// System.out.println("Veering left");

//					ActuatorConfig.getInstance().getDrivetrain().setSpeed(speed, (speed + .12));// 0.05,
																								// +.16
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, (currentSpeed + Math.abs(currentYaw - targetYaw)/30));
					ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, (currentSpeed + Math.abs(currentSpeed)/10));

				//???} else if (currentYaw < (startYaw + 1))
			    } else if (currentYaw < (targetYaw - 1))
				{
					// Veering right, so slow down left
					// System.out.println("Veering right");

//					ActuatorConfig.getInstance().getDrivetrain().setSpeed((speed + .12), speed);// 0.05,
																								// +.16
					//ActuatorConfig.getInstance().getDrivetrain().setSpeed((currentSpeed + Math.abs(currentYaw - targetYaw)/30), currentSpeed);
					ActuatorConfig.getInstance().getDrivetrain().setSpeed((currentSpeed + Math.abs(currentSpeed)/10), currentSpeed);
				}
			    else
//			    	ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed, currentSpeed);
			    	ActuatorConfig.getInstance().getDrivetrain().setSpeed(currentSpeed);
			}
			
			// a little break
			try
			{
				TimeUnit.MILLISECONDS.sleep(10);
			}
			catch(Exception e)
			{
				
			}

		}
		rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
				.getQuadraturePosition() / (2048.0);
		leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
				.getQuadraturePosition() / (-2048.0);
//		currentYaw = navx.getRawYaw();
//		currentYaw = navxThread.readZAngle();
		currentYaw = navx.getZAngle();
		
		System.out.println("End Yaw: " + String.format("%.3f",currentYaw));
		System.out.println("Left Encoder End Value: " + String.format("%.3f",leftEncoderValue));
		System.out.println("Right Encoder End Value: " + String.format("%.3f",rightEncoderValue));

		if(toStop)
		{
			ActuatorConfig.getInstance().getDrivetrain().stop();
		
			rightEncoderValue = ActuatorConfig.getInstance().getRightEncoder().getSensorCollection()
				.getQuadraturePosition() / (2048.0);
			leftEncoderValue = ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection()
				.getQuadraturePosition() / (-2048.0);
//			currentYaw = navx.getRawYaw();
//			currentYaw = navxThread.readZAngle();
			currentYaw = navx.getZAngle();

			System.out.println("Left Encoder Moved Value after stop: " + String.format("%.3f",leftEncoderValue - leftEncoderStartValue));
			System.out.println("Right Encoder Moved Value after stop: " + String.format("%.3f",rightEncoderValue- rightEncoderStartValue));
			System.out.println("End Yaw after stop: " + String.format("%.3f",currentYaw));
		}
		
		double overDistance = ((leftEncoderValue - targetPositionLeft) + (rightEncoderValue - targetPositionRight))/2;
		double overYaw = currentYaw - targetYaw;
		moveCorrection.setDeltaDistance(-overDistance);
		moveCorrection.setDeltaYaw(-overYaw);

		System.out.println("Over distance: " + String.format("%.3f",overDistance));
		System.out.println("Over yaw: " + String.format("%.3f",overYaw));

//		NavXThread navXThread = SensorConfig.getInstance().getNavXThread();

		double movedX = navx.getDispacementX();
		double movedY = navx.getDispacementY();
		double movedZ = navx.getDispacementZ();
		System.out.println("Moved: X=" + String.format("%.3f", movedX) + "; Y=" + String.format("%.3f", movedY) + "; Z=" + String.format("%.3f", movedZ));
//		System.out.println(navXThread.getOutputLog());

//		SensorConfig.getInstance().getTimer().waitTimeInMillis(200); //???
		System.out.println("Estimated Time: " + String.format("%.3f", estimateTime));
		System.out.println("Time used: " + String.format("%.3f", (double)((currentTimeMillis - startTimeMillis)/1000)));
		
	}

	public void movePid(double distance, double speed)
	{

		// double RtargetVelocity_UnitsPer100ms = -0.35 * 8192 * 500.0 / 600;
		// double LtargetVelocity_UnitsPer100ms = -0.35 * 8192 * 500.0 / 600;

		double finalDistance = ActuatorConfig.getInstance().getLeftTalonFront().getSelectedSensorPosition(0) + distance;
		//

		SmartDashboard.putNumber("Final distance", finalDistance);

		while (ActuatorConfig.getInstance().getLeftTalonFront().getSelectedSensorPosition(0) < finalDistance)
		{
			SmartDashboard.putNumber("Enc distance",
					ActuatorConfig.getInstance().getLeftTalonFront().getSelectedSensorPosition(0));
			// System.out.println(_Ltalon.getSelectedSensorPosition(0));
			/* Speed mode */
			/*
			 * 4096 Units/Rev * 500 RPM / 600 100ms/min in either direction:
			 * velocity setpoint is in units/100ms
			 */
			/* 1500 RPM in either direction */
			//
			// ActuatorConfig.getInstance().getRightTalonOne().set(ControlMode.Velocity,
			// RtargetVelocity_UnitsPer100ms);
			// ActuatorConfig.getInstance().getLeftTalonOne().set(ControlMode.Velocity,
			// LtargetVelocity_UnitsPer100ms);

			ActuatorConfig.getInstance().getRightTalonFront().set(ControlMode.PercentOutput, -speed);
			ActuatorConfig.getInstance().getLeftTalonFront().set(ControlMode.PercentOutput, speed);

			// ActuatorConfig.getInstance().getRightTalonOne().getSelectedSensorVelocity(ActuatorConfig.kPIDLoopIdx);
			// ActuatorConfig.getInstance().getLeftTalonOne().getSelectedSensorVelocity(ActuatorConfig.kPIDLoopIdx);
		}

		ActuatorConfig.getInstance().getRightTalonFront().set(ControlMode.PercentOutput, 0);
		ActuatorConfig.getInstance().getLeftTalonFront().set(ControlMode.PercentOutput, 0);

	}
	
	public void goForwardGyro(double distance, double angle, double speed )
	{
		System.out.println("Forward: d=" + distance + "; angle=" + angle + "speed=" + speed);
		moveGyro(distance, angle, speed, false);
		System.out.println("--------------------");
	
	}
	
	public void goBackwardsGyro(double distance, double angle, double speed)
	{ 
		System.out.println("BackwardsGyro: d=" + distance + "; angle=" + angle + "speed=" + speed);
		moveGyro(distance, angle, speed, true);
		System.out.println("--------------------");
	}

	public void goForwardGyroCorrection(double distance, double angle, double speed, boolean doCorrection, boolean toStop)
	{
		System.out.println("Forward: d=" + distance + "; angle=" + angle + "speed=" + speed);
		moveGyroCorrection(distance, angle, speed, false, doCorrection, toStop);
		System.out.println("--------------------");
	
	}
	
	public void goBackwardsGyroCorrection(double distance, double angle, double speed, boolean doCorrection, boolean toStop)
	{ 
		System.out.println("BackwardsGyro: d=" + distance + "; angle=" + angle + "speed=" + speed);
		moveGyroCorrection(distance, angle, speed, true, doCorrection, toStop);
		System.out.println("--------------------");
	}

	public void motionMagic(double distance)
	{
		ActuatorConfig.getInstance().getLeftTalonFront().setSelectedSensorPosition(0, 0, 10);
		ActuatorConfig.getInstance().getRightTalonFront().setSelectedSensorPosition(0, 0, 10);
		double targetPos = distance * 4096 * 4;// 4096 * 10.00
		ActuatorConfig.getInstance().getLeftTalonFront().set(ControlMode.MotionMagic, targetPos);
		ActuatorConfig.getInstance().getRightTalonFront().set(ControlMode.MotionMagic, -targetPos);

	}
}
