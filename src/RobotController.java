
import java.util.Random;
import lejos.nxt.Battery;
import lejos.nxt.LCD;

public class RobotController {

	/*
		Hier definieren wir unsere maximale Zeilen und Spalten Anzahl des Displays.
		Es ist besser diese Werte, die sich nicht währen der Laufzeit unseres Codes
		verändern, in Konstanten zu schreiben, da man so einen einfacheren Überblick
		behält und es am Ende für Entwickler einfacher ist, die Werte anzupassen.
		(Sollte sich z.B. das Display vergrößern oder verkleinern).
	 */
	private static final Random RAND = new Random();
	private static final int MAX_LINES = 8;
	private static final int MAX_COLUMNS = 16;
	private static final int MAX_MILLI_VOLTAGE = 1500 * 6;

	/**
	 * Dies ist ein JavaDoc comment (keine Ahnung, ob wir das in der Schule noch dran nehmen). Dieser comment wird genutzt
	 * um eine Funktion und ihre Parameter zu beschreiben. Innerhalb dieser comments kann man auch HTML nutzen.
	 *
	 * Diese Funktion lässt Text in eine gewünschte Richtung über den Bildschirm "fahren". Sie heißt "marquee", da die Funktion
	 * in HTML auch so heißt. Warum die, das "marquee" genannt haben, weiß ich nicht.
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

		//Wir leeren das komplette Display, da wir nicht wissen, was bzw. wo vorher etwas stand.
		LCD.clear();

		//Variablen deklarieren
		int start;
		int end;
		boolean vertical = false; //Diese Variable entscheidet, ob es von unten -> oben bzw. oben -> unten oder von links -> rechts bzw. rechts -> links geht

		//Wir entscheiden, bei welchem gegebenen char, welche Aktion gemacht werden soll.
		switch (direction) {
			case 'l':
				//"l" für Left (wir beginnen von links)
				start = 0;
				end = MAX_COLUMNS;
				break;
			case 'r':
				//"r" für Right (wir beginnen von rechts)
				start = MAX_COLUMNS;
				end = 0;
				break;
			case 't':
				//"t" für Top (wir beginnen von oben)
				start = 0;
				end = MAX_LINES;
				vertical = true;
				break;
			case 'b':
				//"b" fpr Bottom (wir beginnen von unten)
				start = MAX_LINES;
				end = 0;
				vertical = true;
				break;
			default:
				//Abbruch. Wir wissen ja nicht, was wir machen sollen ¯\_(ツ)_/¯
				System.out.println("Unbekannte Richtung!");
				return;
		}

		/*
			Für das, was wir machen wollen, eignet sich eine FOR-Schleife. Hier wird jedoch
			in dem "update" part (der letzte wo ihr normalerweise n++ oder n-- macht, nichts
			gemacht, da wir n Situationsabhängig ändern müssen.

			Das ist übrigens ein Multiline comment ("/*" [multiline comment] statt "/**" [javadoc comment]).
		 */
		for (int n = start; n != end;) {
			//Hier deklarieren wir unsere X und Y Werte für das Display.
			int x;
			int y;

			//Da wir unterschiedlichen Code für Vertikale und Horizontale "fahrten" brauchen, gibts hier das If-Else-Statement
			if (vertical) {
				//Der Text "fährt" von unten nach oben bzw. von oben nach unten

				//Wir müssen die vorherige Zeile leeren. Da wir nicht Zeile -1 leeren können, müssen wir hier situationsbedingt handeln.
				if (n == 0) {
					//Wenn die Zeile 0 (also ganz oben) ist, dann war die vorherige die maximale Zeile (ganz unten)
					LCD.clear(MAX_LINES);
				} else if (n == MAX_LINES && start > end) {
					//Wenn die Zeile der maximalen Zeile gleicht und wir von unten nach oben "fahren", dann war Zeile 0 die vorherige Zeile
					LCD.clear(0);
				} else if (start > end) {
					//Wenn der Text von unten nach oben "fährt", dann ist die zeile, die geleert werden muss, die darunter.
					LCD.clear(n + 1);
				} else {
					//In allen anderen Fällen leeren wir einfach die vorherige Zeile
					LCD.clear(n - 1);
				}

				//Position für Mitte finden (Die hälfte vom Display minus die hälfte vom Text)
				int middlepos = (MAX_COLUMNS / 2) - (text.length() / 2);

				//Der Text soll nicht bei einer Position kleiner als 0 beginnen (sonst kann man ihn nicht bzw. nicht ganz lesen).
				if (middlepos < 0) {
					middlepos = 0;
				}

				//Wir weisen die "errechneten" Werte den Variablen zu.
				x = middlepos;
				y = n;
			} else {
				//Der Text "fährt" von links nach rechts bzw. von rechts nach links.

				//Da wir den Text mitten auf dem Display schreiben wollen, müssen wir auch immer nur die mittlere Zeile leeren.
				LCD.clear(MAX_LINES / 2);

				//Einfaches Zuweisen der Werte auf die Variablen.
				x = n;
				y = MAX_LINES / 2;
			}

			/*
				Ich versuche in meinem Code Redundanzen zu vermeiden. Wir hätten theoretisch auch die x- bzw. y-variable weglassen können und
				"LCD.drawString" in den if- und else-körper packen können. Das wäre dann redundant. Sollte z.B. die API Methode "drawString" in "drawText"
				umbenannt werden, so müssten wir den Code an zwei Stellen ändern. Hier müssen wir es nur an einer tun.
			 */
			//Der Text wird auf das Display geschrieben. 
			LCD.drawString(text, x, y);

			/*
				Dies ist ein try/catch block. In vielen Programmiersprachen kann man sog. Exceptions "werfen". Das wird
				oft dann gemacht, wenn etwas nicht so gelaufen ist, wie erwartet.

				Der try/catch block "versucht" (wie das try schon sagt) das auszuführen, was in dem try-körper steht. Sollte eine Exception "geworfen" werden,
				so wird diese abgefangen und der catch-körper wird ausgeführt. Dort kann man dann alle weiteren Schritte (je nach dem was man machen möchte, wenn
				das Ausführen des codes im try-körper fehlgeschlagen ist) ausführen.

				Eventuell wird das noch im Unterricht drankommen.
			 */
			try {
				/*
					Wir lassen den aktuellen Thread (kommt noch im Unterricht). Für 300ms (0,3 Sekunden) schlafen (der Thread wird angehalten).
					Der Thread bleibt quasi an dieser Zeile für 0,3 Sekunden stehen.
				 */
				Thread.sleep(300L);
			} catch (InterruptedException ex) {
				/*
					Hier kommt Code rein, der ausgeführt wird, sollte eine "InterruptedException" "geworfen" werden.
					Die InterruptedException wird dann geworfen, wenn ein Thread von extern unterbrochen wird. Im
					Regelfall wird ein Thread dann unterbrochen, wenn seine Arbeit abgebrochen werden soll.

					Wir befinden uns hier in einer Schleife, sollen wir nun unsere Arbeit abbrechen, müssen wir einfach nur
					das weitere Ausführen der schleife verhindern. Das machen wir mit einem "break".
				 */

				break;
			}

			//Nun erhöhen oder verringern wir 'n'.
			if (start > end) {
				//Start ist größer als end (es geht von rechts nach links bzw. von unten nach oben). Demnach müssen wir 'n' verringern.
				n--;
			} else {
				//Start ist kleiner oder gleich end (es geht von links nach rechts bzw. von oben nach unten). Demnach müssen wir 'n' erhöhen.
				n++;
			}
		}

		//Nach der Aktion leeren wir das Display (aufräumen ist immer gut)
		LCD.clear();
	}

	/**
	 * Methode um den Batteriestatus auf dem Display auszugeben.
	 */
	public void showBatteryState() {
		//Aktuelle Spannung der Batterie holen.
		float voltage = Battery.getVoltage();

		/*
			Hier errechnen wir den Prozentsatz (p) des Aktuellen Batteriestands.
			p = W / G * 100
		 */
		float percentage = ((float) Battery.getVoltageMilliVolt()) / MAX_MILLI_VOLTAGE * 100;

		//Bildschirm leeren
		LCD.clear();

		/*
			Um die vielen Nachkommastellen zu entfernen (da wir kein DecimalFormat haben), multiplizieren wir den Wert
			mit 100, runden ihn dann und dividieren das gerundete Ergebnis dann wieder durch 100. Somit erzielen wir zwei
			Nachkommastellen. Würden wir aus der 100 eine 1000 machen, hätten wir drei Nachkommastellen.
		 */
		//Die erste Zeile wird eine Zeile über der mittleren Zeile geschrieben.
		LCD.drawString("Spannung: " + (Math.round(voltage * 100d) / 100d) + " V", 0, MAX_LINES / 2 - 1);

		//Die zweite Zeile wird in der Mitte geschrieben
		LCD.drawString("Status: " + (Math.round(percentage * 100d) / 100d) + " %", 0, MAX_LINES / 2);

		//Die dritte Zeile wird eine Zeile unter der Mitte geschrieben
		/*
			Dieser Code -> Battery.isRechargeable() ? "Ja" : "Nein"
			ist eine Vereinfachung eines If-Statements und wird Ternärer Operator genannt.
			Um das mal in einem If-Statement darzustellen:

			if (Battery.isRechargeable()) {
				"Ja"
			} else {
				"Nein"
			}

			Das ganze würde so natürlich nicht funktionieren, da man sowas nur mit einem Ternären Operator realisieren kann.
		 */
		LCD.drawString("Aufladbar: " + (Battery.isRechargeable() ? "Ja" : "Nein"), 0, MAX_LINES / 2 + 1);
	}

	/**
	 * Lässt zufällige Buchstaben und Zahlen den Bildschirm hinabrieseln
	 */
	public void matrix() {
		//Wir erstellen ein Array mit allen Buchstaben, die wir haben wollen.
		char[] letters = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

		/*
			Dies ist ein Zweidimensionales Array (kommt noch im Unterricht dran).
			Ist im Grunde ganz Einfach. Im gegensatz zum eindimensionalen (normalen) array
			kann ich hier (in unserem Fall) zu einer Kombination aus 2 Zahlen einen char zuweisen.

			Um ein kleines Beispiel zu Zeigen:

			char[][] bspArray = new char[2][3];

			Ich kann in diesem Array nun 2x3 chars unterbringen.

			[0][0], [0][1], [0][2], [1][0], [1][1], [1][2]

			Dieses Array is in diesem Falle nützlich, da ich für jede Position im Display einen char zwischenspeichern kann.
		 */
		char[][] positions = new char[MAX_COLUMNS][MAX_LINES];

		//Unsere erste For-Schleife, jedes Mal wenn sie von Vorn beginnt, wird ein neuer Buchstabe ins Spiel gebracht.
		
		for (int n = 0; n < 80; n++) {
			//Hier holen wir uns mittels Random einen zufälligen Buchstaben aus dem oben definierten Array
			char letter = letters[RAND.nextInt(letters.length)];

			//Nun suchen wir uns eine Spalte aus, in der wir den Buchstaben platzieren.
			int column = RAND.nextInt(MAX_COLUMNS - 1);

			//Der Buchstabe wird in unser positionen array geschrieben
			positions[column][0] = letter;

			//Der Buchstabe wird an seine Position geschrieben
			LCD.drawChar(letter, column, 0);

			//Mit dieser For-Schleife gehen wir durch alle Spalten
			for (int x = 0; x < MAX_COLUMNS; x++) {
				//Mit dieser durch alle Zeilen
				
				for (int y = MAX_LINES - 1; y >= 0; y--) {
					//Wir möchten ja nicht direkt den Anfangsbuchstaben bewegen, deswegen überspringen wir diesen.
					if (x == column && y == 0) continue;

					//Wenn kein Buchstabe an der aktuellen Position vorhanden ist, müssen wir auch nichts machen. Daher überspringen wir in diesem Fall.
					if (positions[x][y] == 0) continue;

					//Wir leeren die alte position des Buchstaben
					LCD.clear(x, y, 1);

					//Wenn die nächste Zeile noch im display vorhanden ist, packen wir den Buchstaben in die nächste Zeile. Ansonsten verwerfen wir ihn.
					if (y + 1 < MAX_LINES) {
						//Der Buchstabe wird an die neue Position geschrieben.
						LCD.drawChar(positions[x][y], x, y + 1);

						//Nun updaten wir unser positionen array
						positions[x][y + 1] = positions[x][y];
					}

					//Da an der vorherigen Stelle nun kein Buchstabe mehr steht, setzen wir den Wert in unserem Positionen array auf 0.
					positions[x][y] = 0;

					//Nun warten wir kurz, damit das alles nicht zu schnell geht.
					try {
						Thread.sleep(50L);
					} catch (InterruptedException ex) {
						return;
					}
				}
			}
		}

		//Nachdem die Aktion fertig ist, leeren wir das Display (aufräumen ist immer gut)
		LCD.clear();
	}
}
