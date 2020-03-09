package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FotoInputDisassembler;
import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoProdutoService fotoProdutoService;

	@Autowired
	private FotoStorageService fotoStorageService;

	@Autowired
	private FotoInputDisassembler fotoInputDisassembler;

	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		Produto produto = produtoService.buscar(restauranteId, produtoId);
		FotoProduto fotoProduto = fotoInputDisassembler.toDomainObject(fotoProdutoInput);
		fotoProduto.setProduto(produto);
		return fotoProdutoModelAssembler
				.toModel(fotoProdutoService.salvar(fotoProduto, fotoProdutoInput.getArquivo().getInputStream()));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = fotoProdutoService.buscar(restauranteId, produtoId);
		return fotoProdutoModelAssembler.toModel(fotoProduto);
	}

	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
		try {
			FotoProduto fotoProduto = fotoProdutoService.buscar(restauranteId, produtoId);
			InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(inputStream));
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound()
					.build();
		}
		
	}

}
