
public class Main {

	public static void main(String[] args) {
		RobotController controller = new RobotController();

		char[] directions = new char[] { 'l', 'r', 't', 'b' };

		for (char c : directions) {
			controller.marquee("Test", c);
		}

	}

}
