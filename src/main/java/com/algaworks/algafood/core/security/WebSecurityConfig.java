package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
		
			.and()
				.authorizeRequests()
			//Permite endpoint de cozinha sem autorizacao
					.antMatchers("/v1/cozinhas/**").permitAll()
			//Todas os outros endpoint devem conter autorizacao
					.anyRequest().authenticated()
					
			//Não guarda dos cookies
			.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					
			//Desabilito o csrf
			.and()
				.csrf().disable();
	}

}
