package com.algaworks.algafood;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;

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
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.http.ContentType;
/**
 * Classe de Teste de API de Restaurantes
 * @author yuri.fernando.silva
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestauranteServiceIT {
	
	private static final int RESTAURANTE_ID_INEXISTENTE = 100;
	
	@LocalServerPort
	private int localServerPort;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	private Restaurante restauranteMarcuriri;
	private int quantidadeRestaurantesCadastradas;
	
	@Before
	public void setUp() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		basePath = "/restaurantes";
		port = localServerPort;
		
		databaseCleaner.clearTables();
		preparaDados();
		
	}
	
	@Test
	public void shouldRetornarStatus200_WhenConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldConterXRestaurantes_WhenConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeRestaurantesCadastradas));
	}
	
	@Test
	public void shouldRetornarStatus201_WhenCadastrarRestaurante() {
		String jsonCorretoRestauranteNewYorkBabercue = ResourceUtils
				.getContentFromResource("/json/correto/restaurante-new-york-barbecue.json");
		given()
			.body(jsonCorretoRestauranteNewYorkBabercue)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarRestauranteSemNome() {
		String jsonRestauranteSemNome = ResourceUtils
				.getContentFromResource("/json/correto/restaurante-nome-null.json");
		given()
			.body(jsonRestauranteSemNome)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarRestauranteComTaxaInvalida() {
		String jsonRestauranteComTaxaInvalida = ResourceUtils
				.getContentFromResource("/json/correto/restaurante-new-york-barbecue-taxa-invalida.json");
		given()
			.body(jsonRestauranteComTaxaInvalida)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarStatus400_WhenCadastrarRestauranteComCozinhaInexistente() {
		String jsonRestauranteComCozinhaInexistente = ResourceUtils
				.getContentFromResource("/json/correto/restaurante-new-york-barbecue-cozinha-inexistente.json");
		given()
			.body(jsonRestauranteComCozinhaInexistente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void shouldRetornarRespostaEStatusCorretos_WhenConsultarRetauranteExistente() {
		given()
			.pathParam("restauranteId", restauranteMarcuriri.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restauranteMarcuriri.getNome()));
	}
	
	@Test
	public void shouldRetornarStatus404_WhenConsultarRestauranteInexistente() {
		given()
			.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void preparaDados() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Japonesa");
		cozinhaRepository.save(cozinha);
		restauranteMarcuriri = new Restaurante();
		restauranteMarcuriri.setNome("Marcuriri");
		restauranteMarcuriri.setTaxaFrete(new BigDecimal(122.1));
		restauranteMarcuriri.setCozinha(cozinha);
		restauranteRepository.save(restauranteMarcuriri);
		
		quantidadeRestaurantesCadastradas = (int) restauranteRepository.count();
		
	}

}
