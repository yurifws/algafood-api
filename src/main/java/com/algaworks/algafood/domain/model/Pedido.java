package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pedido")
public class Pedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private BigDecimal subtotal;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@Column(name = "valor_total", nullable = false)
	private BigDecimal valorTotal;

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;

	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataConfirmacao;

	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCancelamento;

	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataEntrega;

	@Embedded
	private Endereco enderecoEntrega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false)
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Usuario cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>(0);

	public void calcularValorToTal() {
		taxaFrete = restaurante.getTaxaFrete();
		itens.forEach(ItemPedido::calcularPrecoTotal);
		subtotal = itens.stream().map(ItemPedido::getPrecoTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
		valorTotal = subtotal.add(taxaFrete);
	}

	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);		
		dataConfirmacao = OffsetDateTime.now();
	}

	public void entregar() {
		setStatus(StatusPedido.ENTREGUE);	
		dataEntrega = OffsetDateTime.now();
	}

	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);	
		dataCancelamento = OffsetDateTime.now();
	}
	
	private void setStatus(StatusPedido novoStatusPedido) {
		if(status.naoPodeSerAlterado(novoStatusPedido)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s", 
					id, status.getDescricao(), novoStatusPedido.getDescricao()));
		}
		status = novoStatusPedido;
	}

}
