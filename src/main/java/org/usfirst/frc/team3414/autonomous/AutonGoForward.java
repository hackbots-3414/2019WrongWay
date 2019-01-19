package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.sensor.SensorConfig;

public class AutonGoForward extends AutonBase
{
	public AutonGoForward()
	{
		super();
	}

	protected void left() //Goes forward
	{
		SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
        {
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(35);
        }
		
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(24, 0.0, 0.35); //??0.4
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);

		if (("LRL".equals(this.gameData))|| ("LLL".equals(this.gameData)))//Runs left auton for left switch
		{
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
				SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
				ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
				ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
				ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(2, 0.0, .40);
				ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
			}
		}
		
		System.out.println("Position left: Goes forward");

		/// testing
//		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(14/2, 0.0, 0.25); //original d=14, speed= 0.3  ??? scale down distance in test mode
//		SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
//		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(14/2, 0.0, 0.25); //speed d=14, speed= 0.3
//		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(14/2, -10.0, 0.2); //original d=14, speed= 0.3  ??? scale down distance in test mode
//		SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
//		ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(14/2.0, 10.0, 0.2); //speed d=14, speed= 0.3
//		SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);

//??? testing start
//		SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(3);

//		SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
//	ActuatorConfig.getInstance().getDrivetrain().turnRight(.45/4, 45);//.38, 75
//		SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
//	ActuatorConfig.getInstance().getDrivetrain().turnLeft(.45/4, 45);
//		SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
//		ActuatorConfig.getInstance().getDrivetrain().turnLeft(.50, 45);
		//??? testing end
//		ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(.45/2, 45, 48, 24 );//.38, 75
	}

	protected void center() //Has a fail safe so if chosen in game, it'll run center auton.
	{
		SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
		if (("LRL".equals(this.gameData))|| ("LLL".equals(this.gameData)))//Runs basic center auton for left switch
		{
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
				ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
				ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);//60
			}
/*			// MARC event
			//ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.2); //??0.4
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(.45/2, 90, 36, 24 );//.38, 75
			//???		ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.3, 25);
			//ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.2, 85);
			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			//???			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(6, 0.0, 0.4);//17, 9.641ft
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(6/2, 0.0, 0.2);//17, 9.641ft
			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 90, 36, 24 );
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			//ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			//ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.5);//17, 9.748
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.2);//17, 9.748
*/			
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
				ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(35);
	        }
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2, 0.0, 0.30); //??0.4
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.2, 40, 0, 24 );//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			//???		ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.3, 25);
			//ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.2, 85);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
//???			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(6, 0.0, 0.4);//17, 9.641ft14
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(11, 0.0, 0.25);//17, 9.641ft
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 40, 0, 24 );
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
				ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
	        }
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.5);//17, 9.748
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(8, 0.0, 0.25);//17, 9.748
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
				ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
				ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
				SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
				ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
				ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
				ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(2, 0.0, .40);
				ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
	        }
			System.out.println("Position center: Goes forward");
			
			//testing
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(.25);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(.45, 90);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(1);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(.45, 90);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(1);

			
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.8, 45);
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);//Go forward 
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(0.8,45);
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
//			System.out.println("Ya can't go forward unless you give gameData some dumb value. I won't allow it!");		
		}
		else if ("RLR".equals(this.gameData)|| "RRR".equals(this.gameData))//Runs basic center auton for right switch
		{
/*			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
	    		ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
	    		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);//60
	        }
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(35);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(7, 0.0, 0.3); //original d=14, speed= 0.3  ??? scale down distance in test mode
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(.45/2, 540, 0, 24 );//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(.45/2, -45, 0, 24 );//.38, 75
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(.45/2, 45, 0, 24 );
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(.45/2, -45, 0, 24 );
*/
			
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
	    		ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
				ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(35);
	        }
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2, 0.0, 0.30); //??0.4
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 40, 0, 24 );//.38, 75
			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
			//???		ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.3, 25);
			//ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.2, 85);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
//???			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(6, 0.0, 0.4);//17, 9.641ft14
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(12, 0.0, 0.35);//17, 9.641ft
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
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(8, 0.0, 0.30);//17, 9.748
			if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
	        {
				ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
				ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
				SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
				ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
				ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
				ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(2, 0.0, .40);
				ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
	        }
			
			//testing
//???			ActuatorConfig.getInstance().getDrivetrain().motionMagic(3);
//???			ActuatorConfig.getInstance().getDrivetrain().turnRight(.45, 90);
//???			ActuatorConfig.getInstance().getDrivetrain().motionMagic(3);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(.45, 90);
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(3);
			
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(0.8, 45);
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.8,45);
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
			System.out.println("Ya can't go forward unless you give gameData some dumb value. I won't allow it!");		
		}
		else //This does the right auton so be careful not to run into another robot.
		{
			AutonUtility.autonInvalidGameData(this.gameData);
			
			//testing
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(3);
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(.45, 90);
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(3);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(.45, 90);
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(3);
			
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(0.8, 45);
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.8,45);
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
			System.out.println("Fail Safe: (Hannah fix your code) Ya can't go forward unless you give gameData some dumb value. I won't allow it!");
		}
	}


protected void right() //Goes forward
	{
	SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
	
	if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
    {
		ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(35);
    }
	ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(24, 0.0, 0.35); //??0.4
	SensorConfig.getInstance().getTimer().waitTimeInMillis(500);

	if ("RLR".equals(this.gameData)|| "RRR".equals(this.gameData))//Runs right auton for right switch 
	{
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
			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
			ActuatorConfig.getInstance().getDrivetrain().goBackwardsGyro(2, 0.0, .40);
			ActuatorConfig.getInstance().getDrivetrain().lowerLiftTo(200);
		}
	}
	System.out.println("Position right: Goes forward");

	/// testing
	
 /*       if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
        {
    		ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
    		ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);
        }
*/
	//        ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(14, 0.0, 0.3);

		//testing
//		ActuatorConfig.getInstance().getDrivetrain().motionMagic(5);
//		ActuatorConfig.getInstance().getDrivetrain().movePid(4);
//		ActuatorConfig.getInstance().getDrivetrain().turnRightRadius(0.2, 75, 0, 24 );
 //       SensorConfig.getInstance().getTimer().waitTimeInMillis(2000);
//		ActuatorConfig.getInstance().getDrivetrain().turnLeftRadius(0.16, 75, 0, 24 );
  //      ActuatorConfig.getInstance().getDrivetrain().turnRight(0.25, 90);
//		System.out.println("Position right: Goes forward");
	}

}