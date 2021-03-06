package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioSenhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<UsuarioModel> listar() {
		return usuarioModelAssembler.toCollectionModel(usuarioService.listar());
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping("/{id}")
	public UsuarioModel buscar(@PathVariable Long id) {
		return usuarioModelAssembler.toModel(usuarioService.buscar(id));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		return usuarioModelAssembler.toModel(usuarioService.salvar(usuario));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
	@PutMapping("/{id}")
	public UsuarioModel atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioSemSenhaInput usuarioSemSenhaInput) {
		Usuario usuarioAtual =  usuarioService.buscar(id);
		usuarioInputDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuarioAtual);
		return usuarioModelAssembler.toModel(usuarioService.salvar(usuarioAtual));
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@PutMapping("/{id}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarSenha(@PathVariable Long id, @RequestBody @Valid UsuarioSenhaInput usuarioSenhaInput) {
		Usuario usuarioAtual =  usuarioService.buscar(id);
		usuarioService.atualizarSenha(usuarioSenhaInput.getSenhaAtual(), usuarioSenhaInput.getNovaSenha(), usuarioAtual);
	}

//	@DeleteMapping("/{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void remover(@PathVariable Long id) {
//		Usuario usuarioAtual = usuarioService.buscar(id);
//		usuarioService.remover(usuarioAtual.getId());
//	}

}
