package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
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
	private int port;
	
	@Test
	public void shouldRetornarStatus200_WhenConsultarCozinhas() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldConter3Cozinhas_WhenConsultarCozinhas() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(3))
			.body("nome", hasItems("Tailandesa", "Indiana"));
	}

}
