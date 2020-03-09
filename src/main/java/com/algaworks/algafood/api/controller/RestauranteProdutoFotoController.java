package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FotoInputDisassembler;
import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FotoProdutoService fotoProdutoService;
	
	@Autowired
	private FotoInputDisassembler fotoInputDisassembler;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
		Produto produto = produtoService.buscar(restauranteId, produtoId);
		FotoProduto fotoProduto = fotoInputDisassembler.toDomainObject(fotoProdutoInput);
		fotoProduto.setProduto(produto);
		return fotoProdutoModelAssembler.toModel(fotoProdutoService.salvar(fotoProduto));
	}

}
