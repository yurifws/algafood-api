package com.algaworks.algafood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.algafood.core.io.Base64ProtocolResolver;
import com.algaworks.algafood.infraestructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		var application = new SpringApplication(AlgafoodApiApplication.class);
		application.addListeners(new Base64ProtocolResolver()); // adicionando ProtocolResolver para Base64
		application.run(args);
		//SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}
