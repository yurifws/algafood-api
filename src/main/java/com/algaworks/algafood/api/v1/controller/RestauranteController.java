package com.algaworks.algafood.api.v1.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi{

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;
	
	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@CheckSecurity.Restaurantes.PodeConsultar
	//@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public CollectionModel<RestauranteBasicoModel> listar() {
		return restauranteBasicoModelAssembler.toCollectionModel(restauranteService.listar());
	}
	

	@CheckSecurity.Restaurantes.PodeConsultar
	//@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNome() {
		return restauranteApenasNomeModelAssembler.toCollectionModel(restauranteService.listar());
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

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{id}")
	public RestauranteModel buscar(@PathVariable Long id) {
		return restauranteModelAssembler.toModel(restauranteService.buscar(id));
	}
	
	@GetMapping("/por-taxa-frete")
	public CollectionModel<RestauranteModel> consultarPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
			@RequestParam BigDecimal taxaFinal) {
		return restauranteModelAssembler.toCollectionModel(
				restauranteService.buscarPorTaxaFrete(taxaInicial, taxaFinal));
	}

	@GetMapping("/por-nome-e-cozinha-id")
	public CollectionModel<RestauranteModel> consultarPorNomeECozinha(@RequestParam String nome, @RequestParam Long cozinhaId) {
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
	public CollectionModel<RestauranteModel> buscarTop2PorNome(@RequestParam String nome) {
		return restauranteModelAssembler.toCollectionModel(
					restauranteService.buscarTop2PorNome(nome));
	}

	@GetMapping("/count-por-cozinha-id")
	public int countPorCozinhaId(@RequestParam Long cozinhaId) {
		return restauranteService.countPorCozinhaId(cozinhaId);
	}

	@GetMapping("/por-nome-e-frete")
	public CollectionModel<RestauranteModel> buscarPorNomeEFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteModelAssembler.toCollectionModel(
				restauranteService.buscarPorNomeEFrete(nome, taxaInicial, taxaFinal));
	}

	@GetMapping("/com-frete-gratis")
	public CollectionModel<RestauranteModel> buscarComFreteGratis(String nome) {
		return restauranteModelAssembler.toCollectionModel(
				restauranteService.buscarComFreteGratis(nome));
	}

	@GetMapping("/primeiro")
	public RestauranteModel buscarPrimeiro() {
		return restauranteModelAssembler.toModel(restauranteService.buscarPrimeiro());
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
		return restauranteModelAssembler.toModel(restauranteService.salvar(restaurante));
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{id}")
	public RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteAtual = restauranteService.buscar(id);
		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		return restauranteModelAssembler.toModel(restauranteService.salvar(restauranteAtual));
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long id) {
		restauranteService.ativar(id);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long id) {
		restauranteService.inativar(id);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		restauranteService.ativar(restauranteIds);
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		restauranteService.inativar(restauranteIds);
		return ResponseEntity.noContent().build();
	}

}
