package org.usfirst.frc.team3414.teleop;

import org.usfirst.frc.team3414.actuators.ActuatorConfig;
import org.usfirst.frc.team3414.actuators.Drivetrain;
import org.usfirst.frc.team3414.autonomous.AutonStatus;
import org.usfirst.frc.team3414.sensor.ClockTimer;
import org.usfirst.frc.team3414.sensor.Gamepad;
import org.usfirst.frc.team3414.sensor.HBJoystick;
import org.usfirst.frc.team3414.sensor.IGamepad;
import org.usfirst.frc.team3414.sensor.SensorConfig;
import org.usfirst.frc.team3414.util.Status;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WrongWayTeleop implements ITeleop
{

	private HBJoystick rightJoystick;
	private HBJoystick leftJoystick;

	private IGamepad gamepad;

	private Thread driveThread;

	private boolean isRunning;

	private Drivetrain drivetrain = ActuatorConfig.getInstance().getDrivetrain();

	private double startYaw;
	private double endYaw;

	private PowerDistributionPanel pdb;

	public void init()
	{

		rightJoystick = new HBJoystick(0);
		leftJoystick = new HBJoystick(1);

		AutonStatus.getInstance().setStatus(Status.CANCELED);
		System.out.println("Killing Auton! -----------------------------------------------------");
		System.out.println("Interrupting Timer: " + ClockTimer.getInstance().interrupt());
		isRunning = false;

		gamepad = new Gamepad(2);

		driveThread = new Thread(new DriveThread());

		driveThread.start();

		isRunning = true;
		ActuatorConfig.getInstance().getLeftTalonFront().configOpenloopRamp(ActuatorConfig.getInstance().RampTimeTeleop,ActuatorConfig.getInstance().RampTimeoutMs);
		ActuatorConfig.getInstance().getRightTalonFront().configOpenloopRamp(ActuatorConfig.getInstance().RampTimeTeleop,ActuatorConfig.getInstance().RampTimeoutMs);
		
	}

	public void stop()
	{
		if (isRunning)
		{
			isRunning = false;
		}
	}

	public class DriveThread implements Runnable
	{
		public void run()
		{
			while (isRunning)
			{				
				
//				drivetrain.setSpeed(-leftJoystick.getY(), leftJoystick.getY());
				SmartDashboard.putNumber("Elevator Encoder", ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition());

				SmartDashboard.putBoolean("Wing Limit Switch", ActuatorConfig.getInstance().limitSwitchWings().isHit());
				
				SmartDashboard.putBoolean("Bottom Limit Switch Lift", ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isRevLimitSwitchClosed());
				SmartDashboard.putBoolean("Top Limit Switch Lift", ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isFwdLimitSwitchClosed());

				SmartDashboard.putBoolean("Top Limit Switch Angler", ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().isFwdLimitSwitchClosed());
				SmartDashboard.putBoolean("Bottom Limit Switch Angler", ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().isRevLimitSwitchClosed());

				SmartDashboard.putNumber("Angler Encoder", ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition());

				SmartDashboard.putNumber("NavX", SensorConfig.getInstance().getNavX().getRawYaw());
								

				 SmartDashboard.putNumber("Left Encoder - Teleop", ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection().getQuadraturePosition());// *
																														// (-0.000122));//
				 SmartDashboard.putNumber("Right Encoder - Teleop", ActuatorConfig.getInstance().getRightEncoder().getSensorCollection().getQuadraturePosition());//

//			//	 * (0.000122));
//				 System.out.println("Left Encoder Value" +
//				 ActuatorConfig.getInstance().getLeftEncoder().getSensorCollection().getQuadraturePosition());
//				 System.out.println("Right Encoder Value" +
//				 ActuatorConfig.getInstance().getRightEncoder().getSensorCollection().getQuadraturePosition());

				
				
				 if(leftJoystick.getRawButton(1))
				 {
					 double rightYJoystick = rightJoystick.getY();
					 double leftYJoystick = leftJoystick.getY();
					 double RtargetVelocity_UnitsPer100ms = rightYJoystick *4096 * 500.0 / 600;
					 double LtargetVelocity_UnitsPer100ms = rightYJoystick *4096 * 500.0 / 600;
				//
				 ActuatorConfig.getInstance().getRightTalonFront().set(ControlMode.Velocity, RtargetVelocity_UnitsPer100ms);
				 ActuatorConfig.getInstance().getLeftTalonFront().set(ControlMode.Velocity, LtargetVelocity_UnitsPer100ms);
				 ActuatorConfig.getInstance().getRightTalonFront().getSelectedSensorVelocity(ActuatorConfig.kPIDLoopIdx);
				 ActuatorConfig.getInstance().getLeftTalonFront().getSelectedSensorVelocity(ActuatorConfig.kPIDLoopIdx);
				
				 }
				 else if (leftJoystick.getY() > 0.15 || rightJoystick.getY() > 0.15 || leftJoystick.getY() < -0.15 || rightJoystick.getY() <-0.15)
				 {
					 
/* 					//self correction
					 startYaw = SensorConfig.getInstance().getNavX().getRawYaw();
					 double leftCorrect = 0;
					 double rightCorrect = 0;
				
					 if (endYaw > (startYaw))
					 {
						 //System.out.println("Veering Right Telop");
						 rightCorrect = 0.2;
					 }
					 else if (endYaw < (startYaw))
					 {
						// System.out.println("Veering Left Telop");
						 leftCorrect = 0.2;
					 }
					 else
					 {
						 leftCorrect = 0;
						 rightCorrect = 0;
					 }
*/				//			 drivetrain.setSpeed((leftJoystick.getYAxis()) + leftCorrect,(-rightJoystick.getYAxis()) + rightCorrect);
					 
				 drivetrain.setSpeed((leftJoystick.getYAxis()) ,(rightJoystick.getYAxis()));

				 endYaw = SensorConfig.getInstance().getNavX().getRawYaw();
				
		//		 SmartDashboard.putNumber("Statrt Yaw Tle", startYaw);
		//		 SmartDashboard.putNumber("End Yaw Tle", endYaw);
				 }
				
				 else
				 {
					 drivetrain.setSpeed(0);
				 }
				
				 try
				 {
				 Thread.sleep(1);
				 }
				 catch (InterruptedException e)
				 {
				 e.printStackTrace();
				 }
				

				//angler up
				if ((gamepad.getPov() == 0) || (gamepad.getPov() == 45) || (gamepad.getPov() == 315))
//				(gamepad.getButtonState(1))
//						&& ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition() < -150)
//						!(ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().isFwdLimitSwitchClosed()))
				{
					ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(.45);//0.45, 40
//					System.out.println("Angler: " + ActuatorConfig.getInstance().talonIntakeAngler()
//							.getSensorCollection().isRevLimitSwitchClosed());

				}
				
				//angler down
				else if ((gamepad.getPov() == 180) || (gamepad.getPov() == 225) || (gamepad.getPov() == 135))
//				(gamepad.getButtonState(2))
//						&& ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().getQuadraturePosition() > 1400)
//				!(ActuatorConfig.getInstance().talonIntakeAngler().getSensorCollection().isRevLimitSwitchClosed()))
				{
					ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(-.45);//0.45, 40
//					System.out.println("Angler: " + ActuatorConfig.getInstance().talonIntakeAngler()
//							.getSensorCollection().isFwdLimitSwitchClosed());
				}
				
				else if (gamepad.getButtonState(3))
				{
					ActuatorConfig.getInstance().getDrivetrain().liftToSwitchTeleop();
				}
				
				else if (gamepad.getButtonState(4))
				{
					ActuatorConfig.getInstance().getDrivetrain().liftToScaleTeleop();
				}

				else
				{
					ActuatorConfig.getInstance().getMotorIntakeAngler().setSpeed(0);
				}

				if (gamepad.getButtonState(8) && !gamepad.getButtonState(2)) //Place
				{
					// out
					ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0.38);//0.45, 0.35, 0.40
					ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0.38);//0.45, 0.35, 0.40
				}
				else if (gamepad.getButtonState(2)) //Shoot
				{
					//out
					ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0.45);
					ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0.45);
				}

				else if (gamepad.getButtonState(6))
				{
					// in
					ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(-1.0);//-1, .90
					ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(-.7);//-.25, .225
				}
				
				else if(gamepad.getButtonState(1))
					//unjammer
				{
					ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(-.50);//-1, .90
					ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(.5);
				}
				
				else
				{
					ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(0);
					ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(0);
				}

				if (gamepad.getButtonState(5) &&
						!(ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isFwdLimitSwitchClosed()))
				// top
				{
					ActuatorConfig.getInstance().getLift().setSpeed(-.75);//-55,-.45 ,-.40
					
//					System.out.println(ActuatorConfig.getInstance().getLiftTalonTwo().getSelectedSensorVelocity(0));
//					System.out.println("Quad:" + ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition());
//				
//				//motion magic	
//					 double liftGamepad = gamepad.getY();
//				//	 double targetPos = liftGamepad *4096 * 10.0;
//					 double targetPos = 29545;
//
//
//				 ActuatorConfig.getInstance().getLiftTalonTwo().set(ControlMode.MotionMagic, targetPos); 		
				
				}

				else if (gamepad.getButtonState(7) &&
						(!(ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().isRevLimitSwitchClosed())
								|| ActuatorConfig.getInstance().limitSwitchBottomLift().isHit()))
				// bottom
				{
					ActuatorConfig.getInstance().getLift().setSpeed(.55);//.45,.37, .35
					
//					System.out.println(ActuatorConfig.getInstance().getLiftTalonTwo().getSelectedSensorVelocity(0));
//					System.out.println("Quad:" + ActuatorConfig.getInstance().getLiftTalonTwo().getSensorCollection().getQuadraturePosition());
//					
////					//motion magic	
//					 double liftGamepad = gamepad.getY();
//					// double targetPos = liftGamepad *4096 * 10.0;
//					 double targetPos = 0;
//
//				 ActuatorConfig.getInstance().getLiftTalonTwo().set(ControlMode.MotionMagic, targetPos);
					
				}

				else
				{
					ActuatorConfig.getInstance().getLift().setSpeed(0);
				}

				
				//servo release
				if (gamepad.getButtonState(9))
				{
				//	ActuatorConfig.getInstance().servoWingOne().engage();
					ActuatorConfig.getInstance().getServoWingOne().setAngle(130);//140
				//	ActuatorConfig.getInstance().servoWingTwo().disengage();
					ActuatorConfig.getInstance().getServoWingTwo().setAngle(20); //180-160
				}

				else
				{
					ActuatorConfig.getInstance().getServoWingOne().disengage();
					ActuatorConfig.getInstance().getServoWingTwo().setAngle(130);
				}
				
				if ((gamepad.getButtonState(10)) && (!ActuatorConfig.getInstance().limitSwitchWings().isHit()))
					
					//lift wings
				{
					ActuatorConfig.getInstance().getDoubleMotorWings().setSpeed(-.80);//.80 before livonia gearbox changes
					
					
				}

				else
				{
					ActuatorConfig.getInstance().getDoubleMotorWings().setSpeed(0);
				}
				
//				if (ActuatorConfig.getInstance().limitSwitchBottomLift().isHit())
//				{
//					ActuatorConfig.getInstance().getMotorIntakeOne().setSpeed(-0.15);
//					ActuatorConfig.getInstance().getMotorIntakeTwo().setSpeed(-0.15);
//				}

			}
		}
	}
}
