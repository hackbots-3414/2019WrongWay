package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.sensor.SensorConfig;

public class AutonSideSwitchNearOrFar extends AutonBase
{
	// Start from left or right, deliver cube to near switch if it's on our side,
	// otherwise just go straight forward (cross Auto Line) and standby till the end of the auton period
	// Caution: to avoid collisions with friendlies, make sure they will not simultaneously deliver to far scale or far switch.
	// if we're only delivering one cube, it's not a problem if our alliance member goes to far (our side) switch. It delivers to the far switch from behind, 
	// our robot would deliver the cube to switch from near side, left or right. 
	// But when our robot tries to pick up a second cube, it will  to behind of switch  
	public AutonSideSwitchNearOrFar() 
	{
		super();
	}

	protected void left() 
	{
		AutonUtility.autonStartActions();

		if("LRL".equals(this.gameData)|| "LLL".equals(this.gameData)) //Left switch from left side
		{
			//deliver to switch, near side 
			AutonUtility.autonSwitchNearSideDelivery(true);

			System.out.println("Position Left: Left switch");
		}
		else if("RRR".equals(this.gameData) || "RLR".equals(this.gameData)) //Park past switch from left side
		{
			//deliver to switch, far side 
			AutonUtility.autonSwitchFarSideDelivery(true);

			System.out.println("Position Left: right switch");
		}
		else
		{
			AutonUtility.autonInvalidGameData(this.gameData);
		}
		
	}

	protected void center() //Perhaps make a fail safe (but comment it out)
	{	
		AutonUtility.autonStartActions();

		// To do: validation is needed

		System.out.println("SideSwitchNearOrFar - need start from left or right, not center.");
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
			//deliver to switch, near side
			AutonUtility.autonSwitchNearSideDelivery(false);
			
			System.out.println("Position Right: Right switch");
		}
		else if ("LRL".equals(this.gameData) || "LLL".equals(this.gameData))
		{
			//deliver to switch, far side
			AutonUtility.autonSwitchFarSideDelivery(false);
			
			System.out.println("Position Right: Left switch");
		}
		else
		{
			AutonUtility.autonInvalidGameData(this.gameData);
		}
	}
}