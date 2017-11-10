
import lejos.nxt.Battery;

public class DriveUtil {

  private static final double DIAMETER = 56D;
  private static final double SPACING = 117D;

  public static int getRotationValueByAngle(double angle) {
    double distance = 2D * Math.PI * SPACING * angle / 360D;
    double rotation = Math.PI * DIAMETER;

    return (int) (360D * distance / rotation);
  }

  public static int getRotationValue(double value, MetricSystem unit) {
    double extend = Math.PI * DIAMETER;

    //System.out.println(unit.toMillimeters(value));

    return (int) ((unit.toMillimeters(value) / extend) * 360);
  }

  public static void rotatePoint(MotorPair pair, double angle, int speed, boolean immediateReturn) {
    angle = angle / 2;
    int valueLeft = -getRotationValueByAngle(angle);
    int valueRight = -valueLeft;

    pair.setSpeed(getMaxSpeed(speed));

    pair.getLeftMotor().rotate(valueLeft, true);
    pair.getRightMotor().rotate(valueRight, immediateReturn);
  }

  public static void driveForward(MotorPair pair, double value, MetricSystem unit, int speed, boolean immediateReturn) {
    pair.setSpeed(getMaxSpeed(speed));
    pair.rotate(getRotationValue(value, unit), immediateReturn);
  }

  private static int getMaxSpeed(int speed) {
    return (int) Math.min(Battery.getVoltage() * 1000, speed);
  }

}
