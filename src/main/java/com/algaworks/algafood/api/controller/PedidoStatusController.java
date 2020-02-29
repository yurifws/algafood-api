package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.PedidoStatusService;

@RestController
@RequestMapping("/pedidos/{codigo}")
public class PedidoStatusController {
	
	@Autowired
	private PedidoStatusService pedidoStatusService;
	
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigo){
		pedidoStatusService.confirmar(codigo);
	}
	
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable String codigo){
		pedidoStatusService.entregar(codigo);
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigo){
		pedidoStatusService.cancelar(codigo);
	}
	
	
	
}
