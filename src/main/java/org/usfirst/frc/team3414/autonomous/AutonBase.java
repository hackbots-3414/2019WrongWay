package org.usfirst.frc.team3414.autonomous;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.actuators.Drivetrain;
import org.usfirst.frc.team3414.autonomous.Position;
import org.usfirst.frc.team3414.sensor.SensorConfig;
import org.usfirst.frc.team3414.sensor.NavX;
//import org.usfirst.frc.team3414.sensor.NavXThread;
import org.usfirst.frc.team3414.robot.RobotStatus;

import edu.wpi.first.wpilibj.Timer;

public abstract class AutonBase implements Runnable 
{
	
	protected Drivetrain driveTrain = ActuatorConfig.getInstance().getDrivetrain();
	protected Timer timer = new Timer();
	
	protected String gameData;
	protected String caption; // name on GUI

	public AutonBase()
	{
		//this.gameData = gameData;	
	}
	
	protected NavX navX = SensorConfig.getInstance().getNavX();
//	protected NavXThread navXThread = SensorConfig.getInstance().getNavXThread();
	
	private Thread autonThread;
	
	private Position position;
	
	public void doAuto(Position position)
	{
		this.position = position;
		navX.reset();
//		navXThread.reset();
		autonThread = new Thread(this);
		autonThread.start();
	}
		
	protected abstract void left();
	
	protected abstract void center();
	
	protected abstract void right();
	
//	protected abstract void setCaption();

	public void setGameData(String gameDataInput) 
	{
		this.gameData = gameDataInput;
		System.out.println(gameDataInput);
	}
	
	public String getGameData() 
	{
		return this.gameData;
	}
	
	public String getCaption() 
	{
		return this.caption;
	}
	
	public void run()
	{
		SimpleDateFormat ft = new SimpleDateFormat("E yyyy-MM-dd 'at' hh:mm:ss a zzz");
		Date dateNow = new Date(); 
		System.out.println("Date & Time: " + ft.format(dateNow));
		System.out.println("Running Auton Thread----------------------------------------");
		System.out.println(position);
		if(RobotStatus.isAuto())
		{

			switch(position)
			{
				case LEFT:
				left();
				break;
				
				case CENTER:
				center();
				break;
				
				case RIGHT:
				right();
				break;
				
			}		
		}
		
		System.out.println("Auton Thread Completed----------------------------------------");
	}
}

