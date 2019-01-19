/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3414.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import org.usfirst.frc.team3414.robot.Robot.LogRunnable;
import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.autonomous.AutonStatus;
import org.usfirst.frc.team3414.sensor.SensorConfig;
import org.usfirst.frc.team3414.util.Status;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
//import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.DriverStation;

import org.usfirst.frc.team3414.teleop.WrongWayTeleop;
import org.usfirst.frc.team3414.teleop.PacbotTeleop; //???

import org.usfirst.frc.team3414.autonomous.AutonBase;
import org.usfirst.frc.team3414.autonomous.AutonCenterSwitch;
import org.usfirst.frc.team3414.autonomous.AutonDoNothing;
//import org.usfirst.frc.team3414.autonomous.AutonFancyCenter;
//import org.usfirst.frc.team3414.autonomous.AutonGoForward;
import org.usfirst.frc.team3414.autonomous.AutonCrossAutoLine;
import org.usfirst.frc.team3414.autonomous.Position;
//import org.usfirst.frc.team3414.autonomous.AutonSideSwitch;
import org.usfirst.frc.team3414.autonomous.AutonSideSwitchNear;
import org.usfirst.frc.team3414.autonomous.AutonSideSwitchPriorityAndScale;
import org.usfirst.frc.team3414.autonomous.AutonSideSwitchAndScalePriority;
import org.usfirst.frc.team3414.autonomous.AutonSideSwitchNearOrFar;
import org.usfirst.frc.team3414.autonomous.AutonScaleNearOrFar;
import org.usfirst.frc.team3414.autonomous.AutonTest;

import edu.wpi.first.wpilibj.Spark;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot
{
	

//private WrongWayTeleop teleop;
private PacbotTeleop teleop; 
	
	Spark lights = new Spark(2);
	Command autonomousCommand;
	private SendableChooser<AutonBase> autonChooser;
	private SendableChooser<Position> positionChooser;
	
//	private Thread logThread = new Thread(new LogRunnable());
	private PowerDistributionPanel pdb;

	public void robotInit() 
	{

		lights.setSpeed(.91);	//purple lights
		
		RobotStatus.setIsRunning(true);
		System.out.println("RobotStatus.setIsRunning(true)");
		
//		CameraServer.getInstance().addAxisCamera("10.34.14.3");
		CameraServer.getInstance().startAutomaticCapture();
		
		ActuatorConfig.getInstance().init();
		SensorConfig.getInstance().init();
		System.out.println("ActuatorConfig.getInstance().init");
		
//		pdb = SensorConfig.getInstance().getPDB();
		
		teleop = new PacbotTeleop(); //???
//		teleop = new WrongWayTeleop();
		
//		logThread.start();
		
		chooseAuto();	
		
		
	}

	public void disabledInit()
	{
//		ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().setQuadraturePosition(0, 10);
	//	ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().setQuadraturePosition(0, 10);
		
		System.out.println("Disabled");
		// Mentor Francis added the next two lines to reset the encoders each time. This allows repeated testing of Auton without redeploying code
		ActuatorConfig.getInstance().getRightEncoder().getSensorCollection().setQuadraturePosition(0, 10);
		ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection().setQuadraturePosition(0, 10);
		
//		ActuatorConfig.getInstance().getRightTalonFront().setSelectedSensorPosition(0, 0, 10);
//		ActuatorConfig.getInstance().getLeftTalonFront().setSelectedSensorPosition(0, 0, 10);
		
		
		
		teleop.stop();	
}

//
	public void teleopInit()
//	public void teleopPeriodic()
	{
		RobotStatus.setIsRunning(true);
		RobotStatus.setIsAuto(false);
		RobotStatus.setIsTeleop(true);
		
		ActuatorConfig.getInstance().getDrivetrain().stopActuators();
		
//???		ActuatorConfig.getInstance().getLeftTalonFront().setNeutralMode(NeutralMode.Coast);
//		ActuatorConfig.getInstance().getRightTalonFront().setNeutralMode(NeutralMode.Coast);
//		ActuatorConfig.getInstance().getLeftTalonBack().setNeutralMode(NeutralMode.Coast);
//		ActuatorConfig.getInstance().getRightTalonBack().setNeutralMode(NeutralMode.Coast);
		
		System.out.println("Telop Running!");
		teleop.init();		
	}
	
	/**
	 * Choose's the autonomous mode
	 */
	public void chooseAuto()
	{
		System.out.println("choose Auto");
		autonChooser = new SendableChooser<AutonBase>();
		positionChooser = new SendableChooser<Position>();
		
		// To do: need to validate combination of autonChooser and positionChooser.
		// For example, AutonCenterSwitch must pair with Center position, instead of Left or Right.
		autonChooser.addObject("Do Nothing", new AutonDoNothing());
//		autonChooser.addDefault("Go Forward", new AutonGoForward());
		autonChooser.addDefault("Cross Auto Line", new AutonCrossAutoLine());//Cross the Auto Line (a.k.a Auto-Run)
//		autonChooser.addObject("Side Switch", new AutonSideSwitch());
		autonChooser.addObject("Side Switch Near", new AutonSideSwitchNear());
		autonChooser.addObject("Side Switch or Scale Near (SWITCH PRIORITY)", new AutonSideSwitchPriorityAndScale());
		autonChooser.addObject("Side Switch or Scale Near (SCALE PRIORITY)", new AutonSideSwitchAndScalePriority());
		autonChooser.addObject("Center Switch", new AutonCenterSwitch());
		//autonChooser.addObject("Fancy Center", new AutonFancyCenter());
		autonChooser.addObject("Side Switch Near or Far", new AutonSideSwitchNearOrFar()); // make sure switch
		autonChooser.addObject("Scale Near or Far", new AutonScaleNearOrFar());
		autonChooser.addObject("Auton Test", new AutonTest());
		
		SmartDashboard.putData("Autons", autonChooser);
		
		positionChooser.addDefault("Left", Position.LEFT);
		positionChooser.addObject("Center", Position.CENTER);	
		positionChooser.addObject("Right", Position.RIGHT);
		
		SmartDashboard.putData("Position", positionChooser);
		
		SmartDashboard.putBoolean("Second cube", true);

		SmartDashboard.putBoolean("Kill Switch", RobotStatus.isAuto());
	}
	
	public void autonomousInit()
	{	
		
		System.out.println(ActuatorConfig.getInstance());
		System.out.println("Auton Init");
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		autonChooser.getSelected().setGameData(gameData);
//		autonChooser.getSelected().run();
		
		RobotStatus.setIsRunning(true);
		RobotStatus.setIsAuto(true);
		RobotStatus.setIsTeleop(false);

		ActuatorConfig.getInstance().getDrivetrain().stopActuators();
		
		if(!ActuatorConfig.getInstance().getOnlyDriveTrain())
		{	 
			// please make sure that intake angler arms are in most up position
			ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().setQuadraturePosition(0, 10);
			// please make sure that lift is at bottom position
			ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().setQuadraturePosition(0, 10);
		}

		ActuatorConfig.getInstance().getRightEncoder().getSensorCollection().setQuadraturePosition(0, 10);
		ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection().setQuadraturePosition(0, 10);

		ActuatorConfig.getInstance().getLeftTalonFront().setNeutralMode(NeutralMode.Brake);
		ActuatorConfig.getInstance().getRightTalonFront().setNeutralMode(NeutralMode.Brake);
		ActuatorConfig.getInstance().getLeftTalonBack().setNeutralMode(NeutralMode.Brake);
		ActuatorConfig.getInstance().getRightTalonBack().setNeutralMode(NeutralMode.Brake);
		
		AutonStatus.getInstance().setStatus(Status.RUNNING);
		
		SensorConfig.getInstance().getNavX().reset();
		SensorConfig.getInstance().getNavX().resetLastRawYaw();
		
//		  gameData = LLL or LRL or RRR or RLR For testing just comment the other gameData.
		System.out.println(autonChooser.getSelected());
//		
		autonChooser.getSelected().doAuto(positionChooser.getSelected());
//		autonomousCommand = (Command) autonChooser.getSelected();
//		autonomousCommand = (Command) positionChooser.getSelected();
//		autonomousCommand.start();
	}
	
//	public class LogRunnable implements Runnable
//	{
//		public void run()
//		{
//			while(RobotStatus.isRunning())
//			{
//				System.out.println("Battery Voltage: " + pdb.getVoltage());
//			}
//		}
//	}
}
