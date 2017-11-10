
public class DrivePattern {

	private final MotorPair pair;

	public DrivePattern(MotorPair pair) {
		this.pair = pair;
	}

	public void driveCross() {
		for (int n = 0; n < 4; n++) {
			this.driveForwardBackward(1, MetricSystem.METERS, 1000);
			DriveUtil.rotatePoint(pair, -90, 1000, false);
		}
	}

	public void driveZigZag() {
		for (int i = 0; i < 1; i++) {
			for (int n = 0; n <= 4; n++) {
				if (n == 4) {
					DriveUtil.rotatePoint(pair, -90, 1000, false);
					break;
				}

				DriveUtil.driveForward(pair, 100, MetricSystem.CENTIMETERS, 1000, false);

				if (n < 2)
					DriveUtil.rotatePoint(pair, 90, 1000, false);
				else
					DriveUtil.rotatePoint(pair, -90, 1000, false);
			}
		}

	}

	private void driveForwardBackward(double value, MetricSystem unit, int speed) {
		DriveUtil.driveForward(pair, value, unit, speed, false);
		DriveUtil.rotatePoint(pair, 180, speed, false);
		DriveUtil.driveForward(pair, value, unit, speed, false);

	}

}
