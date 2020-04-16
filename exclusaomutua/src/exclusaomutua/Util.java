package exclusaomutua;

import java.util.ArrayList;
import java.util.Random;

public class Util {

	public static ArrayList<Processo> processos;
	public static Object lock;
	public static ArrayList<Processo> filaEspera;

	/*
	 * Método responsável por criar um processo na lista de processos.
	 */
	public static void criaProcesso(int TEMPO_CRIACAO) {
		processos = new ArrayList<Processo>();
		filaEspera = new ArrayList<Processo>();
		lock = new Object();

		new Thread(() -> {
			while (true) {
				synchronized (lock) {
					int ID = newID();
					processos.add(new Processo(ID, false));

					Logger.log("[Processo " + ID + "] Processo criado");
				}

				try {
					Thread.sleep(TEMPO_CRIACAO);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/*
	 * Gera um novo ID aleatorio para o processo, se ja existir um 
	 * processo com esse id, gera outro id.
	 */
	private static int newID() {
		int ID = new Random().nextInt(9999) + 1;
		for (Processo processo : processos) {
			if (processo.getID() == ID) {
				return newID();
			}
		}
		return ID;
	}

	/*
	 * Método responsável por disparar uma requisiçao de recurso com um processo aleatório ao
	 * coordenador. Se o coordenador não responder, é iniciado uma nova eleiçao.
	 */
	public static void consomeRecurso(int TEMPO_CONSUMO_MIN, int TEMPO_CONSUMO_MAX) {
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(new Random().nextInt(TEMPO_CONSUMO_MAX) + TEMPO_CONSUMO_MIN);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (lock) {
					if (processos.size() > 0) {
						int ID = new Random().nextInt(processos.size());
						Processo p = processos.get(ID);
	
						boolean existeCoordenador = p.solicitaRecurso(p);
	
						if (!existeCoordenador) {
							realizaEleicao();
						}
					}
				}
			}
		}).start();
	}

	/*
	 * Método responsável por realizar a eleiçao de um novo coordenador.
	 */
	private static void realizaEleicao() {
		Processo novoCoordenador = null;
		int ID = 0;
		for (Processo processo : processos) {
			if (processo.getID() > ID) {
				novoCoordenador = processo;
			}
		}

		novoCoordenador.setCoordenador(true);
		Logger.log("[Processo " + novoCoordenador.getID() + "] É o novo coordenador");
	}

	/*
	 * Método responsável por inativar o coordenador.
	 */
	public static void inativaCoordenador(int TEMPO_COORDENADOR_INATIVADO) {
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(TEMPO_COORDENADOR_INATIVADO);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (lock) {
					for (Processo p : processos) {
						if (p.isCoordenador()) {
							processos.remove(p);
							Logger.log("[Processo " + p.getID() + "] Coordenador inativado");
							break;
						}
					}
				}
			}
		}).start();
	}

	/*
	 * Método responsável por processar o recurso solicitado pelos processos que estão na fila
	 */
	public static void processaRecurso(int TEMPO_PROCESSAMENTO_MIN, int TEMPO_PROCESSAMENTO_MAX) {
		new Thread(() -> {
			while (true) {
				Processo p;
				synchronized (lock) {
					// pega o primeiro processo da fila e processa a requisição
					if (filaEspera.size() >= 1) {
						p = filaEspera.get(0);
					} else {
						continue;
					}
				}
				
				try {
					Thread.sleep(new Random().nextInt(TEMPO_PROCESSAMENTO_MAX) + TEMPO_PROCESSAMENTO_MIN);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				synchronized (lock) {					
					Logger.log("[Processo " + p.getID() + "] Recurso processado");
					filaEspera.remove(p);
				}
			}
		}).start();
	}

}
