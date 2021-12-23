package com.algaworks.algafood.api;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HostCheckController {
	
	@GetMapping("/hostcheck")
	public String checkHost() throws UnknownHostException {
		return Inet4Address.getLocalHost().getHostAddress() + " - " + Inet4Address.getLocalHost().getHostName();
	}

}
