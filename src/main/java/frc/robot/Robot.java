// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Added imports below
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
//import edu.wpi.first.wpilibj.motorcontrol.MotorController;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; //terminating
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kSpeakermiddle = "Speaker Middle and Backup";
  private static final String kSpeakerLong = "Far Speaker Shot and Backup";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

private final PWMSparkMax leftfront = new PWMSparkMax(3); //Motor 4
private final PWMSparkMax leftrear = new PWMSparkMax(2);  //Motor 3
private final PWMSparkMax rightfront = new PWMSparkMax(1);//Motor 2
private final PWMSparkMax rightrear = new PWMSparkMax(0); //Motor 1





//public void addFollower​(PWMMotorController follower)

//private final MotorControllerGroup leftmotors = new MotorControllerGroup(leftfront, leftrear);
//private final MotorControllerGroup rightmotors = new MotorControllerGroup(rightfront, rightrear);
private final DifferentialDrive myDrive = new DifferentialDrive(leftfront,rightfront);

private final PWMSparkMax feedwheel = new PWMSparkMax(5); //Motor 6
private final PWMSparkMax launchwheel = new PWMSparkMax(4);//Motor 5

private final Timer timer1 = new Timer();
private final Timer timer2 = new Timer();

private final Joystick driver = new Joystick(1);
private final XboxController operator = new XboxController(0);

double drivelimit = 1;

double launchpower = 0;
double feedpower = 0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
 
   //use insertmotorname.setinverted(isinverted:true) below to reverse motor direction
 
   @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Speaker Middle and Backup", kSpeakermiddle);
    m_chooser.addOption("Far Speaker Shot and Backup", kSpeakerLong);
    SmartDashboard.putData("Auto choices", m_chooser);

    timer1.start();
    timer2.start();

    feedwheel.setInverted(true);
    launchwheel.setInverted(true);

    leftfront.setInverted(true);
    leftrear.setInverted(true);
    rightfront.setInverted(true);
    rightrear.setInverted(true);

   leftrear.addFollower(leftfront);
   rightrear.addFollower(rightfront);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() { 
    switch (m_autoSelected) {

      case kSpeakerLong:
        if (timer1.get() < 3){
          launchwheel.set(1);
        }

        else if (timer1.get() < 5){
          launchwheel.set(1);
          feedwheel.set(1);
        }

        else if (timer1.get() > 5){
          launchwheel.set(0);
          feedwheel.set(0);
          myDrive.tankDrive(-.5, -.5);
        }
        else if (timer1.get()< 6.5){
          launchwheel.set(0);
          feedwheel.set(0);
          myDrive.tankDrive(-.5, -.5);
        }

        else if (timer1.get() < 7.5){
          launchwheel.set(0);
          feedwheel.set(0);
          myDrive.tankDrive(.5, -.5);
        }

        else if (timer1.get() < 9.5){
          launchwheel.set(0);
          feedwheel.set(0);
          myDrive.tankDrive(-.5, -.5);
        }

        else{
          launchwheel.set(0);
          feedwheel.set(0);
          myDrive.tankDrive(0, 0);
        }
      break;
        
      case kSpeakermiddle:   //shoot note and cross line
        
        if (timer1.get() < 3){
          launchwheel.set(1);
        }

        else if (timer1.get() < 5){
          launchwheel.set(1);
          feedwheel.set(1);
        }

        else if (timer1.get() > 5){
          launchwheel.set(0);
          feedwheel.set(0);
          myDrive.tankDrive(-.5, -.5);
        }
        else if (timer1.get()< 6.5){
          launchwheel.set(0);
          feedwheel.set(0);
          myDrive.tankDrive(-.5, .5);
        }

        else{
          launchwheel.set(0);
          feedwheel.set(0);
          myDrive.tankDrive(0, 0);
        }

        break;
      case kDefaultAuto: //back up
      default:
        // Put default auto code here
        
        if(timer1.get()<2.0){
        myDrive.tankDrive(-0.5,-0.5);
        }
        else{
          myDrive.tankDrive(0,0);
        }
        
        break;
    }
    }
   

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  

    if (driver.getTriggerPressed() == true){
      drivelimit = 1;
    }
    
    //slower drive
    if (driver.getTriggerReleased() == true){
      drivelimit = 0.5;
    }
    
    //set joystic movement to driving wheels ant then apply scale factor "drivelimit"
    //getX() calls for the x-direction of the joystic
    myDrive.arcadeDrive(-driver.getX()*drivelimit, -driver.getY()*drivelimit);

  
    if (operator.getAButton()){  //Feed
     launchpower = 0.5;
     feedpower = 0.5;
    }
  
else {
  
     if (operator.getYButtonPressed()){  //Launch
      timer1.reset();
    }
    else if (timer1.get() < 1.0){
      launchpower = -1;
      feedpower = 0;
    }
    else if (timer1.get() < 2.0){
      launchpower = -1;
      feedpower = -1;
    }

    else if (operator.getXButtonPressed()){  //amp load
      timer2.reset();
    }
    else if (timer2.get() < 1.0){
      launchpower = -0.29;
      feedpower = -0.6;
    }
    else{
      launchpower = 0;
      feedpower = 0;
    }
  }
    //set constants, that change, to motor controllers  
    launchwheel.set(launchpower);
    feedwheel.set(feedpower);
  
  
  

  } 

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
