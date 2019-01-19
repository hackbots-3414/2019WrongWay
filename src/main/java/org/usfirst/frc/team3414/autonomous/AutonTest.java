package org.usfirst.frc.team3414.autonomous;

public class AutonTest extends AutonBase
{
	public AutonTest()
	{
		super();
	}

	protected void left() //Goes forward
	{
		AutonUtility.autonStartActions();
		AutonUtility.autonCrossAutoLineStraight();
		System.out.println("(test) Position left: Goes forward, cross the Auto Line");
	}

	protected void center() // make a curve to approach switch in favor.
	{
		AutonUtility.autonStartActions();

		if (("LRL".equals(this.gameData))|| ("LLL".equals(this.gameData)))//Runs basic center auton for left switch
		{
			AutonUtility.autonCenterTest(false);
			System.out.println("(test) Position center: Goes towards left switch along a curve, cross the Auto Line");
		}
		else if ("RLR".equals(this.gameData)|| "RRR".equals(this.gameData))//Runs basic center auton for right switch
		{
			AutonUtility.autonCrossAutoLineCurve(true);
			System.out.println("(test) Position center: Goes towards right switch along a curve, cross the Auto Line");
		}
		else //This does the right auton so be careful not to run into another robot.
		{
			AutonUtility.autonInvalidGameData(this.gameData);
		}
	}

	protected void right() //Goes forward
	{
		AutonUtility.autonStartActions();
		AutonUtility.autonCrossAutoLineStraight();
		System.out.println("(test) Position right: Goes forward, cross the Auto Line");
	}

}
