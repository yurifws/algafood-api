package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
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
	
	@Autowired
	private AlgaSecurity algaSecurity;   

	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restauranteModel.getCozinha().getId()));	
		}
		
		if (algaSecurity.podeConsultarCidades()) {
			if(restauranteModel.getEndereco() != null
					&& restauranteModel.getEndereco().getCidade() != null){
				restauranteModel.getEndereco().getCidade().add(
						algaLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
			}
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}
		
		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			if(restauranteModel.aberturaPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteAbertura(restauranteModel.getId(), "abrir"));	
			}
			if(restauranteModel.fechamentoPermitido()) {
				restauranteModel.add(algaLinks.linkToRestauranteFechamento(restauranteModel.getId(), "fechar"));
			}	
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if(restauranteModel.ativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restauranteModel.getId(), "ativar"));
			}
			if(restauranteModel.inativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteInativacao(restauranteModel.getId(), "inativar"));
			}
		}
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteModel.getId(), "formas-pagamento"));
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteResponsavel(restauranteModel.getId(), "responsaveis"));
		}
		
		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		 CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);
		    
		    if (algaSecurity.podeConsultarRestaurantes()) {
		        collectionModel.add(algaLinks.linkToRestaurantes());
		    }
		    
		    return collectionModel;
	}   

}
