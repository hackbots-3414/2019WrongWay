package org.usfirst.frc.team3414.sensor;

public interface IGamepad {
	double getPov = 0;

	/**
	 * Gets the state of the button
	 * @param button
	 * @return state of button
	 */
	public boolean getButtonState(int button);
	
	/**
	 * Get the value of the X axis
	 * @return X-axis value
	 */
	public double getX();
	
	/**
	 * Get the value of the Y axis
	 * @return Y-axis value
	 */
	public double getY();
	
	/**
	 * Get the value of the Z axis
	 * @return Z-axis value
	 */
	public double getZ();
	
	public double getPov();
}
