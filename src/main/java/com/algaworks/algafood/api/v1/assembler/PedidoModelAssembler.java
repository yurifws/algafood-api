package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		if (algaSecurity.podePesquisarPedidos()) {
			pedidoModel.add(
					algaLinks.linkToPedidos("pedidos"));
		}

		if(algaSecurity.podeGerenciarPedidos(pedidoModel.getCodigo())) {
			if(pedido.podeSerConfirmado()) {
				pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedidoModel.getCodigo(), "confirmar"));
			}	
			if(pedido.podeSerEntregue()) {
				pedidoModel.add(algaLinks.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));
			}
			if(pedido.podeSerCancelado()) {
				pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedidoModel.getCodigo(), "cancelar"));
			}
			
		}

	    if (algaSecurity.podeConsultarCidades()) {
			pedidoModel.getEnderecoEntrega().getCidade().add(
					algaLinks.linkToCidade(pedidoModel.getEnderecoEntrega().getCidade().getId()));
	    }
		
	    if (algaSecurity.podeConsultarFormasPagamento()) {
			pedidoModel.getFormaPagamento().add(
					algaLinks.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId()));
	    }
		
	    if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoModel.getRestaurante().add(
					algaLinks.linkToRestaurante(pedidoModel.getRestaurante().getId()));
	    }

	    if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoModel.getCliente().add(
					algaLinks.linkToUsuario(pedidoModel.getCliente().getId()));
	    }
		
	    if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoModel.getItens().forEach(item -> {
				item.add(algaLinks.linkToItem(pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
			});
	    }

		return pedidoModel;
	}

}
