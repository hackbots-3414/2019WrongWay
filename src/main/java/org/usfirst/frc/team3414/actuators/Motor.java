package org.usfirst.frc.team3414.actuators;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team3414.util.RotationalDirection;

public class Motor extends MotorBase
{
	private TalonSRX talon;//The talon that controls the motor
	
	private double speed = 0;
	
	/**
	 * Creates a motor
	 * @param talon
	 */
	public Motor(TalonSRX talon)
	{
		this.talon = talon;
		direction = RotationalDirection.NONE;
	}
	
	public void setFollowerMotor(Motor follower) 
	{
		follower.talon.set(com.ctre.phoenix.motorcontrol.ControlMode.Follower, this.talon.getDeviceID());
	}
		
	public void setSpeed(double speed)
	{
		this.speed = speed;
		
		if(isReversed)
		{
			speed = 0 - speed;
		}
				
		if(speed < 0)
		{
			direction = RotationalDirection.COUNTERCLOCKWISE;
			setRunning(true);
		}
		else if(speed > 0)
		{
			direction = RotationalDirection.CLOCKWISE;
			setRunning(true);
		}
		else
		{
			direction = RotationalDirection.NONE;
			setRunning(false);
		}
		
		talon.set(ControlMode.PercentOutput, speed);
	}

	public void stop() 
	{
		setSpeed(0);		
	}
	
	public double getSpeed()
	{
		return this.speed;
	}

	public RotationalDirection getDirection() 
	{
		return direction;
	}
	
	public double getCurrent()
	{
		return this.getCurrent();
	}

	public void setDirection(RotationalDirection direction)
	{
		this.direction = direction;
	}	
	
	public void setMotorReversed(boolean reverse)
	{
		//this.isReversed = reverse;
		talon.setInverted(reverse);
	}
	
	public void setCurrentLimit(int amps)
	{
		talon.configPeakCurrentLimit(amps, 10);
	}


}
