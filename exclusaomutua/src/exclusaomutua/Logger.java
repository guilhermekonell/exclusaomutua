package exclusaomutua;

import java.time.Duration;
import java.time.Instant;

public class Logger {

	private static Instant start;

	/*
	 * Método responsável por fazer o log do que está ocorrendo na aplicação
	 * inserindo o tempo de execução.
	 */
	public static void log(String message) {
		if (start == null) {
			start = Instant.now();
		}

		Instant now = Instant.now();

		System.out.println("[TEMPO] " + Duration.between(start, now).toSeconds() + " segundos: " + message);
	}
}
