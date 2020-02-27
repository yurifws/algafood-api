package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;

	
	@GetMapping
	public List<PedidoModel> listar(){
		return pedidoModelAssembler.toCollectionModel(pedidoService.listar());
	}
	
	@GetMapping("/{id}")
	public PedidoModel buscar(@PathVariable Long id){
		return pedidoModelAssembler.toModel(pedidoService.buscar(id));
	}
}
