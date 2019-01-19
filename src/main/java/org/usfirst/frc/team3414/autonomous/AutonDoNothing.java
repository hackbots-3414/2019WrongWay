package org.usfirst.frc.team3414.autonomous;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.sensor.SensorConfig;
import org.usfirst.frc.team3414.autonomous.AutonUtility;

public class AutonDoNothing extends AutonBase
{

	public AutonDoNothing() {
		super();
		}

	protected void left() 
	{
		AutonUtility.autonStartActions();
		System.out.println("DO NOTHING I SAY! - Hannah and Kayleigh");		
	}

	protected void center() 
	{
		AutonUtility.autonStartActions();
		System.out.println("DO NOTHING I SAY! - Hannah and Kayleigh");	
	}

	protected void right() 
	{
		AutonUtility.autonStartActions();
		System.out.println("DO NOTHING I SAY! - Hannah and Kayleigh");		
	}

}

