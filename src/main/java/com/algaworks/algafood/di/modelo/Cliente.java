package com.algaworks.algafood.di.modelo;

public class Cliente {
	
	private String nome;
	private String email;
	private long telefone;
	private boolean ativado;
	
	public Cliente(String nome, String email, long telefone) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}
	
	public String getNome() {
		return nome;
	}
	public String getEmail() {
		return email;
	}
	
	public long getTelefone() {
		return telefone;
	}

	public boolean isAtivado() {
		return ativado;
	}
	public void ativar() {
		this.ativado = true;
	}
	
}
