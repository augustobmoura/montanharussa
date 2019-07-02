package br.ufg.softconc.montanharussa;

public class Principal {

	public static void main(String[] args) throws InterruptedException {
		MontanhaRussa montanhaRussa = new MontanhaRussa(5);
		Thread threadMontanhaRussa = new Thread(montanhaRussa);

		montanhaRussa.abrir();
		threadMontanhaRussa.start();

		FilaMontanhaRussa filaMontanhaRussa = new FilaMontanhaRussa(montanhaRussa);
		Thread threadFila = new Thread(filaMontanhaRussa);
		threadFila.start();

		Thread.sleep(1000 * 40);
		montanhaRussa.fechar();
	}
}
