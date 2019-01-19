package org.usfirst.frc.team3414.actuators;

import org.usfirst.frc.team3414.util.RotationalDirection;
import org.usfirst.frc.team3414.actuators.Motor;

public class DoubleMotor extends MotorBase {
	
	private Motor motorOne;
	private Motor motorTwo;
	
	private boolean isSafeConfig = false;
	
	/**
	 * Creates a double motor
	 * @param motorOne
	 * @param motorTwo
	 */
	public DoubleMotor(Motor motorOne, Motor motorTwo)
	{
		this.motorOne = motorOne;
		this.motorTwo = motorTwo;
		
		motorOne.setFollowerMotor(motorTwo);
		
		isSafeConfig();
	}
	
	public void setSpeed(double speed)
	{
		if(isSafeConfig())
		{
			motorOne.setSpeed(speed);
//			motorTwo.setSpeed(speed);
		}	
	}
	public double getCurrentMotorOne()
	{
		return motorOne.getCurrent();
	}
	
	public double getCurrentMotorTwo()
	{
		return motorTwo.getCurrent();
	}
	public void stop() 
	{
		motorOne.stop();
//		motorTwo.stop();
	}
	
	public double getSpeed()
	{
		return (this.motorOne.getSpeed() + this.motorTwo.getSpeed()) / 2;
	}

	public void setDirection(RotationalDirection direction) 
	{
		motorOne.setDirection(direction);
		motorTwo.setDirection(direction);
	}
	
	/**
	 * Checks to make sure that both motors will be spinning in the same direction. Otherwise, the motors will not run in order to prevent damage
	 * @return isConfigSafe
	 */
	private boolean isSafeConfig()
	{
		if(motorOne.isReversed && motorTwo.isReversed)
		{
			isSafeConfig = true;
		}	
		else if(!motorOne.isReversed && !motorTwo.isReversed)
		{
			isSafeConfig = true;
		}
		else
		{
			isSafeConfig = false;
			System.err.println("Double Motor Configuration is unsafe: Locking");
		}
		
		return isSafeConfig;
	}
	
	public void setMotorReversed(boolean reverse)
	{
		this.isReversed = reverse;
		
		motorOne.setMotorReversed(reverse);
		motorTwo.setMotorReversed(reverse);
	}
}
