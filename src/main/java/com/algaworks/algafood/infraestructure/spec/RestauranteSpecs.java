package com.algaworks.algafood.infraestructure.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteSpecs {
	/*
	 * Utilizando as classes Specs
	 */
//	public static Specification<Restaurante> comFreteGratis(){
//		return new RestauranteComFrenteGratisSpec();
//	}
//	
//	public static Specification<Restaurante> comNomeSemelhante(String nome){
//		return new RestauranteComNomeSemelhanteSpec(nome);
//	}
	
	/*
	 * Utilizando as expressoes lambda
	 */
	public static Specification<Restaurante> comFreteGratis() {
		return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

	public static Specification<Restaurante> comNomeSemelhante(String nome) {
		return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
	}

}
