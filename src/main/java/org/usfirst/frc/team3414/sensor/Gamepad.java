package org.usfirst.frc.team3414.sensor;

import edu.wpi.first.wpilibj.DriverStation;

public class Gamepad implements IGamepad {
	private int port;
	private DriverStation driverStation;
	
	public Gamepad(int port)
	{
		this.port = port;
		driverStation = DriverStation.getInstance();
	}

	public boolean getButtonState(int button) 
	{	
		return driverStation.getStickButton(port, (byte)button);
	}

	public double getX() 
	{
		return driverStation.getStickAxis(port, Axis.X.value);
	}

	public double getY()
	{
		return driverStation.getStickAxis(port, Axis.Y.value);
	}

	public double getZ()
	{
		return driverStation.getStickAxis(port, Axis.Z.value);
	}
	
	public double getPov()
	{
		return driverStation.getStickPOV(port, 0);
	}
}
