package com.algaworks.algafood.di.modelo;

public class Cliente {
	
	private String nome;
	private String email;
	private boolean ativado;
	
	public Cliente(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}
	public String getEmail() {
		return email;
	}
	public boolean isAtivado() {
		return ativado;
	}
	
	public void ativar() {
		this.ativado = true;
	}
	
	
	
	
}
