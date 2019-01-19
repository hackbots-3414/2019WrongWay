package org.usfirst.frc.team3414.actuators;

public class MoveCorrection {
	private double maxAbsoluteDeltaDistance = 2; // To do, unit is necessary, related to moveGyro* functions. Currently, unit is rotation or diameter of wheel
	private double maxAbsoluteDeltaYaw = 15; // degree
	private double deltaDistance = 0;
	private double deltaYaw = 0;
	
	private boolean isAdjusted = false;
	
	public void reset()
	{
		deltaDistance = 0;
		deltaYaw = 0;
		isAdjusted = false;
	}
	
	public void setDeltaDistance(double dDist)
	{
		if(validateDeltaDistance(dDist))
			deltaDistance = dDist;
		else
		{
			if(dDist > 0)
				deltaDistance = maxAbsoluteDeltaDistance;
			else
				deltaDistance = -maxAbsoluteDeltaDistance;
			
			isAdjusted = true;
		}
	}
	
	public void setDeltaYaw(double dYaw)
	{
		if(validateDeltaYaw(dYaw))
			deltaYaw = dYaw;
		else
		{
			if(dYaw > 0)
				deltaYaw = maxAbsoluteDeltaYaw;
			else
				deltaYaw = -maxAbsoluteDeltaYaw;
				
			isAdjusted = true;
		}
	}
	
	public double getDeltaDistance()
	{
		return deltaDistance;
	}
	
	public double getDeltaYaw()
	{
		return deltaYaw;
	}
	
	public boolean getIsAdjusted()
	{
		return isAdjusted;
	}
	
	private boolean validateDeltaDistance(double dDist)
	{
		if(Math.abs(dDist) > maxAbsoluteDeltaDistance)
			return false;
		else
			return true;
	}
	
	private boolean validateDeltaYaw(double dYaw)
	{
		if(Math.abs(dYaw) > maxAbsoluteDeltaYaw)
			return false;
		else
			return true;
	}
	
}
