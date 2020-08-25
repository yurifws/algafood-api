package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

//	@GetMapping
//	public List<FormaPagamentoModel> listar() {
//		return formaPagamentoModelAssembler.toCollectionModel(formaPagamentoService.listar());
//	}
	@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> listar() {
		List<FormaPagamentoModel> formaPagamentoModels =  formaPagamentoModelAssembler.toCollectionModel(formaPagamentoService.listar());
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoModels);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long id) {
		FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamentoService.buscar(id));
		 
		 return ResponseEntity.ok()
				 .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				 .body(formaPagamentoModel);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
		return formaPagamentoModelAssembler.toModel(formaPagamentoService.salvar(formaPagamento));
	}

	@PutMapping("/{id}")
	public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual =  formaPagamentoService.buscar(id);
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
		return formaPagamentoModelAssembler.toModel(formaPagamentoService.salvar(formaPagamentoAtual));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		FormaPagamento formaPagamentoAtual = formaPagamentoService.buscar(id);
		formaPagamentoService.remover(formaPagamentoAtual.getId());
	}

}
