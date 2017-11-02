
public class Main {

	public static void main(String[] args) {
		RobotController controller = new RobotController();

		//Wir definieren ein Char array mit unseren Richtungen.
		char[] directions = new char[] { 'l', 'r', 't', 'b' };

		//In einer Foreach-Schleife. Lassen wir das Wort "Test" einmal in jede Richtung fahren. 
		for (char c : directions) {
			controller.marquee("Test", c);
		}

	}

}
