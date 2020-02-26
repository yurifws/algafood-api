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
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService implements IService<Restaurante>{

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	@Override
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

	public Restaurante buscarPrimeiro() {
		Optional<Restaurante> restaurante = restauranteRepository.buscarPrimeiro();
		if(!restaurante.isPresent()) {
			throw new RestauranteNaoEncontradoException("Não existem restaurantes.");
		}
		return restaurante.get();
	}

	@Override
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		try {
			Long cozinhaId = restaurante.getCozinha().getId();
			Cozinha cozinha = cozinhaService.buscar(cozinhaId);
			Long cidadeId = restaurante.getEndereco().getCidade().getId();
			Cidade cidade = cidadeService.buscar(cidadeId);
			restaurante.setCozinha(cozinha);
			restaurante.getEndereco().setCidade(cidade);
			return restauranteRepository.save(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	public void remover(Long id) {
		// TODO Auto-generated method stub
		
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
	
	@Transactional
	public void abrir(Long id) {
		Restaurante restauranteAtual = buscar(id);
		restauranteAtual.abrir();
	}
	
	@Transactional
	public void fechar(Long id) {
		Restaurante restauranteAtual = buscar(id);
		restauranteAtual.fechar();
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);
		restaurante.desassociarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);
		restaurante.associarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long responsavelId) {
		Restaurante restaurante = buscar(restauranteId);
		Usuario responsavel = usuarioService.buscar(responsavelId);
		restaurante.desassociarResponsavel(responsavel);
	}
	
	@Transactional
	public void associarResponsavel(Long restauranteId, Long responsavelId) {
		Restaurante restaurante = buscar(restauranteId);
		Usuario responsavel = usuarioService.buscar(responsavelId);
		restaurante.associarResponsavel(responsavel);
	}
	
}
