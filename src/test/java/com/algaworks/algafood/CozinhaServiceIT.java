package com.algaworks.algafood;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.http.ContentType;
/**
 * Classe de Teste de API de Cozinhas
 * @author yuri.fernando.silva
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaServiceIT {
	
	@LocalServerPort
	private int localServerPort;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaService cozinhaService; 
	
	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		basePath = "/cozinhas";
		port = localServerPort;
		
		databaseCleaner.clearTables();
		preparaDados();
		
	}
	
	@Test
	public void shouldRetornarStatus200_WhenConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldConter3Cozinhas_WhenConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(3))
			.body("nome", hasItems("Italiana", "Japonesa", "Brasileira"));
	}
	
	@Test
	public void shouldRetornarStatus201_WhenCadastrarCozinha() {
		given()
			.body("{ \"nome\" : \"Alemã\"}")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	private void preparaDados() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Italiana");
		cozinhaService.salvar(cozinha);
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		cozinhaService.salvar(cozinha2);
		Cozinha cozinha3 = new Cozinha();
		cozinha3.setNome("Brasileira");
		cozinhaService.salvar(cozinha3);
		
	}

}
