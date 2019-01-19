package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.actuators.Drivetrain;
import org.usfirst.frc.team3414.sensor.SensorConfig;
import org.usfirst.frc.team3414.sensor.NavX;


public class AutonUtility 
{ 
	
	public static void deliverSideSwitch(boolean turnRight)
	{
		if(turnRight == false) {
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
		    {
				ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
				ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(35);
		    }
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(24, 0.0, 0.35); //??0.4
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);

//			if ("RLR".equals(this.gameData)|| "RRR".equals(this.gameData))//Runs right auton for right switch 
			
				ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.2, 60, 0, 24 );
				SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			
				if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
				{
					ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
				}
				ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(3, 0.0, 0.30);//17, 9.748
				if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
				{
					ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
					ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
					SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
					ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
					ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
					ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(2, 0.0, .40);
					ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
				}
		}
		else{
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
        {
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(35);
        }
		
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(25, 0.0, 0.35); //??0.4
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);

//		if (("LRL".equals(this.gameData))|| ("LLL".equals(this.gameData)))//Runs left auton for left switch

			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 60, 0, 24 );
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			{
				ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			}
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(3, 0.0, 0.27);//17, 9.748
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			{
				ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
				ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
				SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
				ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
				ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
				ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(2, 0.0, .40);
				ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
			}
		}
			
			/*
		ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(30);
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(21, 0.0, .35);//22, .85
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(turnRight == true) {
			ActuatorConfig.getInstance().getDrivetrain().turnRight(.45, 43);//.38, 75
		}
		else 
		{
			ActuatorConfig.getInstance().getDrivetrain().turnLeft(.50, 45);
		}
		
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerSwitch();
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2.5, 0.0, .35);//3, .85
		//SensorConfig.getInstance().getTimer().waitTimeInMillis(1500);
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);//2500 for scale
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.35);//40
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.35);//40
		SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(2, 0.0, .45);
		ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200); */
	}
	
	public static void deliverSideScale(boolean turnRight)
	{
		ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(43,.0,.45);//45, 53
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(turnRight == true)
		{
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.25, 30, 0.0, 24);//.35, 88
		}
		else 
		{
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.25, 30, 0.0, 24);
		}
		SensorConfig.getInstance().getTimer().waitTimeInMillis(50);
		ActuatorConfig.getInstance().getDrivetrain().liftToScaleAutonPhillip();
		SensorConfig.getInstance().getTimer().waitTimeInMillis(50);
		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerScale();
		SensorConfig.getInstance().getTimer().waitTimeInMillis(200);
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.4);
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
		ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(100);
		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(1, 0.0, 0.5);
	}

	public static void autonStartActions()
	{
		NavX navX = SensorConfig.getInstance().getNavX();
		navX.reset();
		
		ActuatorConfig.getInstance().getDrivetrain().moveCorrection.reset();
		// pause a little bit, let robot wireless communication becomes stable
		// robot didn't moving after auton started. It happened in 2018 MARC off-season event
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		
		// to prevent cube dropping during robot moving, lower intake arms angle
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
        {
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(35);
        }
	}
	
	public static void autonCrossAutoLineStraight()
	{
		// regardless start position is left or right,
		// robot just needs to move straight forward and cross the auto line
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(24, 0.0, 0.40); //??0.4
	}
	
	public static void autonCrossAutoLineCurve(boolean rightSide)
	{
		// when robot start from Center position, it can't go forward to cross Auto Line. 
		// In fact, a pile of cubes rides on the center line.
		// make a curve and approach left/right side switch
		if(!rightSide) // people always say left, then right
		{
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2, 0.0, 0.40); //??0.4
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.2, 40, 0, 24 );//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(11, 0.0, 0.30);//17, 9.641ft
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 40, 0, 24 );
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			{
				ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			}
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(7, 0.0, 0.30);//17, 9.748
		}
		else
		{
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2, 0.0, 0.40); //??0.4
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 40, 0, 24 );//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//          distance 12 was wrong at MARC event, caused not-release-cube-to-switch problem -- caused last goForward couldn't finish -- blocked by fence.
//			supposed to be same as or shorter than 'Left Side's 11
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(12, 0.0, 0.35);//17, 9.641ft
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(11, 0.0, 0.30);//17, 9.641ft
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.2, 40, 0, 24 );
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
				ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
	        }
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.5);//17, 9.748
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(7, 0.0, 0.30);//17, 9.748
		}
	}

	public static void autonCenterSwitchDelivery(boolean rightSide)
	{
		// when robot start from Center position, it can't go forward to cross Auto Line. 
		// If fact, a pile of cubes rides on the center line.
		// make a curve and approach left/right side switch
		boolean doCorrection = true;
		if(!rightSide) // people always say left, then right
		{
			if(doCorrection)
			{
				ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(2, 0.0, 0.40, false, true); //??0.4
				ActuatorConfig.getInstance().getDrivetrain().turnLeftRadiusCorrection(0.2, 40, 0, 24, false, true );//.38, 75
				ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(11, 0.0, 0.35, false, true);//17, 9.641ft
				ActuatorConfig.getInstance().getDrivetrain().turnRightRadiusCorrection(0.2, 40, 0, 24, false, true);
			}
			else
			{
			
				ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2, 0.0, 0.40); //??0.4
				ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.2, 40, 0, 24 );//.38, 75
				ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(11, 0.0, 0.35);//17, 9.641ft
				ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 40, 0, 24);
			}
		}
		else
		{
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(2, 0.0, 0.40, false, false); //??0.4
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadiusCorrection(0.2, 40, 0, 24, false, false );//.38, 75
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(11, 0.0, 0.25, false, false);//17, 9.641ft
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadiusCorrection(0.3, 40, 0, 24, false, true);
		}

		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
		{
			ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
		}
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(7, 0.0, 0.30, false, true);//17, 9.748
		autonShootCubeToSwitch();
		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyroCorrection(3, 0.0, 0.35, false, true);
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
	}

	public static void autonShootCubeToSwitch()
	{
		if(ActuatorConfig.getInstance().getOnlyDriveTrain())
		{
			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			return;
		}
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
		SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
	}

	public static void autonSwitchNearSideDelivery(boolean turnRight)
	{
		autonCrossAutoLineStraight();
		
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(turnRight == true) // left side
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 60, 0, 24 );
		else
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.2, 60, 0, 24 );
			
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(3, 0.0, 0.27);//17, 9.748
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			autonShootCubeToSwitch();
				
		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(3, 0.0, .40);
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
	}

	public static void autonSwitchFarSideDelivery(boolean turnRight)
	{
		// go straight forward, prepare turn 90 degree 
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(32, 0.0, 0.40);//To do: ??? need to make sure

		//turn to path between scale and switch
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(turnRight == true) // left side
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 60, 0, 24 );
		else
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.2, 60, 0, 24 );
			
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
	return; //test 1, make sure position and turned angle are correct
		/*
		// go horizontally between switch and scale, avoid collide cubes beside fence of switch, platform also need to take attention
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(28, 0.0, 0.27);//??? need to make sure

		//turn to face switch
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(turnRight == true) // left side
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 60, 0, 24 );
		else
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.2, 60, 0, 24 );
			
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(3, 0.0, 0.27);//17, 9.748
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			autonShootCubeToSwitch();
				
		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(2, 0.0, .40);
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
*/
	}

	public static void autonScaleNearSideDelivery(boolean turnRight)
	{
//      ??? already lowered 35 degree at the beginning, 		
//		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);
//      ??? need to lower another 25 (60-35) degree?		
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(43,.0,.45);//45, 53
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(turnRight == true)
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.25, 30, 0.0, 24);//.35, 88
		
		else 
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.25, 30, 0.0, 24);
		SensorConfig.getInstance().getTimer().waitTimeInMillis(50);
		ActuatorConfig.getInstance().getDrivetrain().liftToScaleAutonPhillip();
		SensorConfig.getInstance().getTimer().waitTimeInMillis(200);
		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerScale();
		SensorConfig.getInstance().getTimer().waitTimeInMillis(150);
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.4);
		
		autonShootCubeToScale();

		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(1.5, 0.0, 0.5); 
		ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(100);
	}
	
	public static void autonShootCubeToScale()
	{
		if(ActuatorConfig.getInstance().getOnlyDriveTrain())
		{
			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			return;
		}
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
		SensorConfig.getInstance().getTimer().waitTimeInMillis(750);//500
		ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
		ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
	}

	public static void autonScaleFarSideDelivery(boolean turnRight)
	{
//      ??? already lowered 35 degree at the beginning, 		
//		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);
//      ??? need to lower another 25 (60-35) degree?		
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(32,.0,.40);//??? need make sure
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(turnRight == true)
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.25, 60, 0.0, 24);//.35, 88
		else 
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.25, 60, 0.0, 24);
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
return; //test 1, make sure position and turned angle are correct
/*
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(30,.0,.40);//??? need make sure
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if(turnRight == true)
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.25, 60, 0.0, 24);//.35, 88
		else 
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.25, 60, 0.0, 24);
		
		SensorConfig.getInstance().getTimer().waitTimeInMillis(50);
		ActuatorConfig.getInstance().getDrivetrain().liftToScaleAutonPhillip();
		SensorConfig.getInstance().getTimer().waitTimeInMillis(50);
		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerScale();
		SensorConfig.getInstance().getTimer().waitTimeInMillis(200);
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.4);
		
		autonShootCubeToScale();

		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(1, 0.0, 0.5); 
		ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(100);
*/
	}
	
	public static void autonInvalidGameData(String gameData) 
	{
		System.out.println("Only 4 situations: RRR, RLR, LRL, and LLL. ");
		System.out.println(gameData + " is not a valid game data value. ");
	}

	public static void autonPostionConflict(Position position, String autonName)
	{
		System.out.println("Position: " + position + " is not suitable to " + autonName);
	}

	public static void autonCenterTest(boolean rightSide)
	{
		// when robot start from Center position, it can't go forward to cross Auto Line. 
		// If fact, a pile of cubes rides on the center line.
		// make a curve and approach left/right side switch
		boolean doCorrection = true;
		if(!rightSide) // people always say left, then right
		{
			if(doCorrection)
			{
				ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(2, 0.0, 0.30, false, false); //??0.4
				ActuatorConfig.getInstance().getDrivetrain().turnLeftRadiusCorrection(0.3, 20, 0, 24, false, false );//.38, 75
				ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(10.5, 0.0, 0.25, false, false);//17, 9.641ft
				ActuatorConfig.getInstance().getDrivetrain().turnRightRadiusCorrection(0.3, 25, 0, 24, false, true);
				if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
				{
					ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
				}
				ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(4, 0.0, 0.30, true, true);//17, 9.748
				autonShootCubeToSwitch();
				ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(1, 0.0, 0.35);
				/////////////////// test start
				SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
				ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(5, 0.0, 0.35);
				
				
				ActuatorConfig.getInstance().getDrivetrain().turnRightRadiusCorrection(0.3, 90, 48, 24, true, false);
				SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
				ActuatorConfig.getInstance().getDrivetrain().turnBackRightRadiusCorrection(0.3, 90, 48, 24, true, false);
				/////////////////// test end
			}
			else
			{
			
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2, 0.0, 0.30); //??0.4
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.3, 40, 0, 24 );//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(11, 0.0, 0.25);//17, 9.641ft
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.3, 40, 0, 24 );
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
			{
				ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			}
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(8, 0.0, 0.30);//17, 9.748
			autonShootCubeToSwitch();
			}
			
			ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(1, 0.0, 0.35);
			
		}
		else
		{
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2, 0.0, 0.30); //??0.4
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.3, 40, 0, 24 );//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//          distance 12 was wrong at MARC event, caused not-release-cube-to-switch problem -- caused last goForward couldn't finish -- blocked by fence.
//			supposed to be same as or shorter than 'Left Side's 11
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(12, 0.0, 0.35);//17, 9.641ft
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(11, 0.0, 0.35);//17, 9.641ft
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.3, 40, 0, 24 );
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
				ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
	        }
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.5);//17, 9.748
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(8, 0.0, 0.30);//17, 9.748
			autonShootCubeToSwitch();
			ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(1, 0.0, 0.35);
		}
	}

	public static void autonTurnRadiusTest()
	{
//		ActuatorConfig.getInstance().getDrivetrain().goForwardGyroCorrection(24, 0.0, 0.35, false, true);//17, 9.641ft
		// test 
		if(true)
		{
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadiusCorrection(0.4, 90, 32, 24, false, true);//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().turnBackLeftRadiusCorrection(0.4, 90, 32, 24, false, true);
			SensorConfig.getInstance().getTimer().waitTimeInMillis(3000);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadiusCorrection(0.4, 90, 32, 24, false, true);//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().turnBackRightRadiusCorrection(0.4, 90, 32, 24, false, true);
			SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
		}
		else
		{
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadiusCorrection(0.4, 90, 32, 24, false, true);//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(3000);
			ActuatorConfig.getInstance().getDrivetrain().turnBackRightRadiusCorrection(0.4, 90, 32, 24, false, true);
			SensorConfig.getInstance().getTimer().waitTimeInMillis(3000);
		}
	}

}
