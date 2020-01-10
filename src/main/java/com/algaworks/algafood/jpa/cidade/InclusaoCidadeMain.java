package com.algaworks.algafood.jpa.cidade;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class InclusaoCidadeMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
		EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

		Cidade cidade1 = new Cidade();
		cidade1.setNome("Rio Branco");
		cidade1.setEstado(estadoRepository.buscar(3L));

		Cidade cidade2 = new Cidade();
		cidade2.setNome("Macapá");
		cidade2.setEstado(estadoRepository.buscar(4L));

		cidade1 = cidadeRepository.salvar(cidade1);
		cidade2 = cidadeRepository.salvar(cidade2);

		System.out.printf("%s - %s - %s\n", cidade1.getId(), cidade1.getNome(), cidade1.getEstado().getNome());
		System.out.printf("%s - %s - %s\n", cidade2.getId(), cidade2.getNome(), cidade1.getEstado().getNome());

	}

}
