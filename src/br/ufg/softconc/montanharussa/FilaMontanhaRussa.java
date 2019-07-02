package br.ufg.softconc.montanharussa;

public class FilaMontanhaRussa implements Runnable {

	private MontanhaRussa montanhaRussa;
	private GeradorPessoa geradorPessoa = new GeradorPessoa();

	public FilaMontanhaRussa(MontanhaRussa montanhaRussa) {
		this.montanhaRussa = montanhaRussa;
	}

	public synchronized void iniciar() throws InterruptedException {
		while (montanhaRussa.estaFuncionando()) {
			if (Thread.currentThread().isInterrupted()) {
				return;
			}

			tempoEntreParticipantes();

			montanhaRussa.entrarNaFila(geradorPessoa.novaPessoa());
		}
	}

	@Override
	public void run() {
		try {
			iniciar();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void tempoEntreParticipantes() throws InterruptedException {
		int tempo = (int) Math.floor(1000 * Math.random()) + 1000;
		Thread.sleep(tempo);
	}
}
