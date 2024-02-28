package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Robot extends TimedRobot {
  public VictorSPX rf = new VictorSPX(1);
  public VictorSPX rb = new VictorSPX(2);
  public VictorSPX lf = new VictorSPX(3);
  public VictorSPX lb = new VictorSPX(4);

  public CANSparkMax rs = new CANSparkMax(6, MotorType.kBrushless);
  public CANSparkMax ls = new CANSparkMax(5, MotorType.kBrushless);
  public VictorSPX mts = new VictorSPX(7);

  public VictorSPX cr = new VictorSPX(8);
  public VictorSPX cl = new VictorSPX(9);

  UsbCamera camera1;
  UsbCamera camera2;

  XboxController driver = new XboxController(0); 
  //Joystick driver = new Joystick(0);
  Joystick oper = new Joystick(1);

  public boolean Shooting = true;
  public boolean Sucking = false;

  @Override
  public void robotInit() {
    ls.restoreFactoryDefaults();
    rs.restoreFactoryDefaults();
    
    camera1 = CameraServer.startAutomaticCapture(0);
    camera2 = CameraServer.startAutomaticCapture(1);

  }


  public void climb(){

    cr.set(ControlMode.PercentOutput, 1.0);
    cl.set(ControlMode.PercentOutput, -1.0);
    Timer.delay(0.1); // dont turn off if not this 
    cr.set(ControlMode.PercentOutput, 0.0);
    cl.set(ControlMode.PercentOutput, 0.0);

  }

  public void shoot(boolean Shoot) {
    if (Shoot == true) {
      ls.set(-1.0);
      rs.set(1.0);
      Timer.delay(1.0);
      mts.set(ControlMode.PercentOutput, -1.0);
      Timer.delay(0.75);
      ls.set(0);
      rs.set(0);
      mts.set(ControlMode.PercentOutput, 0);
    } else if (Shoot == false) {
      ls.set(0.5);
      rs.set(-0.5);
      mts.set(ControlMode.PercentOutput, 0.5);
      Timer.delay(0.3);
      ls.set(0);
      rs.set(0);
      mts.set(ControlMode.PercentOutput, 0);
    }
  }

  public void tank(double r, double l){

   if (r < 0.2 && r > 0 ){r = 0;}
   else if (r > -0.2 && r < 0){ r = 0; }
 if (l < 0.2 && l > 0 ){l = 0;}
   else if (l > -0.2 && l < 0){ l = 0; }

    lb.set(ControlMode.PercentOutput, l);
    lf.set(ControlMode.PercentOutput, l);
    rf.set(ControlMode.PercentOutput, -r);
    rb.set(ControlMode.PercentOutput, -r);
  }


public void arcadeJ(double thr, double tur) {
   if (thr < 0.2 && thr > 0 ){thr = 0;}
   else if (thr > -0.2 && thr < 0){ thr = 0; }
 if (tur < 0.2 && tur > 0 ){tur = 0;}
   else if (tur > -0.2 && tur < 0){ tur = 0; }

    lb.set(ControlMode.PercentOutput, thr + tur);
    lf.set(ControlMode.PercentOutput, thr + tur);
    rf.set(ControlMode.PercentOutput, -thr + tur);
    rb.set(ControlMode.PercentOutput, -thr + tur);

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    shoot(Shooting);
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {


    double thrR = -driver.getRawAxis(1);
    double thrL = -driver.getRawAxis(5);
        
   //double thr = -driver.getRawAxis(1);
   //double tur = driver.getRawAxis(4);

   // boolean shooting = driver.getRawButton(1);
    //boolean sucking = driver.getRawButton(2);
    boolean sucking = oper.getRawButton(1);
    boolean shooting = oper.getRawButton(2);
    boolean climbing = oper.getRawButton(3);

   tank(thrR, thrL);
   // arcade(thr, tur);
   if (climbing) {
    climb();
   }
    if (shooting) {
      shoot(Shooting);
    } else if (sucking) {
      shoot(Sucking);
    }
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {

    tank(1.0, 1.0);
    shoot(Sucking);
    System.out.println("test active");
  }

  @Override
  public void testPeriodic() {

  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
