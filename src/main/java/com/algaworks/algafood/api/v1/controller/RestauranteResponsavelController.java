package com.algaworks.algafood.api.v1.controller;

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

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteResponsavelControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteResponsavelController implements RestauranteResponsavelControllerOpenApi{

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private UsuarioModelAssembler responsavelModelAssembler;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		CollectionModel<UsuarioModel>  responsaveisModel = 
				responsavelModelAssembler.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks();
		
		responsaveisModel.add(algaLinks.linkToRestauranteResponsavel(restauranteId));

	    if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			responsaveisModel.add(algaLinks.linkToRestauranteResponsavelAssociacao(
							restauranteId, "associar"));
	
			responsaveisModel.getContent().forEach( responsavelModel -> {
				responsavelModel.add(
						algaLinks.linkToRestauranteResponsavelDesassociacao(
								restauranteId, responsavelModel.getId(), "desassociar"));
			});
	    }
		return responsaveisModel;
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.associarResponsavel(restauranteId, responsavelId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		restauranteService.desassociarResponsavel(restauranteId, responsavelId);
		return ResponseEntity.noContent().build();
	}


}
