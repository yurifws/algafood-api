package com.algaworks.algafood.jpa.estado;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class InclusaoEstadoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

		Estado estado1 = new Estado();
		estado1.setNome("Acre");

		Estado estado2 = new Estado();
		estado2.setNome("Mato Grosso");

		estado1 = estadoRepository.salvar(estado1);
		estado2 = estadoRepository.salvar(estado2);

		System.out.printf("%s - %s\n", estado1.getId(), estado1.getNome());
		System.out.printf("%s - %s\n", estado2.getId(), estado2.getNome());

	}

}
