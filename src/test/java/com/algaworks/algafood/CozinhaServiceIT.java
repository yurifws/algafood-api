package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CozinhaServiceIT {
	
	@Autowired
	private CozinhaService cozinhaService;

	@Test
	public void shouldAtribuirId_WhenCadastarCozinhaComDadosCorretos() {
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Mexicana");
		
		//ação
		novaCozinha = cozinhaService.salvar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();		
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void shouldFalhar_WhenCadastarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		novaCozinha = cozinhaService.salvar(novaCozinha);
			
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void shouldFalhar_WhenExcluirCozinhaEmUso() {
		cozinhaService.remover(1L);
		
	}
	
	@Test(expected = CozinhaNaoEncontradaException.class)
	public void shouldFalhar_WhenExcluirCozinhaInexistente() {
		cozinhaService.remover(50L);
	}

}
