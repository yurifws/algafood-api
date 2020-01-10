package com.algaworks.algafood.jpa.permissao;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

public class AlteracaoPermissaoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

		Permissao permissao = new Permissao();
		permissao.setId(1L);
		permissao.setNome("Alterar");
		permissao.setDescricao("Permite alterar");

		permissao = permissaoRepository.salvar(permissao);
		System.out.printf("%s - %s\n", permissao.getNome(), permissao.getDescricao());

	}

}
