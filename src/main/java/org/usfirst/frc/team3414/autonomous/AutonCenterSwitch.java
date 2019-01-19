package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.sensor.SensorConfig;
import org.usfirst.frc.team3414.actuators.ActuatorConfig;

public class AutonCenterSwitch extends AutonBase{
	// Sets robot near center position, left side of bumper should touch the right side line of the exchange area 
	//wrong - Set the robot right in front of the right side of the switch
	public AutonCenterSwitch() {
		super();
	}

	protected void left() 
	{
		AutonUtility.autonStartActions();

/* regular season version
		ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, 0.0, 0.5);
*/
		System.out.println("Really confused! Supposed to set robot in center position.");
		System.out.println("Validate combination of autonChooser and positionChooser.");
		System.out.println("For example, AutonCenterSwitch must pair with Center position, instead of Left or Right.");
		
		System.out.println("Position conflict!");
		System.out.println("SideSwitchNear - need start from left or right, not center.");
		System.out.println("This is human error.But I (robot) must do something anyway.");
		System.out.println("I (must) use vision (camera) to see where I am.");
		// center,left, or right
		// accordingly do suitable cross Auto Line of deliver near switch  
		
	}

	protected void center() 
	{
		AutonUtility.autonStartActions();
		if("LLL".equals(this.gameData) || "LRL".equals(this.gameData)) 
		{
			AutonUtility.autonCenterSwitchDelivery(false);
/* regular season version
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();

			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(30);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.4);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.3, 25);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(6, 0.0, 0.4);//17, 9.641ft
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().turnRight(0.32, 5);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			//ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.5);//17, 9.748
			
			//lift to switch used to be here
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerSwitch();
			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0.4);
			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0.4);

//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(3);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(.45, 90);
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(2);
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(.45, 90);
//			ActuatorConfig.getInstance().getDrivetrain().motionMagic(1);
//			ActuatorConfig.getInstance().getLift().setSpeed(-.20);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1500);
//			ActuatorConfig.getInstance().getLift().setSpeed(0);
//			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.35);
//			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.35);

			
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.8, 90);
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(0.8, 90);
 */
		}
		
		else if("RLR".equals(this.gameData) || "RRR".equals(this.gameData)) 
		{
			AutonUtility.autonCenterSwitchDelivery(true);
/* regular season version
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(30);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(1, 0.0, 0.4);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			ActuatorConfig.getInstance().getDrivetrain().turnRight(0.3, 12);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(2.5, 0.0, 0.4);//17, 9.641ft
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
			ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.32, 20);
			//SensorConfig.getInstance().getTimer().waitTimeInMillis(300);
			ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(4, 0.0, 0.5);//17, 9.748
			//lift to switch used to be here
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerSwitch();
			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0.4);
			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0.4);
			
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(16, 90);
//			ActuatorConfig.getInstance().getLift().setSpeed(0.3);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-0.4);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1500);
//			ActuatorConfig.getInstance().getLift().setSpeed(0);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
//			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0.5);
//			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0.5);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1500);
//			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
//			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
			
			
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(0.8, 90);
//			ActuatorConfig.getInstance().getDrivetrain().movePid(2);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(0.8, 90);

 */
		}
		
		else //This does the right auton so be careful to not run into another robot. 
		{ 
			AutonUtility.autonInvalidGameData(this.gameData);

/* regular season version
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(200);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(10, 0.0, 0.5);
			ActuatorConfig.getInstance().getDrivetrain().liftToSwitch();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerSwitch();	
*/
		}
	}

	protected void right() 
	{
		AutonUtility.autonStartActions();
/* regular season version
		ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
		ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, 0.0, 0.5);
*/		
//		AutonUtility.autonPostionConflict(Position.RIGHT, autonName);
		System.out.println("Really confused! Supposed to set robot in center position.");
		System.out.println("Validate combination of autonChooser and positionChooser.");
		System.out.println("For example, AutonCenterSwitch must pair with Center position, instead of Left or Right.");
		
		System.out.println("Position conflict!");
		System.out.println("SideSwitchNear - need start from left or right, not center.");
		System.out.println("This is human error.But I (robot) must do something anyway.");
		System.out.println("I (must) use vision (camera) to see where I am.");
		// center,left, or right
		// accordingly do suitable cross Auto Line of deliver near switch  

	}

}
