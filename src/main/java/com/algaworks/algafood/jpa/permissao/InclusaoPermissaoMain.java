package com.algaworks.algafood.jpa.permissao;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

public class InclusaoPermissaoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

		Permissao permissao1 = new Permissao();
		permissao1.setNome("Gerenciar");
		permissao1.setDescricao("Permite gerenciamneto");

		Permissao permissao2 = new Permissao();
		permissao2.setNome("Excluir");
		permissao2.setDescricao("Permite exclusão");

		permissao1 = permissaoRepository.salvar(permissao1);
		permissao2 = permissaoRepository.salvar(permissao2);

		System.out.printf("%s - %s\n", permissao1.getId(), permissao1.getNome());
		System.out.printf("%s - %s\n", permissao2.getId(), permissao2.getNome());

	}

}
