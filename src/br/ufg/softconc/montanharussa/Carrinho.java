package br.ufg.softconc.montanharussa;

import java.util.*;

public class Carrinho {

	private final int capacidade;
	private List<Pessoa> pessoas = new ArrayList<>();

	public Carrinho(int capacidade) {
		this.capacidade = capacidade;
	}

	public boolean estaCheio() {
		return getQuantidadePessoas() == capacidade;
	}

	public boolean temPessoas() {
		return !pessoas.isEmpty();
	}

	public boolean entrar(Pessoa pessoa) {
		if (estaCheio()) {
			System.out.println("Carrinho já está cheio");
			return false;
		}

		pessoas.add(pessoa);
		return true;
	}

	public int getQuantidadePessoas() {
		return pessoas.size();
	}

	public ListIterator<Pessoa> pessoas() {
		return pessoas.listIterator();
	}

}
