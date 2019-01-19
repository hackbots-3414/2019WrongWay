package org.usfirst.frc.team3414.sensor;

public class ClockTimer {
private static ClockTimer instance;
	
	private ClockTimer(){}
	
	public static ClockTimer getInstance()
	{
		if(instance == null)
		{
			instance = new ClockTimer();
		}
		
		return instance;
	}

	public void waitTimeInMillis(double milliseconds)
	{
		// Timer.delay(milliseconds / 1000);
		try
		{
			Thread.sleep((long) milliseconds);
		} catch (InterruptedException e)
		{
			System.err.println("Interrupted Timer! ---------------------");
			e.printStackTrace();
		}
	}

	public void waitTimeInSeconds(double seconds)
	{
		// Timer.delay(seconds);
		this.waitTimeInMillis(seconds * 60);
	}

	public void waitTimeInMinutes(double minutes)
	{
		// Timer.delay(minutes * 60);
		this.waitTimeInMillis(minutes * 3600);
	}
	
	public boolean interrupt()
	{
		Thread.currentThread().interrupt();
		return Thread.interrupted();
	}
}
