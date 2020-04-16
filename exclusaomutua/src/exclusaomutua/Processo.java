package exclusaomutua;

public class Processo {

	private final int ID;
	private boolean coordenador;

	/*
	 * Classe que representa o processo.
	 */
	public Processo(int ID, boolean coordenador) {
		super();
		this.ID = ID;
		this.coordenador = coordenador;
	}

	public int getID() {
		return ID;
	}

	public boolean isCoordenador() {
		return coordenador;
	}

	public void setCoordenador(boolean isCoordenador) {
		this.coordenador = isCoordenador;
	}

	/*
	 * Método responsável por fazer uma solicitação de recurso ao coordenador, se encontrar o coordenador,
	 * adiciona o processo na fila, se não, retorna que não encontrou o coordenador.
	 */
	public boolean solicitaRecurso(Processo processo) {
		for (Processo p : Util.processos) {
			if (p.isCoordenador()) {
				return p.recurso(processo);
			}
		}
		
		Logger.log("[Processo " + this.ID + "] Coodenador não encontrado");
		return false;
	}

	private boolean recurso(Processo processo) {
		Logger.log("[Processo " + this.ID + "] Recurso solicitado");
		Util.filaEspera.add(processo);
		return true;
	}
}
