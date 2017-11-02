
public class Main {

	public static void main(String[] args) {
		RobotController controller = new RobotController();

		testMatrix(controller);
	}

	private static void testMarquee(RobotController controller) {
		char[] directions = new char[] { 'l', 'r', 't', 'b' };

		for (char c : directions) {
			controller.marquee("Test", c);
		}
	}

	private static void testBatteryStatus(RobotController controller) {
		controller.showBatteryState();

		try {
			Thread.sleep(2000L);
		} catch (InterruptedException ex) {
			//Nothing
		}
	}

	private static void testMatrix(RobotController controller) {
		controller.matrix();
	}

}
