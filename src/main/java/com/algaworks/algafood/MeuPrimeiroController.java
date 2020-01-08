package com.algaworks.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {
	
	private AtivacaoClienteService ativacaoClienteService;
	
	public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoClienteService = ativacaoClienteService;
		System.out.println("MeuPrimeiroController: "+ativacaoClienteService);
	}


	@GetMapping("/hello")
	@ResponseBody	
	public String hello() {
		Cliente cliente = new Cliente("Yuri", "yurifernando@xyz.com", 8133245411L);
		ativacaoClienteService.ativar(cliente);
		
		return "Hello!";
	}

}
