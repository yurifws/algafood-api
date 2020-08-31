package com.algaworks.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi{

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeService.listar());
	}
	
	@GetMapping("/{id}")
	public CidadeModel buscar(@PathVariable Long id) {
		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidadeService.buscar(id));
		
		//cidadeModel.add(new Link("http://localhost:8080/cidades/1"));
		cidadeModel.add(linkTo(CidadeController.class)
				.slash(cidadeModel.getId())
				.withSelfRel());
		
		//cidadeModel.add(new Link("http://localhost:8080/cidades", "cidades"));
		cidadeModel.add(linkTo(CidadeController.class)
				.withRel("cidades"));
		
		//cidadeModel.getEstado().add(new Link("http://localhost:8080/estados/1"));
		cidadeModel.getEstado().add(linkTo(EstadoController.class)
				.slash(cidadeModel.getEstado().getId())
				.withSelfRel());
		
		return cidadeModel;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
		CidadeModel cidadeModel =  cidadeModelAssembler.toModel(cidadeService.salvar(cidade));
		ResourceUriHelper.addUriResponseHeader(cidadeModel.getId());
		return cidadeModel;
	}

	@PutMapping("/{id}")
	public CidadeModel atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidadeAtual = cidadeService.buscar(id);
		cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
		return cidadeModelAssembler.toModel(cidadeService.salvar(cidadeAtual));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cidadeService.remover(id);
	}

}
