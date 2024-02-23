package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Robot extends TimedRobot {
  public VictorSPX rf = new VictorSPX(1);
  public VictorSPX rb = new VictorSPX(2);
  public VictorSPX lf = new VictorSPX(3);
  public VictorSPX lb = new VictorSPX(4);

  public CANSparkMax rs = new CANSparkMax(5, MotorType.kBrushless);
  public CANSparkMax ls = new CANSparkMax(6, MotorType.kBrushless);
  public VictorSPX mts = new VictorSPX(7);

  Joystick driver = new Joystick(0);
  Joystick oper = new Joystick(0);

  public boolean Shooting = true;
  public boolean Sucking = false;

  @Override
  public void robotInit() {
    ls.restoreFactoryDefaults();
    rs.restoreFactoryDefaults();

  }

  public void shoot(boolean Shoot) {
    if (Shoot == true) {
      ls.set(1.0);
      rs.set(1.0);
      Timer.delay(1.5);
      mts.set(ControlMode.PercentOutput, 1.0);
    } else if (Shoot == false) {
      ls.set(-0.5);
      rs.set(-0.5);
      mts.set(ControlMode.PercentOutput, -0.5);
    }
  }

  public void arcade(double thr, double tur) {

    lb.set(ControlMode.PercentOutput, -thr + tur);
    lf.set(ControlMode.PercentOutput, -thr + tur);
    rf.set(ControlMode.PercentOutput, thr + tur);
    rb.set(ControlMode.PercentOutput, thr + tur);

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
    double thr = driver.getRawAxis(1);
    double tur = driver.getRawAxis(0);
    boolean shooting = oper.getRawButton(1);
    boolean sucking = oper.getRawButton(2);

    arcade(thr, tur);
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

    arcade(1.0, 0);
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
