
import java.util.Random;
import lejos.nxt.Battery;
import lejos.nxt.LCD;

public class RobotController {

	private static final Random RAND = new Random();
	private static final int MAX_LINES = 8;
	private static final int MAX_COLUMNS = 16;
	private static final int MAX_MILLI_VOLTAGE = 1500 * 6;

	/**
	 * Diese Funktion lässt Text in eine gewünschte Richtung über den Bildschirm "fahren".
	 *
	 * @param text      Der Text, welcher über den Bildschirm laufen soll.
	 * @param direction Die Richtung, in die der Text "fahren" soll.
	 *
	 * <table border="1">
	 * <tr>
	 * <th><b>Char</b></th>
	 * <th><b>Richtung</b></th>
	 * </tr>
	 * <tr>
	 * <td>l</td>
	 * <td>Links -> Rechts</td>
	 * </tr>
	 * <tr>
	 * <td>r</td>
	 * <td>Rechts -> Links</td>
	 * </tr>
	 * <tr>
	 * <td>t</td>
	 * <td>Oben -> Unten</td>
	 * </tr>
	 * <tr>
	 * <td>b</td>
	 * <td>Unten -> Oben</td>
	 * </tr>
	 * </table>
	 */
	public void marquee(String text, char direction) {

		LCD.clear();

		int start;
		int end;
		boolean vertical = false;
		switch (direction) {
			case 'l':
				start = 0;
				end = 16;
				break;
			case 'r':
				start = 16;
				end = 0;
				break;
			case 't':
				start = 0;
				end = 4;
				vertical = true;
				break;
			case 'b':
				start = 4;
				end = 0;
				vertical = true;
				break;
			default:
				System.out.println("Unbekannte Richtung!");
				return;
		}

		for (int n = start; n != end;) {
			int x;
			int y;

			if (vertical) {

				if (n == 0) {
					LCD.clear(MAX_LINES);
				} else if (n == MAX_LINES && start > end) {
					LCD.clear(0);
				} else {
					LCD.clear(n - 1);
				}

				int middlepos = (MAX_COLUMNS / 2) - (text.length() / 2);

				if (middlepos < 0) {
					middlepos = 0;
				}

				x = middlepos;
				y = n;
			} else {

				LCD.clear(MAX_LINES / 2);

				x = n;
				y = MAX_LINES / 2;
			}

			LCD.drawString(text, x, y);

			try {
				Thread.sleep(300L);
			} catch (InterruptedException ex) {
				break;
			}

			if (start > end) {
				n--;
			} else {
				n++;
			}
		}
	}

	public void showBatteryState() {
		float voltage = Battery.getVoltage();
		float percentage = ((float) Battery.getVoltageMilliVolt()) / MAX_MILLI_VOLTAGE * 100;

		LCD.clear();
		LCD.drawString("Spannung: " + (Math.round(voltage * 100d) / 100d) + " V", 0, MAX_LINES / 2 - 1);
		LCD.drawString("Ladestatus: " + (Math.round(percentage * 100d) / 100d) + " %", 0, MAX_LINES / 2);
		LCD.drawString("Wiederaufladbar: " + (Battery.isRechargeable() ? "Ja" : "Nein"), 0, MAX_LINES / 2 + 1);
	}

	public void matrix() {
		char[] letters = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

		char[][] positions = new char[MAX_COLUMNS - 1][MAX_LINES];

		for (int n = 0; n < 20; n++) {
			char letter = letters[RAND.nextInt(letters.length)];
			int column = RAND.nextInt(MAX_COLUMNS);
			positions[column][0] = letter;

			LCD.drawChar(letter, column, 0);

			for (int x = 0; x < MAX_COLUMNS; x++) {
				for (int y = MAX_LINES; y > 0; y--) {
					if (x == column && y == 0) continue;
					if (positions[x][y] == 0) continue;

					LCD.clear(x, y, 1);

					if (y + 1 < MAX_LINES) {
						LCD.drawChar(positions[x][y], x, y + 1);
						positions[x][y + 1] = positions[x][y];
						positions[x][y] = 0;
					} else {
						positions[x][y] = 0;
					}
				}
			}
		}
	}
}
