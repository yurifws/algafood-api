package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.model.EnderecoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
//		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				source -> source.getCidade().getEstado().getNome(), 
				(destination, value) -> destination.getCidade().setEstado(value));
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var fotoInputToFotoTypeMap = modelMapper.createTypeMap(FotoProdutoInput.class, FotoProduto.class);
		fotoInputToFotoTypeMap.<String>addMapping(
				source -> source.getArquivo().getContentType(), 
				(destination, value) -> destination.setContentType(value));
		fotoInputToFotoTypeMap.<String>addMapping(
				source -> source.getArquivo().getOriginalFilename(), 
				(destination, value) -> destination.setNomeArquivo(value));
		fotoInputToFotoTypeMap.<Long>addMapping(
				source -> source.getArquivo().getSize(), 
				(destination, value) -> destination.setTamanho(value));
		fotoInputToFotoTypeMap.<Long>addMapping(
				source -> source.getArquivo().getSize(), 
				(destination, value) -> destination.setTamanho(value));
		
		return modelMapper;
	}

}
