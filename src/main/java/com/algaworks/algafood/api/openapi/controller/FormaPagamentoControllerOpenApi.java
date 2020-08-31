package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation(value = "Listagem de formas de pagamento")
	public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);

	@ApiOperation(value = "Buscar uma forma de pagamento por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Id da forma de pagamento inválida", response = Problem.class),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
	})
	public ResponseEntity<FormaPagamentoModel> buscar(
			@ApiParam(value = "Id de uma forma de pagamento", example = "1") Long id, ServletWebRequest request);

	@ApiOperation(value = "Cadastra uma forma de pagamento por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Forma de pagamento cadastrada")
	})
	public FormaPagamentoModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento") FormaPagamentoInput formaPagamentoInput);

	@ApiOperation(value = "Atualiza uma forma de pagamento por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
	})
	public FormaPagamentoModel atualizar(
			@ApiParam(value = "Id de uma forma de pagamento", example = "1") Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados") FormaPagamentoInput formaPagamentoInput);

	@ApiOperation(value = "Exclui uma forma de pagamento por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Forma de pagamento excluída"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
	})
	public void remover(
			@ApiParam(value = "Id de uma forma de pagamento", example = "1") Long id);

}
