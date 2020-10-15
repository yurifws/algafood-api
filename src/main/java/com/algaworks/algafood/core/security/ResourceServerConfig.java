package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter{

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
			.cors()
			.and()
			.oauth2ResourceServer()
			//.opaqueToken(
			.jwt();
	}
	
	//Para chaves simetricas
	//@Bean
	//public JwtDecoder jwtDecoder() {
	//	var secretKey = new SecretKeySpec("da87d98s7d89sa78das78d9sa787da8d7sa8da78s9jj".getBytes(), "HmacSHA256");
	//	return NimbusJwtDecoder.withSecretKey(secretKey).build();
	//}

}
