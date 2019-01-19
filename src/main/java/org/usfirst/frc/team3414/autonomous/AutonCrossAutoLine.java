package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.sensor.SensorConfig;
import org.usfirst.frc.team3414.autonomous.AutonUtility;

// Crosses the Auto Line (a.k.a Auto-Run). 
// Normally, robot move forward over (10 ft - robot length) from ALLIANCE WALL,
// but in 2018 Power Up game, if the robot starts from the center, it needs to move in a curve 
// to avoid colliding with the cube pile in front.
// So use AutonCrossAutoLine to replace AutonGoForward
public class AutonCrossAutoLine extends AutonBase
{
	public AutonCrossAutoLine()
	{
		super();
	}

	protected void left() //Goes forward
	{
		AutonUtility.autonStartActions();
		AutonUtility.autonCrossAutoLineStraight();
		System.out.println("Position left: Goes forward, cross the Auto Line");
	}

	protected void center() // make a curve to approach switch in favor.
	{
		AutonUtility.autonStartActions();

		if (("LRL".equals(this.gameData))|| ("LLL".equals(this.gameData)))//Runs basic center auton for left switch
		{
			AutonUtility.autonCrossAutoLineCurve(false);
			System.out.println("Position center: Goes towards left switch along a curve, cross the Auto Line");
		}
		else if ("RLR".equals(this.gameData)|| "RRR".equals(this.gameData))//Runs basic center auton for right switch
		{
			AutonUtility.autonCrossAutoLineCurve(true);
			System.out.println("Position center: Goes towards right switch along a curve, cross the Auto Line");
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
		System.out.println("Position right: Goes forward, cross the Auto Line");
	}
}