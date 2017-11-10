
public enum MetricSystem {

	MILLIMETERS,
	CENTIMETERS,
	METERS,
	KILOMETERS;

	public double toKilometers(double value) {
		switch (this) {
			case MILLIMETERS:
				return value / 1000000;
			case CENTIMETERS:
				return value / 100000;
			case METERS:
				return value / 1000;
			default:
				return value;
		}
	}

	public double toMeters(double value) {
		switch (this) {
			case MILLIMETERS:
				return value / 1000;
			case CENTIMETERS:
				return value / 100;
			case KILOMETERS:
				return value * 1000;
			default:
				return value;
		}
	}

	public double toCentimeters(double value) {
		switch (this) {
			case MILLIMETERS:
				return value / 10;
			case METERS:
				return value * 100;
			case KILOMETERS:
				return value * 100000;
			default:
				return value;
		}
	}

	public double toMillimeters(double value) {
		switch (this) {
			case CENTIMETERS:
				return value * 10;
			case METERS:
				return value * 1000;
			case KILOMETERS:
				return value * 1000000;
			default:
				return value;
		}
	}

}
