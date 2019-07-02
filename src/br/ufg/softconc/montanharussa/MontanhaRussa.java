package br.ufg.softconc.montanharussa;

import java.util.ListIterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MontanhaRussa implements Runnable {

	private BlockingQueue<Pessoa> fila = new LinkedBlockingQueue<>();
	private Carrinho carrinho;

	private boolean aberto = false;

	public MontanhaRussa(int capacidade) {
		carrinho = new Carrinho(capacidade);
	}

	public void abrir() {
		aberto = true;
	}

	synchronized public void iniciar() throws InterruptedException {
		System.out.println(">>> Iniciando montanha russa...");

		abrir();

		while (estaFuncionando()) {
			while (!carrinho.estaCheio() && estaFuncionando()) {
				entrarNoCarrinho(fila.take());
			}

			if (carrinho.temPessoas()) {
				iniciarVolta();

				descarregarCarrinho();
			}
		}

		System.out.println("[C] Montanha russa encerrou o funcionamento");
	}

	public void fechar() {
		System.out.println(">>> Fechando montanha russa, o último carrinho deverá partir se ainda houver pessoas dentro dele");
		aberto = false;
	}

	public boolean estaFuncionando() {
		return aberto;
	}

	public void entrarNaFila(Pessoa pessoa) {
		if (!aberto) {
			System.out.println("Montanha russa já fechou, " + pessoa.getNome() + " não conseguiu entrar");
			return;
		}

		System.out.println(pessoa.getNome() + " entra na fila");

		try {
			fila.put(pessoa);
		} catch (InterruptedException ignore) {
		}
	}

	private void entrarNoCarrinho(Pessoa pessoa) {
		System.out.println(pessoa.getNome() + " entrou no carrinho");
		carrinho.entrar(pessoa);
	}

	private void iniciarVolta() throws InterruptedException {
		System.out.println("[C] Iniciando montanha russa com " + carrinho.getQuantidadePessoas() + " pessoas...");
		System.out.println("[C] Carrinho está subindo...");

		Thread.sleep(2000);

		System.out.println("[C] Carrinho está descendo primeira subida em alta velocidade!");
		Thread.sleep(500);

		System.out.println("[C] Carrinho desceu tudo e está entrando no loop...");
		Thread.sleep(200);
		System.out.println("[C] Carrinho fez o loop!");

		System.out.println("[C] Carrinho está subindo a subida principal...");
		Thread.sleep(3000);

		System.out.println("[C] Carrinho está descendo a subida principal!");
		Thread.sleep(500);

		System.out.println("[C] Carrinho está voltando para o início");
		Thread.sleep(2000);

		System.out.println("[C] Carrinho estacionou e espera para ser descarregado");
	}

	private void descarregarCarrinho() {
		System.out.println("[C] Carrinho está descarregando...");

		ListIterator<Pessoa> pessoasIterator = carrinho.pessoas();
		while (pessoasIterator.hasNext()) {
			Pessoa pessoa = pessoasIterator.next();
			pessoasIterator.remove();

			System.out.println(pessoa.getNome() + " saiu do carrinho...");
		}

		System.out.println("[C] Carrinho está vazio");
	}

	@Override
	public void run() {
		try {
			iniciar();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
