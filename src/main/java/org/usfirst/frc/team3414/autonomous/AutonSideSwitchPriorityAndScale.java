package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.sensor.SensorConfig;

public class AutonSideSwitchPriorityAndScale extends AutonBase
{

	public AutonSideSwitchPriorityAndScale() {
		super();
	}

	protected void left() 
	{
		AutonUtility.autonStartActions();

		if("LRL".equals(this.gameData)) //Switch only
		{	
			AutonUtility.autonSwitchNearSideDelivery(true);
/* regular season version
			//deliver to switch
			AutonUtility.deliverSideSwitch(true);
			
			
			
			//Lincoln-Timed
//			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, .35);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(.36, 88);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-.40);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1600);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
//			ActuatorConfig.getInstance().getLift().setSpeed(-.40);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
//			ActuatorConfig.getInstance().getLift().setSpeed(0);
//			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
//			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
*/			
			System.out.println("Position left: Going for the switch");
		}
		
		else if ("LLL".equals(this.gameData)) //Switch
		{
			AutonUtility.autonSwitchNearSideDelivery(true);
/* regular season version
			
			//deliver to switch
			AutonUtility.deliverSideSwitch(true);
			
			
			//Lincoln-Timed
//			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, .35);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//			ActuatorConfig.getInstance().getDrivetrain().turnRight(.36, 88);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-.40);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1600);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
//			ActuatorConfig.getInstance().getLift().setSpeed(-.40);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
//			ActuatorConfig.getInstance().getLift().setSpeed(0);
//			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
//			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
*/			
			System.out.println("Postion left: Going for both the switch and scale (wish me luck ^-^)");
		}
		
		else if ("RLR".equals(this.gameData))//Scale only
		{
			AutonUtility.autonScaleNearSideDelivery(true);
/* regular season version
			
			//deliver to scale
			AutonUtility.deliverSideScale(true);
*/			

		}
		else if ("RRR".equals(this.gameData))//drive forward 
		{
			AutonUtility.autonCrossAutoLineStraight();
/* regular season version
			
			//drive forwards
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, 0.0, .35);
*/			
		}
		else
		{
			AutonUtility.autonInvalidGameData(this.gameData);
/* regular season version
//			ActuatorConfig.getInstance().getDrivetrain().movePid(4);	
			System.out.println("Hannah, ya done messed up, fix your code.");
*/
		}
	}

	protected void center() //Perhaps make a failsafe here if this becomes a problem.
	{
		AutonUtility.autonStartActions();
//		System.out.println("Nothing here to see here o_o -Hannah");	
		//To do: validation is needed
	}

	protected void right() 
	{
		AutonUtility.autonStartActions();

		if("RLR".equals(this.gameData)) //Switch only
		{
			AutonUtility.autonSwitchNearSideDelivery(false);
			
/* regular season version
			
			//deliver to switch
			AutonUtility.deliverSideSwitch(false);

			
			
			//Lincoln- Timed
//			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, .35);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(.36, 88);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-.40);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1600);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
//			ActuatorConfig.getInstance().getLift().setSpeed(-.40);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
//			ActuatorConfig.getInstance().getLift().setSpeed(0);
//			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
//			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
*/
			System.out.println("Position right: Going for the switch");
		}
		else if ("RRR".equals(this.gameData)) //switch
		{
			AutonUtility.autonSwitchNearSideDelivery(false);
			
/* regular season version
			//deliver to switch
			AutonUtility.deliverSideSwitch(false);

			
			
			//Lincoln - Timed
//			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
//			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, .35);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(500);
//			ActuatorConfig.getInstance().getDrivetrain().turnLeft(.36, 88);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-.40);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1600);
//			ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
//			ActuatorConfig.getInstance().getLift().setSpeed(-.40);
//			SensorConfig.getInstance().getTimer().waitTimeInMillis(1000);
//			ActuatorConfig.getInstance().getLift().setSpeed(0);
//			ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(.40);
//			ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.40);
*/			
//			System.out.println("Position right: Going for both the switch and scale (wish me luck ^-^)");
		}
		else if ("LRL".equals(this.gameData))//Scale only
		{
			AutonUtility.autonScaleNearSideDelivery(false);
/* regular season version
			//deliver to scale
			AutonUtility.deliverSideScale(false);
//			System.out.println("Position right: Going for scale");
*/
		}
		else if ("LLL".equals(this.gameData))//drive forward
		{
			AutonUtility.autonCrossAutoLineStraight();
/* regular season version
			//drive forwards
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, 0.0,.35);
*/			
			System.out.println("Position right: Parking past switch");
		}
		else
		{
			AutonUtility.autonInvalidGameData(this.gameData);
/* regular season version
//			ActuatorConfig.getInstance().getDrivetrain().movePid(4);	
			System.out.println("Hannah, ya done messed up, fix your code.");
*/			
		}
		
	}

}


