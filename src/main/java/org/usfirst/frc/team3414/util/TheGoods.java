package org.usfirst.frc.team3414.util;
import org.usfirst.frc.team3414.sensor.NavX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;

public class TheGoods
{
//	TalonSRX talonRightOne = new TalonSRX(1);
//	TalonSRX _talonRightTwo = new TalonSRX(0);
//	TalonSRX _talonRightThree = new TalonSRX(2);
//	
//	TalonSRX talonLeftOne = new TalonSRX(3);
//	TalonSRX _talonLeftTwo = new TalonSRX(4);
//	TalonSRX _talonLeftThree = new TalonSRX(5);
//	Joystick _joy = new Joystick(0);
//	Joystick _joyTwo = new Joystick(1);
//	StringBuilder _sb = new StringBuilder();
//	int _loops = 0;
//
//	private NavX navX = new NavX(new AHRS(SPI.Port.kMXP));
//	
//	private double startAngle;
//	
//	private double endAngle;
//	
//	private double theta;
//	
//	public static final int kTimeoutMs = 10;
//	public static final int kPIDLoopIdx = 0;
//	
//	public static final double RampTime =  1;//0.5 for teleop
//	public static final int RampTimeoutMs = 20000;
//	
//	public void robotInit()
//	{
//		talonRightOne.setSensorPhase(true);
//		talonLeftOne.setSensorPhase(true);
//		
//		talonLeftOne.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
//
//		talonRightOne.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
//		
//		talonLeftOne.configOpenloopRamp(RampTime,RampTimeoutMs);
//		talonRightOne.configOpenloopRamp(RampTime, RampTimeoutMs);
//		
////		talonRightOne.configNominalOutputForward(0, Constants.kTimeoutMs);
////		talonRightOne.configNominalOutputReverse(0, Constants.kTimeoutMs);
////		talonRightOne.configPeakOutputForward(1, Constants.kTimeoutMs);
////		talonRightOne.configPeakOutputReverse(-1, Constants.kTimeoutMs);
////		talonLeftOne.configNominalOutputReverse(0, Constants.kTimeoutMs);
////		talonLeftOne.configPeakOutputForward(1, Constants.kTimeoutMs);
////		talonLeftOne.configPeakOutputReverse(-1, Constants.kTimeoutMs);
////		
////		talonRightOne.config_kF(Constants.kPIDLoopIdx, 0.09366, Constants.kTimeoutMs);//0.09053
////		talonRightOne.config_kP(Constants.kPIDLoopIdx, 1.2, Constants.kTimeoutMs);
////		talonRightOne.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
////		talonRightOne.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
////		talonLeftOne.config_kF(Constants.kPIDLoopIdx, 0.08999, Constants.kTimeoutMs);//3.9346
////		talonLeftOne.config_kP(Constants.kPIDLoopIdx, 0.365357, Constants.kTimeoutMs);
////		talonLeftOne.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
////		talonLeftOne.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
////		startAngle = navX.getYaw();
//		
//		this._talonRightTwo.set(ControlMode.Follower, this.talonRightOne.getDeviceID());
//		this._talonRightThree.set(ControlMode.Follower, this._talonRightTwo.getDeviceID());
//		
//		this._talonLeftTwo.set(ControlMode.Follower, this.talonLeftOne.getDeviceID());
//		this._talonLeftThree.set(ControlMode.Follower, this._talonLeftTwo.getDeviceID());
//	}
//
//	/**
//	 * This function is called periodically during operator control
//	 */
//	public void teleopPeriodic()
//	{
//		double leftYstick = _joy.getY();	
////		double leftRstick = _joyTwo.getY();	
//		
//		talonLeftOne.set(ControlMode.PercentOutput, -leftYstick);
//		talonRightOne.set(ControlMode.PercentOutput, leftYstick);
////		talonRightOne.set(ControlMode.PercentOutput, leftRstick);
//		
////		+
////	
////		/* get gamepad axis */
////		
////		double motorOutput = _talonRightOne.getMotorOutputPercent();
////		/* prepare line to print */
////		_sb.append("\tout:");
////		_sb.append(motorOutput);
////		_sb.append("\tspd:");
////		_sb.append(_talonRightOne.getSelectedSensorVelocity(Constants.kPIDLoopIdx));
////
////		if (_joy.getRawButton(1)) {
////			/* Speed mode */
////			/* Convert 500 RPM to units / 100ms.
////			 * 4096 Units/Rev * 500 RPM / 600 100ms/min in either direction:
////			 * velocity setpoint is in units/100ms
////			 */
////			double targetVelocity_UnitsPer100ms = leftYstick * 500.0 * 8192 / 600;
////			/* 500 RPM in either direction */
////			_talonRightOne.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
////			_talonLeftOne.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
////
////			/* append more signals to print when in speed mode. */
////			_sb.append("\terr:");
////			_sb.append(_talonRightOne.getClosedLoopError(Constants.kPIDLoopIdx));
////			_sb.append("\ttrg:");
////			_sb.append(targetVelocity_UnitsPer100ms);
////		}
////		else  if(_joy.getRawButton(3))
////		{
////			startAngle = navX.getYaw();
////		}
////		else
////		{
////			/* Percent voltage mode */
////			_talonRightOne.set(ControlMode.PercentOutput, leftYstick);
////			_talonLeftOne.set(ControlMode.PercentOutput, leftYstick);
////			//_talonLeftOne.set(ControlMode.PercentOutput, leftYstick);
////		//	System.out.println("Else------------------------------------");
////		}
////
////		if (++_loops >= 10) {
////			_loops = 0;
////			System.out.println(_sb.toString());
////		}
////		_sb.setLength(0);
////		
////		endAngle = navX.getYaw();
////		
////		theta = endAngle - startAngle;
////		
////		SmartDashboard.putNumber("Theta", theta);
//	}

}
