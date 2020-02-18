package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de código %d náo pode ser removida, pois está em uso.";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public List<FormaPagamento> listar(){
		return formaPagamentoRepository.findAll();
	}
	
	public FormaPagamento buscar(Long id){
		return formaPagamentoRepository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void remover(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
		}

	}
	

}
