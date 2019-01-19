package org.usfirst.frc.team3414.actuators;

public interface IDriveTrain {
	/**
	 * Sets the overall speed for the drivetrain
	 * @param speed
	 */
	public void setSpeed(double speed);
	
	/**
	 * Sets the speed of the drivetrain for each respective sides
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public void setSpeed(double leftSpeed, double rightSpeed);
	
	/**
	 * Stops the drivetrain
	 */
	public void stop();
	
	/**
	 * Turns the robot a specified angle to the right
	 * @param speed
	 * @param angle
	 */
	public void turnRight(double speed, double angle);
	/**
	 * Moves the robot along a circle's circumference, the three radii being the robot's center's and ends' distances from the "origin" 
	 * @param speed
	 * @param angle
	 * @param radius
	 * @param width
	 */
	public void turnRightRadius(double speed, double angle, double radius, double width); 
	
	

	/**
	 * Turns the robot a specified angle to the left
	 * @param speed
	 * @param angle
	 */
	public void turnLeft(double speed, double angle);
}
