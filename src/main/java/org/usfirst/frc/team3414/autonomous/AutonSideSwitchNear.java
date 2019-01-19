package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.sensor.SensorConfig;

public class AutonSideSwitchNear extends AutonBase
{
	// start from left or right, deliver cube to near switch if it is in our favor,
	// otherwise just finish straight forward (cross Auto Line) and standby till end of auton period
	public AutonSideSwitchNear() 
	{
		super();
	}

	protected void left() 
	{
		AutonUtility.autonStartActions();

		if("LRL".equals(this.gameData)|| "LLL".equals(this.gameData)) //Left switch from left side
		{
			//deliver to switch
			AutonUtility.autonSwitchNearSideDelivery(true);

			System.out.println("Position Left: Left switch");
		}
		else if("RRR".equals(this.gameData) || "RLR".equals(this.gameData)) //Park past switch from left side
		{
			//regression, cross the Auto Line (go forward)
			AutonUtility.autonCrossAutoLineStraight();
			
			System.out.println("Position Left: Park past switch");
		}
		else
		{
			AutonUtility.autonInvalidGameData(this.gameData);
		}
		
	}

	protected void center() //Perhaps make a fail safe (but comment it out)
	{	
		AutonUtility.autonStartActions();
		
		System.out.println("Position conflict!");
		System.out.println("SideSwitchNear - need start from left or right, not center.");
		System.out.println("This is human error.But I (robot) must do something anyway.");
		System.out.println("I (must) use vision (camera) to see where I am.");
		// center cross Auto Line
		// center switch
		// straight cross Auto Line
		// side switch near
	}

	protected void right() 
	{
		AutonUtility.autonStartActions();

		if("RLR".equals(this.gameData)|| "RRR".equals(this.gameData)) //Right switch from right position
		{
			//deliver to switch
			AutonUtility.autonSwitchNearSideDelivery(false);
			
			System.out.println("Position Right: Right switch");
		}
		else if ("LRL".equals(this.gameData) || "LLL".equals(this.gameData))
		{
			//regression, cross the Auto Line (go forward)
			AutonUtility.autonCrossAutoLineStraight();
			
			System.out.println("Position Right: Park past switch");	
		}
		else
		{
			AutonUtility.autonInvalidGameData(this.gameData);
		}
	}
}