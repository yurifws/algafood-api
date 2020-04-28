package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class PedidoStatusService {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	@Transactional
	public void confirmar(String codigo) {
		Pedido pedido = pedidoService.buscar(codigo);
		pedido.confirmar();
		
		Mensagem mensagem = Mensagem.builder()
				.assunto(String.format("%s - Pedido confirmado", pedido.getRestaurante().getNome()))
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		envioEmailService.enviar(mensagem);
	}
	
	@Transactional
	public void entregar(String codigo) {
		Pedido pedido = pedidoService.buscar(codigo);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigo) {
		Pedido pedido = pedidoService.buscar(codigo);
		pedido.cancelar();
	}


}
