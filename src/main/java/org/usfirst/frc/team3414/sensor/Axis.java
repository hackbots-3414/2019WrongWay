package org.usfirst.frc.team3414.sensor;

public enum Axis {
	X(0),
	Y(1),
	Z(2);
	
	//The value of the access(Obtained from the joystick code)
	public final int value;
	
	private Axis(int value)
	{
		this.value = value;
	}
}
