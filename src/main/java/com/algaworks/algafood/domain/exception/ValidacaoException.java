package com.algaworks.algafood.domain.exception;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = -7640081010255246788L;

	private BindingResult bindingResult;
	
	
}
