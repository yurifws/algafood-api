package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RUNTIME)
@Target(METHOD)
public @interface CheckSecurity {
	
	public @interface Cozinhas {
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("isAuthenticated()")
		public @interface PodeConsultar {}
		
		@Retention(RUNTIME)
		@Target(METHOD)
		@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
		public @interface PodeEditar {}
		
	}
		


}
