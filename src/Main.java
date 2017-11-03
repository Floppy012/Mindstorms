
import lejos.nxt.Button;

public class Main {

	public static void main(String[] args) {
		RobotController controller = new RobotController();

		Button.ESCAPE.waitForPressAndRelease();

		testButtons(controller);
	}

	private static void testMarquee(RobotController controller) {
		//Wir definieren ein Char array mit unseren Richtungen.
		char[] directions = new char[] { 'l', 'r', 't', 'b' };

		//In einer Foreach-Schleife. Lassen wir das Wort "Test" einmal in jede Richtung fahren. 
		for (char c : directions) {
			controller.marquee("Test", c);
		}
	}

	private static void testBatteryStatus(RobotController controller) {
		controller.showBatteryState();

		//Wir warten für zwei Sekunden, damit man sich den kram auch aufm Display anschauen kann.
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
		//Wir definieren ein boolean mit dem wir feststellen, ob die escape taste das 2. Mal gedrückt wird
		boolean escOnce = false;

		//Solange die Enter Taste nicht gedrückt wird, soll die while-schleife laufen
		while (!Button.ENTER.isDown()) {

			//Wenn die Escape taste gedrückt wird, dann soll folgende Aktion ausgeführt werden
			if (Button.ESCAPE.isDown()) {
				//Wenn escOnce true ist, dann wurde der Button vorher bereits einmal gedrückt, also machen wir die Matrix
				if (escOnce) {
					controller.matrix();

					//Da beim nächtsten Mal drücken wieder Marquee ausgeführt werden soll, setzen wir den Wert wieder auf false
					escOnce = false;
				} else {
					//Ist escOnce nicht true, machen wir ein Marquee
					controller.marquee("Test", 'b');

					//Wir setzen escOnce auf true, damit beim nächsten Mal drücken wieder der Matrix effekt ausgeführt wird
					escOnce = true;
				}

				//Da wir keine weiteren Aktionen mehr machen müssen, brechen wir hier ab und lassen die Schleife von vorn beginnen.
				continue;
			}

			//Wenn die linke Taste gedrückt wird, soll ein Text von rechts nach links scrollen
			if (Button.LEFT.isDown()) {
				controller.marquee("Text", 'r');
				continue;
			}

			//Wenn die rechte Taste gedrückt wird, soll ein Text von links nach rechts scrollen
			if (Button.RIGHT.isDown()) {
				controller.marquee("Text", 'l');
				continue;
			}
		}
	}

}
