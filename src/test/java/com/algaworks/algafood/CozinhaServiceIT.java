package com.algaworks.algafood;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

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
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	@LocalServerPort
	private int localServerPort;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	private Cozinha cozinhaItaliana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		basePath = "/cozinhas";
		port = localServerPort;
		
		jsonCorretoCozinhaChinesa = ResourceUtils
				.getContentFromResource("/json/correto/cozinha-chinesa.json");
		
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
	public void shouldConterXCozinhas_WhenConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeCozinhasCadastradas));
	}
	
	@Test
	public void shouldRetornarStatus201_WhenCadastrarCozinha() {
		given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldRetornarRespostaEStatusCorretos_WhenConsultarCozinhaExistente() {
		given()
			.pathParam("cozinhaId", cozinhaItaliana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhaItaliana.getNome()));
	}
	
	@Test
	public void shouldRetornarStatus404_WhenConsultarCozinhaInexistente() {
		given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void preparaDados() {
		cozinhaItaliana = new Cozinha();
		cozinhaItaliana.setNome("Italiana");
		cozinhaRepository.save(cozinhaItaliana);
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		cozinhaRepository.save(cozinha2);
		Cozinha cozinha3 = new Cozinha();
		cozinha3.setNome("Brasileira");
		cozinhaRepository.save(cozinha3);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
		
	}

}
