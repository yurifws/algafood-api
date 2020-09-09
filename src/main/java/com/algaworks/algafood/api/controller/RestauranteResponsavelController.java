package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteResponsavelController implements RestauranteResponsavelControllerOpenApi{

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private UsuarioModelAssembler responsavelModelAssembler;


	@Autowired
	private AlgaLinks algaLinks;

	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		CollectionModel<UsuarioModel>  responsaveisModel = 
				responsavelModelAssembler.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks()
				.add(algaLinks.linkToRestauranteResponsavel(restauranteId))
				.add(algaLinks.linkToRestauranteResponsavelAssociacao(
						restauranteId, "associar"));

		responsaveisModel.getContent().forEach( responsavelModel -> {
			responsavelModel.add(
					algaLinks.linkToRestauranteResponsavelDesassociacao(
							restauranteId, responsavelModel.getId(), "desassociar"));
		});
		return responsaveisModel;
	}

	@PutMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.associarResponsavel(restauranteId, responsavelId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.desassociarResponsavel(restauranteId, responsavelId);
		return ResponseEntity.noContent().build();
	}


}
