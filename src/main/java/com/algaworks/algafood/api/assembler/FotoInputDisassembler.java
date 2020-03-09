package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProduto toDomainObject(FotoProdutoInput fotoProdutoInput) {
		return modelMapper.map(fotoProdutoInput, FotoProduto.class);
	}
	
}
