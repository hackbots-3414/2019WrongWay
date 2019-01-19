package org.usfirst.frc.team3414.autonomous;

public class AutonScaleNearOrFar extends AutonBase
{
	public AutonScaleNearOrFar() 
	{
		super();
	}

	
	protected void left()
	{
		AutonUtility.autonStartActions();

		if("LRL".equals(this.gameData)) //scale
		{
			AutonUtility.autonSwitchFarSideDelivery(true);
		}
		else if ("LLL".equals(this.gameData)) //scale
		{
			AutonUtility.autonScaleNearSideDelivery(true);
		}
		else if ("RLR".equals(this.gameData)) //scale
		{
			AutonUtility.autonScaleNearSideDelivery(true);
		}
		else if ("RRR".equals(this.gameData)) //scale
		{
			AutonUtility.autonSwitchFarSideDelivery(true);
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
		System.out.println("Only 4 situations: RRR, RLR, LRL, and LLL. ");
		System.out.println(this.gameData + " is not a valid game data value. ");
	}

	
	protected void right()
	{
		AutonUtility.autonStartActions();

		if("RLR".equals(this.gameData)) //switch
		{
			AutonUtility.autonScaleFarSideDelivery(false);
		}
		else if ("RRR".equals(this.gameData)) //scale
		{
			AutonUtility.autonScaleNearSideDelivery(false);
		}
		else if ("LRL".equals(this.gameData)) //scale
		{
			AutonUtility.autonScaleNearSideDelivery(false);
		}
		else if ("LLL".equals(this.gameData)) //drive forward
		{
			AutonUtility.autonScaleFarSideDelivery(false);
		}
		else
		{
//			System.out.println("I'm a little confused");
			AutonUtility.autonInvalidGameData(this.gameData);
		}
	}
}
