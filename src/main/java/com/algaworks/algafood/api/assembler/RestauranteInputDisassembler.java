package com.algaworks.algafood.api.assembler;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	

	public Restaurante toDomainObject(@Valid RestauranteInput restauranteInput) {
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		
		Restaurante restaurante = new Restaurante();
		restaurante.setId(restauranteInput.getId());
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		restaurante.setCozinha(cozinha);
		return restaurante;
	}
}
