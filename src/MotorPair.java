
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;

public class MotorPair implements RegulatedMotor {

	private final NXTRegulatedMotor leftMotor;
	private final NXTRegulatedMotor rightMotor;

	public MotorPair(NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}

	public NXTRegulatedMotor getLeftMotor() {
		return leftMotor;
	}

	public NXTRegulatedMotor getRightMotor() {
		return rightMotor;
	}

	@Override
	public void addListener(RegulatedMotorListener listener) {
		this.leftMotor.addListener(listener);
		this.rightMotor.addListener(listener);
	}

	@Override
	public RegulatedMotorListener removeListener() {
		throw new UnsupportedOperationException("This operation is not supported!");
	}

	@Override
	public void stop(boolean immediateReturn) {
		this.leftMotor.stop(true);
		this.rightMotor.stop(immediateReturn);
	}

	@Override
	public void flt(boolean immediateReturn) {
		this.leftMotor.flt();
		this.rightMotor.flt();
	}

	@Override
	public void waitComplete() {
		this.leftMotor.waitComplete();
		this.rightMotor.waitComplete();
	}

	@Override
	public void rotate(int angle, boolean immediateReturn) {
		this.leftMotor.rotate(angle, true);
		this.rightMotor.rotate(angle, immediateReturn);
	}

	@Override
	public void rotate(int angle) {
		this.rotate(angle, false);
	}

	@Override
	public void rotateTo(int limitAngle) {

	}

	@Override
	public void rotateTo(int limitAngle, boolean immediateReturn) {
		this.leftMotor.rotateTo(limitAngle, true);
		this.rightMotor.rotateTo(limitAngle, immediateReturn);
	}

	@Override
	public int getLimitAngle() {
		throw new UnsupportedOperationException("This operation is not supported!");
	}

	@Override
	public void setSpeed(int speed) {
		this.leftMotor.setSpeed(speed);
		this.rightMotor.setSpeed(speed);
	}

	@Override
	public int getSpeed() {
		throw new UnsupportedOperationException("This operation is not supported!");
	}

	@Override
	public float getMaxSpeed() {
		return Math.min(this.leftMotor.getMaxSpeed(), this.rightMotor.getMaxSpeed());
	}

	@Override
	public boolean isStalled() {
		throw new UnsupportedOperationException("This operation is not supported!");
	}

	@Override
	public void setStallThreshold(int error, int time) {
		this.leftMotor.setStallThreshold(error, time);
		this.rightMotor.setStallThreshold(error, time);
	}

	@Override
	public void setAcceleration(int acceleration) {
		this.leftMotor.setAcceleration(acceleration);
		this.rightMotor.setAcceleration(acceleration);
	}

	@Override
	public void forward() {
		this.leftMotor.forward();
		this.rightMotor.forward();
	}

	@Override
	public void backward() {
		this.leftMotor.backward();
		this.rightMotor.backward();
	}

	@Override
	public void stop() {
		this.leftMotor.stop(true);
		this.rightMotor.stop();
	}

	@Override
	public void flt() {
		this.leftMotor.flt(true);
		this.rightMotor.flt();
	}

	@Override
	public boolean isMoving() {
		throw new UnsupportedOperationException("This operation is not supported!");
	}

	@Override
	public int getRotationSpeed() {
		throw new UnsupportedOperationException("This operation is not supported!");
	}

	@Override
	public int getTachoCount() {
		throw new UnsupportedOperationException("This operation is not supported!");
	}

	@Override
	public void resetTachoCount() {
		throw new UnsupportedOperationException("This operation is not supported!");
	}

	public static MotorPair get(MotorPort left, MotorPort right) {
		return new MotorPair(new NXTRegulatedMotor(left), new NXTRegulatedMotor(right));
	}
}
