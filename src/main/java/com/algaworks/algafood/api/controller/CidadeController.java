package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@ApiOperation(value = "Listagem as cidades")
	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeService.listar());
	}

	@ApiOperation(value = "Buscar uma cidade por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Id da cidade inválida", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
	})
	@GetMapping("/{id}")
	public CidadeModel buscar(@ApiParam(value = "Id de uma cidade", example = "1") 
	@PathVariable Long id) {
		return cidadeModelAssembler.toModel(cidadeService.buscar(id));
	}

	@ApiOperation(value = "Cadastra uma cidade por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Cidade cadastrada")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
		return cidadeModelAssembler.toModel(cidadeService.salvar(cidade));
	}

	@ApiOperation(value = "Atualiza uma cidade por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
	})
	@PutMapping("/{id}")
	public CidadeModel atualizar(
			@ApiParam(value = "Id de uma cidade", example = "1") 
			@PathVariable Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
			@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidadeAtual = cidadeService.buscar(id);
		cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
		return cidadeModelAssembler.toModel(cidadeService.salvar(cidadeAtual));
		
	}

	@ApiOperation(value = "Exclui uma cidade por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Cidade excluída"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
	})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(
			@ApiParam(value = "Id de uma cidade", example = "1") 
			@PathVariable Long id) {
		cidadeService.remover(id);
	}

}
