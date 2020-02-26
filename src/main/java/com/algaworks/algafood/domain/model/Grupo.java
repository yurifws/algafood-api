package com.algaworks.algafood.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_grupo")
public class Grupo {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "tb_grupo_permissao",
		joinColumns = @JoinColumn(name = "grupo_id"),
		inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	private Set<Permissao> permissoes= new HashSet<>(0);
	
	public void adicionarPermissao(Permissao permissao) {
		permissoes.add(permissao);
	}
	
	public void removerPermissao(Permissao permissao) {
		permissoes.remove(permissao);
	}
	
}
