package exclusaomutua;

import java.time.Duration;
import java.time.Instant;

public class Logger {

	private static Instant start;

	/*
	 * M�todo respons�vel por fazer o log do que est� ocorrendo na aplica��o
	 * inserindo o tempo de execu��o.
	 */
	public static void log(String message) {
		if (start == null) {
			start = Instant.now();
		}

		Instant now = Instant.now();

		System.out.println("[TEMPO] " + Duration.between(start, now).toSeconds() + " segundos: " + message);
	}
}
