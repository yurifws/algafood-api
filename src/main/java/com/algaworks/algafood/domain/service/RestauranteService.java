package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaService cozinhaService;

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	public List<Restaurante> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	public List<Restaurante> buscarPorNomeECozinhaId(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}

	public Optional<Restaurante> buscarPrimeiroPorNome(String nome) {
		return restauranteRepository.findFirstByNomeContaining(nome);
	}

	public List<Restaurante> buscarTop2PorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}

	public int countPorCozinhaId(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}

	public List<Restaurante> buscarPorNomeEFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}

	public List<Restaurante> buscarComFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}

	public Optional<Restaurante> buscarPrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		try {
			Long cozinhaId = restaurante.getCozinha().getId();
			Cozinha cozinha = cozinhaService.buscar(cozinhaId);
			restaurante.setCozinha(cozinha);
			return restauranteRepository.save(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Restaurante restauranteAtual = buscar(id);
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long id) {
		Restaurante restauranteAtual = buscar(id);
		restauranteAtual.inativar();
	}
	
}
