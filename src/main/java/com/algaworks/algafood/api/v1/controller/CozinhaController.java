package com.algaworks.algafood.api.v1.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping(path = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi{

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaService.listar(pageable);
		return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public CozinhaModel buscar(@PathVariable Long id) {
		return cozinhaModelAssembler.toModel(cozinhaService.buscar(id));
	}

	@GetMapping("/por-nome")
	public CollectionModel<CozinhaModel> consultarPorNome(@RequestParam("nome") String nome) {
		return cozinhaModelAssembler.toCollectionModel(cozinhaService.consultarTodasPorNome(nome));
	}

	@GetMapping("/unica-por-nome")
	public ResponseEntity<CozinhaModel> consultarTodasPorNome(@RequestParam("nome") String nome) {
		Optional<Cozinha> cozinha = cozinhaService.consultarPorNome(nome);
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinhaModelAssembler.toModel(cozinha.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/exists")
	public boolean existsNome(@RequestParam String nome) {
		return cozinhaService.existsNome(nome);
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		return cozinhaModelAssembler.toModel(cozinhaService.salvar(cozinha));
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@PutMapping("/{id}")
	public CozinhaModel atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cozinhaService.buscar(id);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		return cozinhaModelAssembler.toModel(cozinhaService.salvar(cozinhaAtual));
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		Cozinha cozinhaAtual = cozinhaService.buscar(id);
		cozinhaService.remover(cozinhaAtual.getId());
	}

}
