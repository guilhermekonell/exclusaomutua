package exclusaomutua;

/*
 * Especificação:
 *	▪ a cada 40 segundos um novo processo deve ser criado (IDrandômico)
 *	▪ os processos tentam consumir o(s) recurso(s) num intervalo de 10 à 25 segundos
 *	▪ o tempo de processamento de um recurso é de 5 à 15 segundos
 *	▪ a cada 1 minuto o coordenador morre
 *
 *	▪ quando o coordenador morre, a fila também morre
 *	▪ dois processos não podem ter o mesmo ID
 */
public class Main {

	private final static int TEMPO_CRIACAO = 40000;
	private final static int TEMPO_COORDENADOR_INATIVADO = 60000;
	private final static int TEMPO_CONSUMO_MIN = 10000;
	private final static int TEMPO_CONSUMO_MAX = 25000;
	private final static int TEMPO_PROCESSAMENTO_MIN = 5000;
	private final static int TEMPO_PROCESSAMENTO_MAX = 15000;

	public static void main(String[] args) {
		Util.criaProcesso(TEMPO_CRIACAO);
		Util.consomeRecurso(TEMPO_CONSUMO_MIN, TEMPO_CONSUMO_MAX);
		Util.inativaCoordenador(TEMPO_COORDENADOR_INATIVADO);
		Util.processaRecurso(TEMPO_PROCESSAMENTO_MIN, TEMPO_PROCESSAMENTO_MAX);
	}

}