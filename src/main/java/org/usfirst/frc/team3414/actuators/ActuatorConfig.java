package org.usfirst.frc.team3414.actuators;

import org.usfirst.frc.team3414.robot.RobotStatus;
import org.usfirst.frc.team3414.actuators.Servo;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX; 
import org.usfirst.frc.team3414.actuators.LimitSwitchDigital;
import org.usfirst.frc.team3414.util.RobotChassis;
import org.usfirst.frc.team3414.util.RobotSelection;

public class ActuatorConfig 
{   
	private static ActuatorConfig instance;
	
	private TalonSRX talonLeftFront;
	private TalonSRX talonLeftBack;
	private TalonSRX talonRightFront;
	private TalonSRX talonRightBack;
// three motors
	private TalonSRX talonLeftTop;
	private TalonSRX talonRightTop;

	private TalonSRX talonIntakeOne;
	private TalonSRX talonIntakeTwo;
	
	private TalonSRX talonIntakeAngler;
	
	private TalonSRX talonLiftOne;
	private TalonSRX talonLiftTwo;
	
	private TalonSRX talonWingOne;
	private TalonSRX talonWingTwo;
	
	private Motor motorLeftFront;
	private Motor motorLeftBack;
	private Motor motorRightFront;
	private Motor motorRightBack;
	
	// for three motor drive train
	private Motor motorLeftTop;
	private Motor motorRightTop;
	
	private Motor motorIntakeOne;
	private Motor motorIntakeTwo;
	
	private Motor motorIntakeAngler;
	
	private Motor motorLiftOne;
	private Motor motorLiftTwo;
	
	private Motor motorWingOne;
	private Motor motorWingTwo;
	
	private Servo servoWingOne;
	private Servo servoWingTwo;
	
	private LimitSwitchDigital limitSwitchWings;
	private LimitSwitchDigital limitSwitchBottomLift;
	
//  2018 Power Up
	private DoubleMotor doubleMotorRight;
	private DoubleMotor doubleMotorLeft;

//  3 motors chassis
	private TripleMotor tripleMotorRight;
	private TripleMotor tripleMotorLeft;
	
	private DoubleMotor doubleMotorIntake;
 	private DoubleMotor doubleMotorLift;
 	private DoubleMotor doubleMotorWings;
	
	private Drivetrain drivetrain;
	
	public static final int kTimeoutMs = 10;
	public static final int kPIDLoopIdx = 0;
	
	public static final double RampTimeTeleop =  0.30;//0.25, 0.5
	public static final double RampTimeAuton =  1;//1
			
	public static final int RampTimeoutMs = 20000;

	// robot or chassis is one of 
	// ROBOT_POWERUP_ALPHA, ROBOT_POWERUP_BETA, CHASSIS_DOUBLE_MOTORS, CHASSIS_TRIPLE_MOTORS
//	public static final RobotChassis whichRobotOrChassis = RobotChassis.CHASSIS_DOUBLE_MOTORS;
	public static final RobotChassis whichRobotOrChassis = RobotChassis.ROBOT_POWERUP_ALPHA;
	// disable two wings
	private boolean wingDisabled = true;
	
	// drive train default uses DoubleMotor. If it uses TripleMotor, set following flag to true. 
	private boolean isTripleMotor = false;
	
	// for convenient, turn this flag to true when only use drive train.
	// Some test chassis only has drive train
	private boolean onlyDriveTrain = true;

	// navX docking horizontally (and heading to front ) or 
	// vertically (and heading to upward)
	private boolean navxHorizontal = true;
	private double wheelDiameter = 4; 
	private double wheelDistance = 19.25; // distance between left side wheels and right side wheels
	private double bumperLength = 36;
	private double bumperWidth = 28;

	
	public static ActuatorConfig getInstance()
	{
		if(instance == null)
		{
			instance = new ActuatorConfig();
		}
		
		return instance;
		
	}
	
	public void init()
	{   
		//if(RobotSelection.robotSelected == RobotChassis.CHASSIS_TRIPLE_MOTORS)

		switch (whichRobotOrChassis)
		{
			case ROBOT_POWERUP_ALPHA:
				wingDisabled = true;
				isTripleMotor = false;
				onlyDriveTrain = false;//should be false in competitions
				navxHorizontal = false;
				wheelDiameter = 6.25;
				// To do: make sure following measures, unit is inch
				wheelDistance = 24.25;
				bumperLength = 37.75;
				bumperWidth = 33;
				break;
				
			case ROBOT_POWERUP_BETA:
				wingDisabled = true;
				isTripleMotor = false;
				onlyDriveTrain = false;
				navxHorizontal = false;
				wheelDiameter = 6.26;
				// To do: make sure following measures, unit is inch
				wheelDistance = 24.25;
				bumperLength = 37.75;
				bumperWidth = 33;
				break;
		
			case CHASSIS_DOUBLE_MOTORS:
				wingDisabled = true;
				isTripleMotor = false;
				onlyDriveTrain = true;
				navxHorizontal = true;
				wheelDiameter = 4;
				wheelDistance = 19.25;
				bumperLength = 36;
				bumperWidth = 28;
				break;
				
			case CHASSIS_TRIPLE_MOTORS:
				wingDisabled = true;
				isTripleMotor = true;
				onlyDriveTrain = true;
				navxHorizontal = true;
				wheelDiameter = 4;
				// To do: make sure following measures, unit is inch
				wheelDistance = 19.25;
				bumperLength = 36;
				bumperWidth = 28;
				break;
			default:
				System.out.println("Please set type of robot or chassis.");
				break;
		}
		
		//talons mapping
		switch (whichRobotOrChassis)
		{
			case ROBOT_POWERUP_ALPHA:
			case ROBOT_POWERUP_BETA:
				talonLeftFront = new TalonSRX(1);//3  1   3
				talonLeftBack = new TalonSRX(0);//4  2   2
				talonRightFront = new TalonSRX(3);//1  3   4
				talonRightBack = new TalonSRX(2);//0  4   1

				// following are actuators other than drive train
				talonIntakeOne = new TalonSRX(7);
				talonIntakeTwo = new TalonSRX(8);
				
				talonIntakeAngler = new TalonSRX(6);
				
				talonLiftOne = new TalonSRX(9);
				talonLiftTwo = new TalonSRX(10);
				
				if(!wingDisabled)
				{
					talonWingOne = new TalonSRX(4);
					talonWingTwo = new TalonSRX(5);
								
					servoWingOne = new Servo(0);
					servoWingTwo = new Servo(1);
				
					limitSwitchWings = new LimitSwitchDigital(0, false);
				}
				limitSwitchBottomLift = new LimitSwitchDigital(1, false);
				break;

			case CHASSIS_DOUBLE_MOTORS:
                // 2 motors chassis
		        // to mapping motors' id, please using IE to open Roborio's aetting page,
				// http://10.34.14.2/#Home, 
				// under 'CAN Interface', you can find list of motors, check each one to 
				// get its ID.
				talonLeftFront = new TalonSRX(1);
				talonLeftBack = new TalonSRX(2);
				talonRightFront = new TalonSRX(3);
				talonRightBack = new TalonSRX(4);
				break;

			case CHASSIS_TRIPLE_MOTORS:
				// 3 motors chassis
				talonLeftFront = new TalonSRX(3);   // 3A master
				talonLeftBack = new TalonSRX(5);    // 1A follower
				if(isTripleMotor)
					talonLeftTop = new TalonSRX(4); // 2A follower
				
				talonRightFront = new TalonSRX(0);  // 2B master   we call it front since it has encoder (sensor)
				talonRightBack = new TalonSRX(2);   // 3B follower
				if(isTripleMotor)
					talonRightTop = new TalonSRX(1);// 1B follower
				break;
		
			default:
				System.out.println("Please set type of robot or chassis.");
				break;
				
		}

		
		//motors
		motorLeftFront = new Motor(talonLeftFront);
	    motorLeftBack = new Motor(talonLeftBack);
		motorRightFront = new Motor(talonRightFront);
		motorRightBack = new Motor(talonRightBack);
        // triple motors
		if(isTripleMotor)
		{
			motorLeftTop = new Motor(talonLeftTop);
			motorRightTop = new Motor(talonRightTop);
		}
		
		if(!onlyDriveTrain)
		{
			motorIntakeOne = new Motor(talonIntakeOne);
			motorIntakeTwo = new Motor(talonIntakeTwo);
		
			motorIntakeAngler = new Motor(talonIntakeAngler);
		
			motorLiftOne = new Motor(talonLiftOne);
			motorLiftTwo = new Motor(talonLiftTwo);

			if(!wingDisabled)
			{
				motorWingOne = new Motor(talonWingOne);
				motorWingTwo = new Motor(talonWingTwo);
			
//				motorWingTwo.setMotorReveresed(true);
			}
			motorIntakeTwo.setMotorReversed(true);
		
//			motorLiftTwo.setMotorReveresed(true);	
		}
		
		// reverse motor, if necessary
		switch (whichRobotOrChassis)
		{
			case ROBOT_POWERUP_ALPHA:
			case ROBOT_POWERUP_BETA:
				doubleMotorLeft = new DoubleMotor(motorLeftFront, motorLeftBack);
				doubleMotorRight = new DoubleMotor(motorRightFront, motorRightBack);
				doubleMotorRight.setMotorReversed(true); 
				break;

			case CHASSIS_DOUBLE_MOTORS:
				doubleMotorLeft = new DoubleMotor(motorLeftFront, motorLeftBack);
				doubleMotorRight = new DoubleMotor(motorRightFront, motorRightBack);
//???				
				doubleMotorLeft.setMotorReversed(false); 
				doubleMotorRight.setMotorReversed(true); 
				break;

			case CHASSIS_TRIPLE_MOTORS:
				//triple motors for drive train
				tripleMotorLeft = new TripleMotor(motorLeftFront, motorLeftBack, motorLeftTop);
				tripleMotorRight = new TripleMotor(motorRightTop, motorRightBack, motorRightFront);

				//tripleMotorLeft.setMotorReversed(true); //??? need test
				//tripleMotorRight.setMotorReversed(true);

				break;
		
			default:
				System.out.println("Please set type of robot or chassis.");
				break;
				
		}
		
		if(!onlyDriveTrain)
		{
		doubleMotorLift = new DoubleMotor(motorLiftOne, motorLiftTwo);
		if(!wingDisabled)
			doubleMotorWings = new DoubleMotor(motorWingOne, motorWingTwo);
		}		
		//talon configs
		talonRightFront.setSensorPhase(true);
		talonLeftFront.setSensorPhase(true);
		
		talonLeftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
		talonRightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
		if(!onlyDriveTrain)
		{
		talonIntakeAngler.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
		
		talonLiftTwo.setNeutralMode(NeutralMode.Brake);
		if(!wingDisabled)
		{
		servoWingOne.disengage();
		servoWingTwo.engage();
		}
		}		
		//current limit - DISABLED POST LINCOLN
//		talonLeftFront.configContinuousCurrentLimit(20, 0);
//		talonLeftBack.configContinuousCurrentLimit(20, 0);
//		talonRightFront.configContinuousCurrentLimit(20, 0);
//		talonRightBack.configContinuousCurrentLimit(20, 0);
		
		switch (whichRobotOrChassis)
		{
			case ROBOT_POWERUP_ALPHA:
			case ROBOT_POWERUP_BETA:
				talonLeftFront.enableCurrentLimit(true);
				talonLeftBack.enableCurrentLimit(true);
				talonRightFront.enableCurrentLimit(true);
				talonRightBack.enableCurrentLimit(true);
				break;

			case CHASSIS_DOUBLE_MOTORS:
		// ??? these will cause CHASSIS_DOUBLE_MOTORS hardly to move
				//talonLeftFront.enableCurrentLimit(true);
				//talonLeftBack.enableCurrentLimit(true);
				//talonRightFront.enableCurrentLimit(true);
				//talonRightBack.enableCurrentLimit(true);
				break;

			case CHASSIS_TRIPLE_MOTORS:
				break;
		
			default:
				System.out.println("Please set type of robot or chassis.");
				break;
				
		}

		if(!onlyDriveTrain)
		{
		//limit switch stuff
 		talonLiftTwo.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
		talonLiftTwo.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
 		talonLiftTwo.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
 		talonLiftTwo.overrideLimitSwitchesEnable(false);
 		talonLiftTwo.getSensorCollection().isRevLimitSwitchClosed();
 		
		talonIntakeAngler.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		talonIntakeAngler.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		
		talonIntakeAngler.overrideSoftLimitsEnable(true);
		talonIntakeAngler.overrideLimitSwitchesEnable(true);
 	 		
 		if(talonLiftTwo.getSensorCollection().isRevLimitSwitchClosed())
 		{
 			talonLiftTwo.getSensorCollection().setQuadraturePosition(0, 0);
 		}
 		
// 		if(talonIntakeAngler.getSensorCollection().isFwdLimitSwitchClosed())
// 		{
// 			talonIntakeAngler.getSensorCollection().setQuadraturePosition(0, 0);
// 		}


 		
 		
 		//ramp rates lift and angler
 		talonLiftTwo.configOpenloopRamp(0.65, 0);
 		talonIntakeAngler.configOpenloopRamp(.85, 0);
 		
		if(!wingDisabled)
		{
 		//wings servos
 		servoWingOne.disengage();
 		servoWingTwo.engage();
 		servoWingTwo.set(130);
		}
 		
 		//motion magic lift
 				// Set relevant frame periods to be at least as fast as periodic rate
 				talonLiftTwo.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
 				talonLiftTwo.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);
 				// set the peak and nominal outputs 
 				talonLiftTwo.configNominalOutputForward(0, kTimeoutMs);
 				talonLiftTwo.configNominalOutputReverse(0, kTimeoutMs);
 				talonLiftTwo.configPeakOutputForward(1, kTimeoutMs);
 				talonLiftTwo.configPeakOutputReverse(-1, kTimeoutMs);
 				// set closed loop gains in slot0 - see documentation 
 				talonLiftTwo.selectProfileSlot(0, kPIDLoopIdx);
 				talonLiftTwo.config_kF(kPIDLoopIdx, 0.93084622, kTimeoutMs);//0.2481
 				talonLiftTwo.config_kP(kPIDLoopIdx, 0, kTimeoutMs);
 				talonLiftTwo.config_kI(kPIDLoopIdx, 0, kTimeoutMs);
 				talonLiftTwo.config_kD(kPIDLoopIdx, 0, kTimeoutMs);
 				// set acceleration and vcruise velocity - see documentation 
 				talonLiftTwo.configMotionCruiseVelocity(824, kTimeoutMs);
 				talonLiftTwo.configMotionAcceleration(824, kTimeoutMs);//2725
 				// zero the sensor 
 				talonLiftTwo.setSelectedSensorPosition(0, 0, 0);
		}
 		
 		
 		//drive motion magic pid config
		if(!onlyDriveTrain)
		{
			System.out.println("PIDing-----------------------------------------------------------------------------");
			talonLeftFront.configOpenloopRamp(RampTimeAuton,RampTimeoutMs);
			talonRightFront.configOpenloopRamp(RampTimeAuton, RampTimeoutMs);
			
			talonRightFront.configNominalOutputForward(0, kTimeoutMs);
			talonRightFront.configNominalOutputReverse(0, kTimeoutMs);
			talonRightFront.configPeakOutputForward(1, kTimeoutMs);
			talonRightFront.configPeakOutputReverse(-1, kTimeoutMs);
			
			talonLeftFront.configNominalOutputForward(0, kTimeoutMs);
			talonLeftFront.configNominalOutputReverse(0, kTimeoutMs);
			talonLeftFront.configPeakOutputForward(1, kTimeoutMs);
			talonLeftFront.configPeakOutputReverse(-1, kTimeoutMs);
		}
 			switch (whichRobotOrChassis)
 			{
 				case ROBOT_POWERUP_ALPHA:
 					// Alpha numbers for PID
 					talonRightFront.config_kF(kPIDLoopIdx, 0.109, kTimeoutMs);//0.09053
 					talonRightFront.config_kP(kPIDLoopIdx, 0, kTimeoutMs);
 					talonRightFront.config_kI(kPIDLoopIdx, 0, kTimeoutMs);
 					talonRightFront.config_kD(kPIDLoopIdx, 0, kTimeoutMs);
 					
 					talonLeftFront.config_kF(kPIDLoopIdx, 0.0999, kTimeoutMs);//3.9346
 					talonLeftFront.config_kP(kPIDLoopIdx, 0, kTimeoutMs);
 					talonLeftFront.config_kI(kPIDLoopIdx, 0, kTimeoutMs);
 					talonLeftFront.config_kD(kPIDLoopIdx, 0, kTimeoutMs);
 					break;
 				case ROBOT_POWERUP_BETA:
 					// Beta numbers for PID
 					talonRightFront.config_kF(kPIDLoopIdx, 0.10774092, kTimeoutMs);//0.09053
 					talonRightFront.config_kP(kPIDLoopIdx, 0, kTimeoutMs);
 					talonRightFront.config_kI(kPIDLoopIdx, 0, kTimeoutMs);
 					talonRightFront.config_kD(kPIDLoopIdx, 0, kTimeoutMs);
 					
 					talonLeftFront.config_kF(kPIDLoopIdx, 0.1094703, kTimeoutMs);//3.9346
 					talonLeftFront.config_kP(kPIDLoopIdx, 0, kTimeoutMs);
 					talonLeftFront.config_kI(kPIDLoopIdx, 0, kTimeoutMs);
 					talonLeftFront.config_kD(kPIDLoopIdx, 0, kTimeoutMs);
 					
 					break;

 				case CHASSIS_DOUBLE_MOTORS: //???
 					talonRightFront.config_kF(kPIDLoopIdx, 0.109, kTimeoutMs);//0.09053
 					talonRightFront.config_kP(kPIDLoopIdx, 0, kTimeoutMs);
 					talonRightFront.config_kI(kPIDLoopIdx, 0, kTimeoutMs);
 					talonRightFront.config_kD(kPIDLoopIdx, 0, kTimeoutMs);
 					
 					talonLeftFront.config_kF(kPIDLoopIdx, 0.0999, kTimeoutMs);//3.9346
 					talonLeftFront.config_kP(kPIDLoopIdx, 0, kTimeoutMs);
 					talonLeftFront.config_kI(kPIDLoopIdx, 0, kTimeoutMs);
 					talonLeftFront.config_kD(kPIDLoopIdx, 0, kTimeoutMs);

 					break;

 				case CHASSIS_TRIPLE_MOTORS://???
 					break;
		 			
 				default:
 					System.out.println("Please set type of robot or chassis. drive motion magic pid config");
 			}
			
			talonLeftFront.configMotionCruiseVelocity(7009, kTimeoutMs);
			talonLeftFront.configMotionAcceleration(7009, kTimeoutMs);
			talonRightFront.configMotionCruiseVelocity(7121, kTimeoutMs);
			talonRightFront.configMotionAcceleration(7121, kTimeoutMs);		
 		
 	
		drivetrain = new Drivetrain(doubleMotorRight, doubleMotorLeft);
		
//		drivetrain.moveStraightSpeedPlan.init(0.25, 0.4, 0.15, 30, 20);
		drivetrain.moveStraightSpeedPlan.init(0.27, 0.4, 0.15, 30, 17);
		drivetrain.turnRadiusSpeedPlan.init(0.2, 0.3, 0.15, 40, 10);
		drivetrain.liftSpeedPlan.init(0.6, 0.85, 0.2, 40, 40);

        // triple motors
		//drivetrain = new Drivetrain(tripleMotorRight, tripleMotorLeft);
	}
	
	public TalonSRX getRightTalonFront()
	{
		return talonRightFront;
	}
	
	public TalonSRX getLeftTalonFront()
	{
		return talonLeftFront;
	}
	
	public TalonSRX getRightTalonBack()
	{
		return talonRightBack;
	}
	
	public TalonSRX getLeftTalonBack()
	{
		return talonLeftBack;
	}
	
	public Motor getRightMotorFront()
	{
		return motorRightFront;
	}
	
	public Motor getRightMotorBack()
	{
		return motorRightBack;
	}
	
	public Motor getLeftMotorFront()
	{
		return motorLeftFront;
	}
	public Motor getLeftMotorBack()
	{
		return motorLeftBack;
	}
	
	public TalonSRX getRightEncoder()
	{
		return talonRightFront;//Figure out which talons to use for encoders
	}
	
	public TalonSRX getLeftEncoder()
	{
		return talonLeftFront; //Figure out which talons to use for encoders
	}
	
	public Drivetrain getDrivetrain()
	{
		return drivetrain;
	}
	
	public DoubleMotor getLift()
	{
		return doubleMotorLift;
	}
	
	public TalonSRX getLiftTalonTwo()
	{
		return talonLiftTwo;
	}
	
	public Motor getMotorIntakeOne()
	{
		return motorIntakeOne;
	}
	
	public Motor getMotorIntakeTwo()
	{
		return motorIntakeTwo;
	}
	
	public Servo getServoWingOne()
	{
		return servoWingOne;
	}
	
	public Servo getServoWingTwo()
	{
		return servoWingTwo;
	}
	
	public DoubleMotor getDoubleMotorWings()
	{
		return doubleMotorWings;
	}
	
	public Motor getMotorIntakeAngler()
	{
		return motorIntakeAngler;
	}
	
	public TalonSRX talonIntakeAngler()
	{
		return talonIntakeAngler;
	}
	
	public LimitSwitchDigital limitSwitchBottomLift()
	{
		return limitSwitchBottomLift;
	}
	
	public LimitSwitchDigital limitSwitchWings()
	{
		return limitSwitchWings;
	}
	
	public boolean getWingDisabled()
	{
		return wingDisabled;
	}

	public boolean getIsTripleMotor()
	{
		return isTripleMotor;
	}
	
	public boolean getOnlyDriveTrain()
	{
		return onlyDriveTrain;
	}

	public double getWheelDiameter()
	{
		return wheelDiameter;
	}
	
	public double getWheelDistance()
	{
		return wheelDistance;
	}
	
	public double getBumperLength()
	{
		return bumperLength;
	}
	
	public double getBumperWidth()
	{
		return bumperWidth;
	}

}
	
