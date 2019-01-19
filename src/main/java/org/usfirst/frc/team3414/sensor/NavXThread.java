package org.usfirst.frc.team3414.sensor;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;

public class NavXThread implements Runnable
{
	private AHRS ahrs;
	
	private final static double GRAVITY = 9.80;
	
	private boolean isEnabled = true;
	
	private double pastYaw = 0;
	
	private double trueYaw = 0;
	
	private double lastRawYaw = 0;
	private Thread newThread = new Thread(this);
	
	private double currentZAngle = 0;
	private double currentYaw = 0;
	private double currentPitch = 0;
	private double currentRoll = 0;

	private double currentVelocityX = 0;
	private double currentVelocityY = 0;
	private double currentVelocityZ = 0;

	private double lastZAngle = 0.0;
	private double lastYaw = 0.0;
	private double lastPitch = 0.0;
	private double lastRoll = 0.0;
	private double lastVelocityX = 0;
	private double lastVelocityY = 0;
	private double lastVelocityZ = 0;

	private double movedX = 0;
	private double movedY = 0;
	private double movedZ = 0;
//	private double lastTimeStamp;
	private double lastTimeStampMillis;
	private String outputLog = "Timeleap, movedX;";


	public NavXThread(AHRS ahrs)
	{
		this.ahrs = ahrs;
		
		newThread.start();
		
	}
		
	public double getX()
	{
		return ahrs.getWorldLinearAccelX();
	}

	public double getY()
	{
		return ahrs.getWorldLinearAccelY();
	}

	public double getZ()
	{
		return ahrs.getWorldLinearAccelZ() + GRAVITY;
	}

	public void resetDisplacment()
	{
		ahrs.resetDisplacement();
	}
	
	public float getYaw()
	{
		pastYaw = ahrs.getYaw();
		
		if(pastYaw < 0)
		{			
			return (float)ahrs.getYaw() + 360;
		}
		else if(pastYaw > 0)
		{
			return (float)ahrs.getYaw();
		}
		else
		{
			return 0;
		}
	}
	
	public double getTrueYaw()
	{
		double currentRawYaw = getRawYaw(); 
		 
//		if (currentRawYaw < 0){
//			trueYaw = (180 + (180 - Math.abs(currentRawYaw)));
//		}
		
		if (currentRawYaw >= 0 && lastRawYaw >= 0) {
		    trueYaw += currentRawYaw - lastRawYaw;
		}
		else if (currentRawYaw >= 0 && lastRawYaw <= 0) {
			if((Math.abs(currentRawYaw) < 90) && (Math.abs(lastRawYaw)< 90))
				trueYaw += (Math.abs(currentRawYaw) + Math.abs(lastRawYaw));
			else if ((Math.abs(currentRawYaw) > 90) && (Math.abs(lastRawYaw) > 90))
				trueYaw += -((180- Math.abs(currentRawYaw)) + (180 -Math.abs(lastRawYaw))) ;
			else
			{
				System.out.println("Error sampling: currentRawYaw=" + currentRawYaw + "lastRawYaw=" + lastRawYaw); 
				System.out.println("NavX sampling gap is bigger than 90 degree, hard to guess which rotation direction");
				System.out.println("Please consider init TrueYaw, or init getTrueYaw loop (last = current)");
			}
		}
		else if (currentRawYaw <= 0 && lastRawYaw >= 0) {
			if((Math.abs(currentRawYaw) < 90) && (Math.abs(lastRawYaw)< 90))
				trueYaw += -(Math.abs(currentRawYaw) + Math.abs(lastRawYaw));
			else if((Math.abs(currentRawYaw) > 90) && (Math.abs(lastRawYaw) > 90))
				trueYaw += ((180- Math.abs(currentRawYaw)) + (180 -Math.abs(lastRawYaw))) ;
			else {
				System.out.println("Error sampling: currentRawYaw=" + currentRawYaw + "lastRawYaw=" + lastRawYaw); 
				System.out.println("NavX sampling gap is bigger than 90 degree, hard to guess which rotation direction");
				System.out.println("Please consider init TrueYaw, or init getTrueYaw loop (last = current)");
			}
		}
		else if (currentRawYaw < 0 && lastRawYaw < 0) {
		    trueYaw += - (Math.abs(currentRawYaw) - Math.abs(lastRawYaw));
		}

		lastRawYaw = currentRawYaw;
		return trueYaw; 
	}
	
	
	public double getPitch()
	{
		return ahrs.getPitch();
	}
	
	public double getRoll()
	{
		return ahrs.getRoll();
	}
	
	public double getRawYaw()
	{
		return ahrs.getYaw();
	}
	
	public double getRate()
	{
		return ahrs.getRate();
	}

	public void enable()
	{
		isEnabled = true;
	}

	public void disable()
	{
		isEnabled = false;
	}
	
	public boolean isEnabled() 
	{
		return isEnabled;
	}
	
	public void reset()
	{
		ahrs.reset();
		ahrs.resetDisplacement();
		lastRawYaw = getRawYaw();
		trueYaw = 0;
		
		lastZAngle = 0;
		lastYaw = 0;
		lastPitch = 0;
		lastRoll = 0;
		lastVelocityX = 0;
		lastVelocityY = 0;
		lastVelocityZ = 0;
		movedX = 0;
		movedY = 0;
		movedZ = 0;
//		lastTimeStamp = Timer.getFPGATimestamp();
		lastTimeStampMillis = System.currentTimeMillis();

	}
	public boolean isMoving()
    {
        return ahrs.isMoving();
    }

    public boolean isRotating()
    {
        return ahrs.isRotating();
    }


	
	public void resetLastRawYaw()
	{
		lastRawYaw = getRawYaw();
	}
	
	public double getVelocityX()
	{
		return ahrs.getVelocityX();
	}
	
	public double getVelocityY()
	{
		return ahrs.getVelocityY();
	}
	
	public double getVelocityZ()
	{
		return ahrs.getVelocityZ();
	}
	
	public double getZAngle()
	{
	/*
    * Returns the total accumulated yaw angle (Z Axis, in degrees)
    * reported by the sensor.
    *<p>
    * NOTE: The angle is continuous, meaning it's range is beyond 360 degrees.
    * This ensures that algorithms that wouldn't want to see a discontinuity 
    * in the gyro output as it sweeps past 0 on the second time around.
    *<p>
    * Note that the returned yaw value will be offset by a user-specified
    * offset value; this user-specified offset value is set by 
    * invoking the zeroYaw() method.
    */
		// use this function to replace getTrueYaw function
		return ahrs.getAngle();
	}

	public double getDispacementX()
	{
		return ahrs.getDisplacementX();
	}
	
	public double getDispacementY()
	{
		return ahrs.getDisplacementY();
	}
	
	public double getDispacementZ()
	{
		return ahrs.getDisplacementZ();
	}
	
	
	public double readZAngle()
	{
		return currentZAngle;
	}
	
	public double readPitch()
	{
		return currentPitch;
	}
	
	public double readYaw()
	{
		return currentYaw;
	}
	
	public double readRoll()
	{
		return currentRoll;
	}
	
	public double readVelocityX()
	{
		return currentVelocityX;
	}
	
	public double readVelocityY()
	{
		return currentVelocityY;
	}
	
	public double readVelocityZ()
	{
		return currentVelocityZ;
	}
	
	public double readMovedX()
	{
		return movedX;
	}
	
	public double readMovedY()
	{
		return movedY;
	}
	
	public double readMovedZ()
	{
		return movedZ;
	}
	
	public String getOutputLog()
	{
		return outputLog;
	}
	
	public void run() 
	{
		double timePerLoop = 0.02; // second(s)
//		double currentTimeStamp;
		long currentTimeStampMillis;
		double timeLeap;
		
		reset();
		
//		lastTimeStamp = Timer.getFPGATimestamp();
		lastTimeStampMillis = System.currentTimeMillis();
		
		Boolean movingStarted = false;
		long loopCount = 20;
		long iLoop = 0;
		
		while(isEnabled)
		{
//			double currentZAngle = Math.round(getZAngle());
//			double currentYaw = Math.round(getRawYaw());
//			double currentPitch = Math.round(getPitch());
//			double currentRoll = Math.round(getRoll());
			currentZAngle = getZAngle();
			currentYaw = getRawYaw();
			currentPitch = getPitch();
			currentRoll = getRoll();

			currentVelocityX = getVelocityX();
			currentVelocityY = getVelocityY();
			currentVelocityZ = getVelocityZ();

			// usually accumulate from second loop (sampling).
			// Since at the beginning last* values all 0, for simple, we accumulate from first loop
			// To do: make correction in the future
			// Note: default velocity unit is meter per second
			// 
//			currentTimeStamp = Timer.getFPGATimestamp();
			currentTimeStampMillis = System.currentTimeMillis();

			timeLeap = (double)(currentTimeStampMillis - lastTimeStampMillis) / 1000;
			movedX += currentVelocityX * timeLeap; 
			movedY += currentVelocityY * timeLeap; 
			movedZ += currentVelocityZ * timeLeap; 
			
			if(Math.abs(currentVelocityX) > 0.05)
				movingStarted = true;
			if(movingStarted && iLoop < loopCount)
			{
				outputLog += "i=" + iLoop + String.format(",%.3f,", timeLeap) + String.format("%.3f;", movedX);
				System.out.println("i=" + iLoop + String.format(",%.3f,", timeLeap) + String.format("%.3f;", movedX));
			}

			iLoop++;
			if(movingStarted && iLoop == loopCount)
			{
				movingStarted = false;
				loopCount = 0;
			}
			
			/*if((lastYaw != currentYaw) || (lastPitch != currentPitch) || (lastRoll != currentRoll))
			{
				notifyObservers();
			}*/
			
			if((Math.abs(currentZAngle - lastZAngle) > 0.5))
			{
				//System.out.println("Yaw Changed more than....");
				//System.out.println("Last: " + lastZAngle + " Current: " + currentZAngle ) ;
			}
			
			/*if((lastYaw == currentYaw) || ((currentYaw + 1) <= lastYaw) || (lastYaw >= (currentYaw - 1)))
			{
				System.out.println("Yaw Changed....");
				System.out.println("Last: " + lastYaw + " Current: " + currentYaw ) ;
			}*/
			
			if(lastYaw != currentYaw)
			{
				//System.out.println("Yaw Changed....");
			}
			
			if(lastPitch != currentPitch)
			{
				//System.out.println("Pitch Changed....");
			}
			
			///System.out.println("Yaw: " + getYaw());
			
			if(lastRoll != currentRoll)
			{
				//System.out.println("Roll Changed....");
			}

			lastZAngle = currentZAngle;
			lastYaw = currentYaw;
			lastPitch = currentPitch;
			lastRoll = currentRoll;
			lastVelocityX = currentVelocityX;
			lastVelocityY = currentVelocityY;
			lastVelocityZ = currentVelocityZ;
			
//			lastTimeStamp = currentTimeStamp;
			lastTimeStampMillis = currentTimeStampMillis;

			// To do: need to make sure NavX sampling update rate
			Timer.delay(timePerLoop);
		}
	}
}
