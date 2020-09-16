package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel>{

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restauranteModel.getCozinha().getId()));	
		
		if(restauranteModel.getEndereco() != null
				&& restauranteModel.getEndereco().getCidade() != null){
			restauranteModel.getEndereco().getCidade().add(
					algaLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
		}
		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		if(restauranteModel.aberturaPermitida()) {
			restauranteModel.add(algaLinks.linkToRestauranteAbertura(restauranteModel.getId(), "abrir"));	
		}
		if(restauranteModel.fechamentoPermitido()) {
			restauranteModel.add(algaLinks.linkToRestauranteFechamento(restauranteModel.getId(), "fechar"));
		}
		if(restauranteModel.ativacaoPermitida()) {
			restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restauranteModel.getId(), "ativar"));
		}
		if(restauranteModel.inativacaoPermitida()) {
			restauranteModel.add(algaLinks.linkToRestauranteInativacao(restauranteModel.getId(), "inativar"));
		}
		restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteModel.getId(), "formas-pagamento"));
		restauranteModel.add(algaLinks.linkToRestauranteResponsavel(restauranteModel.getId(), "responsaveis"));
		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}   

}
