package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@GetMapping
	public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaService.listar(pageable);
		List<CozinhaModel> cozinhaModels = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
		return new PageImpl<>(cozinhaModels, pageable, cozinhasPage.getTotalElements());
	}

	@GetMapping("/{id}")
	public CozinhaModel buscar(@PathVariable Long id) {
		return cozinhaModelAssembler.toModel(cozinhaService.buscar(id));
	}

	@GetMapping("/por-nome")
	public List<CozinhaModel> consultarPorNome(@RequestParam("nome") String nome) {
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

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		return cozinhaModelAssembler.toModel(cozinhaService.salvar(cozinha));
	}

	@PutMapping("/{id}")
	public CozinhaModel atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cozinhaService.buscar(id);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		return cozinhaModelAssembler.toModel(cozinhaService.salvar(cozinhaAtual));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		Cozinha cozinhaAtual = cozinhaService.buscar(id);
		cozinhaService.remover(cozinhaAtual.getId());
	}

}
