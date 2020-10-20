package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.PermissaoService;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi{
	
	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<PermissaoModel> listar() {
		return permissaoModelAssembler.toCollectionModel(permissaoService.listar());
	}

}
