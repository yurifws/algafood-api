package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
	public CollectionModel<GrupoModel> listar(
			@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId);
	
	@ApiOperation("Associação de grupo com usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", 
            response = Problem.class)
    })
	public ResponseEntity<Void> associar(
			@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "Id do grupo", example = "1", required = true) Long grupoId);
	
	@ApiOperation("Desassociação de grupo com usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", 
            response = Problem.class)
    })
	public ResponseEntity<Void> desassociar(
			@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "Id do grupo", example = "1", required = true) Long grupoId);
}
