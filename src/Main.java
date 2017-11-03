
import lejos.nxt.Button;

public class Main {

  public static void main(String[] args) {
    RobotController controller = new RobotController();

	  Button.ESCAPE.waitForPressAndRelease();

	  testButtons(controller);
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

	private static void testButtons(RobotController controller) {
		boolean escOnce = false;
		while (!Button.ENTER.isDown()) {
			if (Button.ESCAPE.isDown()) {
				if (escOnce) {
					controller.matrix();
					escOnce = false;
				} else {
					controller.marquee("Test", 'b');
					escOnce = true;
				}

				continue;
			}

			if (Button.LEFT.isDown()) {
				controller.marquee("Text", 'r');
				continue;
			}

			if (Button.RIGHT.isDown()) {
				controller.marquee("Text", 'l');
				continue;
			}
		}
	}

}
