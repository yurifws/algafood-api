package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	public List<FormaPagamentoModel> listar(
			 @ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problem.class)
    })
	public void associar(
			@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID da forma de pagamento", example = "1", required = true)Long formaPagamentoId);
	
	 @ApiOperation("Desassociação de restaurante com forma de pagamento")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
	        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
	            response = Problem.class)
	    })
	public void desassociar(
			@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "Id da forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
