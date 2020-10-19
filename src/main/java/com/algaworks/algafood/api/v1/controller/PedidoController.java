package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslatator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.PedidoService;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi{
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable){
		Pageable pageableTraduzido = traduzirPageable(pageable);
		Page<Pedido> pedidosPage = pedidoService.pesquisar(filtro, pageableTraduzido);
		
		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
	}
	
	@CheckSecurity.Pedidos.PodeBuscar
	@GetMapping("/{codigo}")
	public PedidoModel buscar(@PathVariable String codigo){
		return pedidoModelAssembler.toModel(pedidoService.buscar(codigo));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput){
		Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
		
		//SERA USADO O DE AUTENTICAÇÃO
		pedido.setCliente(new Usuario());
		pedido.getCliente().setId(algaSecurity.getUsuarioId());
		
		return pedidoModelAssembler.toModel(pedidoService.salvar(pedido));
		
	}
	
	private Pageable traduzirPageable(Pageable pageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
				);
		return PageableTranslatator.translate(pageable, mapeamento);
	}
	
}
