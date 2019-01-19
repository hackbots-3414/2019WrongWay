package org.usfirst.frc.team3414.sensor;

import edu.wpi.first.wpilibj.Joystick;

public class HBJoystick extends Joystick{
private boolean isReversed;
	
	public HBJoystick(int port)
	{
		super(port);
	}
	
	public double getYAxis()
	{
		if(isReversed)
		{
			return getY();
		}
		else
		{
			return -(getY());
		}
	}
	
	public void setReversed(boolean isReversed)
	{
		this.isReversed = isReversed;
	}

	public boolean isReversed()
	{
		return isReversed;
	}
}
