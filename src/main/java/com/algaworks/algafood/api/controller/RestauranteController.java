package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteService.listar();
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
	public Restaurante buscar(@PathVariable Long id) {
		return restauranteService.buscar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		return restauranteService.salvar(restaurante);
	}

	@PutMapping("/{id}")
	public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteService.buscar(id);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "dataCadastro", "endereco",
				"produtos");
		try {
			return restauranteService.salvar(restauranteAtual);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = restauranteService.buscar(id);
		merge(campos, restauranteAtual);
		return atualizar(id, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		dadosOrigem.forEach((propriedade, valor) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, propriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});

	}

}
