package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.algaworks.algafood.api.model.RestauranteView;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectionModel(restauranteService.listar());
	}
	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteModel> listarApenasNome() {
		return restauranteModelAssembler.toCollectionModel(restauranteService.listar());
	}
	
//	@GetMapping
//	public MappingJacksonValue listarComWrapper(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteService.listar();
//		List<RestauranteModel> restauranteModels = restauranteModelAssembler.toCollectionModel(restaurantes);
//		MappingJacksonValue restauranteWrapper = new MappingJacksonValue(restauranteModels);
//		
//		restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);
//		if("apenas-nome".equals(projecao)) {
//			restauranteWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		}else if("completo".equals(projecao)) {
//			restauranteWrapper.setSerializationView(null);
//		}
//		
//		return restauranteWrapper;
//	}

	@GetMapping("/{id}")
	public RestauranteModel buscar(@PathVariable Long id) {
		return restauranteModelAssembler.toModel(restauranteService.buscar(id));
	}
	
	@GetMapping("/por-taxa-frete")
	public List<RestauranteModel> consultarPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
			@RequestParam BigDecimal taxaFinal) {
		return restauranteModelAssembler.toCollectionModel(
				restauranteService.buscarPorTaxaFrete(taxaInicial, taxaFinal));
	}

	@GetMapping("/por-nome-e-cozinha-id")
	public List<RestauranteModel> consultarPorTaxaFrete(@RequestParam String nome, @RequestParam Long cozinhaId) {
		return restauranteModelAssembler.toCollectionModel(
				restauranteService.buscarPorNomeECozinhaId(nome, cozinhaId));
	}

	@GetMapping("/primeiro-por-nome")
	public ResponseEntity<RestauranteModel> buscarPrimeiroPorNome(@RequestParam String nome) {
		Optional<Restaurante> restaurante = restauranteService.buscarPrimeiroPorNome(nome);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restauranteModelAssembler.toModel(restaurante.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/top2-por-nome")
	public List<RestauranteModel> buscarTop2PorNome(@RequestParam String nome) {
		return restauranteModelAssembler.toCollectionModel(
					restauranteService.buscarTop2PorNome(nome));
	}

	@GetMapping("/count-por-cozinha-id")
	public int countPorCozinhaId(@RequestParam Long cozinhaId) {
		return restauranteService.countPorCozinhaId(cozinhaId);
	}

	@GetMapping("/por-nome-e-frete")
	public List<RestauranteModel> buscarPorNomeEFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteModelAssembler.toCollectionModel(
				restauranteService.buscarPorNomeEFrete(nome, taxaInicial, taxaFinal));
	}

	@GetMapping("/com-frete-gratis")
	public List<RestauranteModel> buscarComFreteGratis(String nome) {
		return restauranteModelAssembler.toCollectionModel(
				restauranteService.buscarComFreteGratis(nome));
	}

	@GetMapping("/primeiro")
	public RestauranteModel buscarPrimeiro() {
		return restauranteModelAssembler.toModel(restauranteService.buscarPrimeiro());
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
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		restauranteService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		restauranteService.inativar(id);
	}
	
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		restauranteService.abrir(id);
	}
	
	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		restauranteService.fechar(id);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		restauranteService.ativar(restauranteIds);
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		restauranteService.inativar(restauranteIds);
	}

}
