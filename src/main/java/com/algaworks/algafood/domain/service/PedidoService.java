package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.infraestructure.spec.PedidoSpecs;

@Service
public class PedidoService {
	
	private static final String MSG_FORMA_DE_PAGAMENTO_NAO_ACEITA = "Forma de pagamento '%s' não é aceita por esse restaurante.";

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProdutoService produtoService;

	public Page<Pedido> pesquisar(PedidoFilter filtro, Pageable pageable) {
		return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
	}

	public Pedido buscar(String codigo) {
		return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradaException(codigo));
	}

	@Transactional
	public Pedido salvar(Pedido pedido) {
		validarPedido(pedido);
		validarItensPedido(pedido);
		pedido.calcularValorToTal();
		
		return pedidoRepository.save(pedido);
	}

	private void validarPedido(Pedido pedido) {
		Restaurante restaurante = restauranteService.buscar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());
		Cidade cidade = cidadeService.buscar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = usuarioService.buscar(pedido.getCliente().getId());

		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		
		if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format(MSG_FORMA_DE_PAGAMENTO_NAO_ACEITA, formaPagamento.getDescricao()));
		}
	}

	private void validarItensPedido(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.buscar(pedido.getRestaurante().getId(), item.getProduto().getId());
			//item.setId(null);
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}

}
