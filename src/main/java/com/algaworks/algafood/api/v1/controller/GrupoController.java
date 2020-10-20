package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi{

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<GrupoModel> listar() {
		return grupoModelAssembler.toCollectionModel(grupoService.listar());
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping("/{id}")
	public GrupoModel buscar(@PathVariable Long id) {
		return grupoModelAssembler.toModel(grupoService.buscar(id));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
		return grupoModelAssembler.toModel(grupoService.salvar(grupo));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("/{id}")
	public GrupoModel atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual =  grupoService.buscar(id);
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		return grupoModelAssembler.toModel(grupoService.salvar(grupoAtual));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		Grupo grupoAtual = grupoService.buscar(id);
		grupoService.remover(grupoAtual.getId());
	}

}
