package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.autonomous.AutonUtility;

public class AutonSideSwitchAndScalePriority extends AutonBase
{
	public AutonSideSwitchAndScalePriority() 
	{
		super();
	}

	
	protected void left()
	{
		AutonUtility.autonStartActions();

		if("LRL".equals(this.gameData)) //switch
		{
			AutonUtility.autonSwitchNearSideDelivery(true);
//			AutonUtility.deliverSideSwitch(true);
		}
		else if ("LLL".equals(this.gameData)) //scale
		{
			AutonUtility.autonScaleNearSideDelivery(true);
//			AutonUtility.deliverSideScale(true);
		}
		else if ("RLR".equals(this.gameData)) //scale
		{
			AutonUtility.autonScaleNearSideDelivery(true);
//			AutonUtility.deliverSideScale(true);
		}
		else if ("RRR".equals(this.gameData)) //drive forward
		{
			AutonUtility.autonCrossAutoLineStraight();
/*
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, 0.0, .35);
*/
		}
		else
		{
//			System.out.println("I'm a little confused");
			AutonUtility.autonInvalidGameData(this.gameData);
		}
		
	}

	
	protected void center()
	{
		AutonUtility.autonStartActions();

//		System.out.println("I'm a little confused");
		
		System.out.println("Position conflict. ");
	}

	
	protected void right()
	{
		AutonUtility.autonStartActions();

		if("RLR".equals(this.gameData)) //switch
		{
			AutonUtility.autonSwitchNearSideDelivery(false);
//			AutonUtility.deliverSideSwitch(false);
		}
		else if ("RRR".equals(this.gameData)) //scale
		{
			AutonUtility.autonScaleNearSideDelivery(false);
//			AutonUtility.deliverSideScale(false);
		}
		else if ("LRL".equals(this.gameData)) //scale
		{
			AutonUtility.autonScaleNearSideDelivery(false);
//			AutonUtility.deliverSideScale(false);
		}
		else if ("LLL".equals(this.gameData)) //drive forward
		{
			AutonUtility.autonCrossAutoLineStraight();
/*
			ActuatorConfig.getInstance().getDrivetrain().setInitialServoPosition();
			ActuatorConfig.getInstance().getDrivetrain().lowerAnglerTo(60);
			ActuatorConfig.getInstance().getDrivetrain().goForwardGyro(18, 0.0, .35);
*/
		}
		else
		{
//			System.out.println("I'm a little confused");
			AutonUtility.autonInvalidGameData(this.gameData);
		}
	}
	

}
