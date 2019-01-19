package org.usfirst.frc.team3414.sensor;
import org.usfirst.frc.team3414.sensor.ClockTimer;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;

public class SensorConfig {
	private static SensorConfig instance;
	
	private NavX navX;
//	private NavXThread navXThread;
	
	private ClockTimer timer;
	
	private PowerDistributionPanel pdb;
	
	private SensorConfig(){}
	
	public void init()
	{
		byte update_rate_hz = 100;
		navX = new NavX(new AHRS(SPI.Port.kMXP));
//		navX = new NavX(new AHRS(SPI.Port.kMXP), update_rate_hz);
		// To do: eventually, only use either NavX or NavXThread
//		navXThread = new NavXThread(new AHRS(SPI.Port.kMXP));
		
		timer = ClockTimer.getInstance();
		
		pdb = new PowerDistributionPanel(8);
	}
	
	public static SensorConfig getInstance()
	{
		if(instance == null)
		{
			instance = new SensorConfig();
		}
		
		return instance;
	}
	
	public NavX getNavX()
	{
		return navX;
	}
	
//	public NavXThread getNavXThread()
//	{
//		return navXThread;
//	}
	
	public ClockTimer getTimer()
	{
		return timer;
	}
	
	public PowerDistributionPanel getPDB()
	{
		return pdb;
	}

}
