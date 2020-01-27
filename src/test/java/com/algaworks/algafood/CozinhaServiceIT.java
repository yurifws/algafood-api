package com.algaworks.algafood;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.http.ContentType;
/**
 * Classe de Teste de API de Cozinhas
 * @author yuri.fernando.silva
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CozinhaServiceIT {
	
	@LocalServerPort
	private int localServerPort;
	
	@Autowired
	private Flyway flyway;
	
	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		basePath = "/cozinhas";
		port = localServerPort;
		
		flyway.migrate();
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
			.body("nome", hasItems("Tailandesa", "Indiana"));
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

}
