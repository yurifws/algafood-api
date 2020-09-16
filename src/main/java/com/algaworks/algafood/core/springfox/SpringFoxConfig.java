package com.algaworks.algafood.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.GruposModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosModelOpenApi;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{


	@Bean
	public Docket apiDocketV1() {
		var typeResolver = new TypeResolver();
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V1")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
				.paths(PathSelectors.ant("/v1/**"))
				.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class, URI.class, URLStreamHandler.class,
						Resource.class, File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(
						AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaModelV2.class), 
								CozinhasModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, PedidoResumoModel.class), 
								PedidosResumoModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CidadeModelV2.class), 
								CidadesModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, EstadoModel.class), 
										EstadosModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class), 
								FormasPagamentoModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, GrupoModel.class), 
								GruposModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, PermissaoModel.class), 
								PermissoesModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, ProdutoModel.class), 
								ProdutosModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class), 
								RestaurantesBasicoModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, UsuarioModel.class), 
								UsuariosModelOpenApi.class)
						)
				.apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia as grupos"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes"),
						new Tag("Usuários", "Gerencia os usuários"),
						new Tag("Estatísticas", "Estatísticas da AlgaFood"),
				        new Tag("Permissões", "Gerencia as permissões"),
				        new Tag("Root Entry Point", "Rotas da API")
						);
	}
	
	@Bean
	public Docket apiDocketV2() {
		var typeResolver = new TypeResolver();

		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V2")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
				.paths(PathSelectors.ant("/v2/**"))
				.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class, URI.class, URLStreamHandler.class,
						Resource.class, File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(
						AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaModelV2.class), 
								CozinhasModelOpenApi.class),
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CidadeModelV2.class), 
								CidadesModelOpenApi.class)
						)
				.apiInfo(apiInfoV2())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Cozinhas", "Gerencia as cozinhas")
						);
	}

	private List<ResponseMessage> globalGetResponseMessages(){
		return Arrays.asList(
				responseMessageInternalServerError(),
				responseMessageNotAcceptable()
				);
	}

	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				responseMessageBadRequest(),
				responseMessageInternalServerError(),
				responseMessageNotAcceptable(),
				responseMessageUnsupportedMediaType()
				);
	}

	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				responseMessageBadRequest(),
				responseMessageInternalServerError()
				);
	}

	private ResponseMessage responseMessageUnsupportedMediaType() {
		return new ResponseMessageBuilder()
				.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
				.message("Requisição recusada porque o corpo está em um formato não suportado")
				.build();
	}

	private ResponseMessage responseMessageNotAcceptable() {
		return new ResponseMessageBuilder()
				.code(HttpStatus.NOT_ACCEPTABLE.value())
				.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
				.build();
	}

	private ResponseMessage responseMessageInternalServerError() {
		return new ResponseMessageBuilder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("Erro interno no servidor")
				.responseModel(new ModelRef(Problem.MODEL_REF))
				.build();
	}

	private ResponseMessage responseMessageBadRequest() {
		return new ResponseMessageBuilder()
				.code(HttpStatus.BAD_REQUEST.value())
				.message("Requisição inválida (erro do cliente)")
				.responseModel(new ModelRef(Problem.MODEL_REF))
				.build();
	}

	private ApiInfo apiInfo(String versao) {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version(versao)
				.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
				.build();
	}
	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("AlgaFood API (Depreciada)")
				.description("API aberta para clientes e restaurantes.<br>"
						+ "<strong>Essa versão da API está depreciada e deixará de existir a partir de 01/01/2021. "
						+ "Use a versão mais atual da API.")
				.version("1")
				.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
				.build();
	}
	private ApiInfo apiInfoV2() {
		return apiInfo("2");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");

	}

}
