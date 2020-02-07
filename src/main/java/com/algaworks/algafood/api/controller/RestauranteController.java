package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectionModel(restauranteService.listar());
	}

	@GetMapping("/por-taxa-frete")
	public List<Restaurante> consultarPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
			@RequestParam BigDecimal taxaFinal) {
		return restauranteService.buscarPorTaxaFrete(taxaInicial, taxaFinal);
	}

	@GetMapping("/por-nome-e-cozinha-id")
	public List<Restaurante> consultarPorTaxaFrete(@RequestParam String nome, @RequestParam Long cozinhaId) {
		return restauranteService.buscarPorNomeECozinhaId(nome, cozinhaId);
	}

	@GetMapping("/primeiro-por-nome")
	public ResponseEntity<Restaurante> buscarPrimeiroPorNome(@RequestParam String nome) {
		Optional<Restaurante> restaurante = restauranteService.buscarPrimeiroPorNome(nome);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/top2-por-nome")
	public List<Restaurante> buscarTop2PorNome(@RequestParam String nome) {
		return restauranteService.buscarTop2PorNome(nome);
	}

	@GetMapping("/count-por-cozinha-id")
	public int countPorCozinhaId(@RequestParam Long cozinhaId) {
		return restauranteService.countPorCozinhaId(cozinhaId);
	}

	@GetMapping("/por-nome-e-frete")
	public List<Restaurante> buscarPorNomeEFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteService.buscarPorNomeEFrete(nome, taxaInicial, taxaFinal);
	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> buscarComFreteGratis(String nome) {
		return restauranteService.buscarComFreteGratis(nome);
	}

	@GetMapping("/primeiro")
	public Optional<Restaurante> buscarPrimeiro() {
		return restauranteService.buscarPrimeiro();
	}

	@GetMapping("/{id}")
	public RestauranteModel buscar(@PathVariable Long id) {
		return restauranteModelAssembler.toModel(restauranteService.buscar(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
		return restauranteModelAssembler.toModel(restauranteService.salvar(restaurante));
	}


	@PutMapping("/{id}")
	public RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = restauranteService.buscar(id);
		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		return restauranteModelAssembler.toModel(restauranteService.salvar(restauranteAtual));
	}

}
