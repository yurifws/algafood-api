package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoModel;
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
	
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		pedidoModel.add(
				algaLinks.linkToPedidos());
		
		pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedidoModel.getCodigo(), "confirmar"));
		pedidoModel.add(algaLinks.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));
		pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedidoModel.getCodigo(), "cancelar"));
		pedidoModel.getEnderecoEntrega().getCidade().add(
				algaLinks.linkToCidade(pedidoModel.getEnderecoEntrega().getCidade().getId()));
		pedidoModel.getFormaPagamento().add(
				algaLinks.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId()));
		pedidoModel.getRestaurante().add(
				algaLinks.linkToRestaurante(pedidoModel.getRestaurante().getId()));
		pedidoModel.getCliente().add(
				algaLinks.linkToUsuario(pedidoModel.getCliente().getId()));
		pedidoModel.getItens().forEach(item -> {
			item.add(algaLinks.linkToItem(pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});
		
		return pedidoModel;
	}

}
