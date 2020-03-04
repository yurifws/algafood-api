package com.algaworks.algafood.core.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile>{
	
	private String[] contentTypes;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.contentTypes = constraintAnnotation.allowed();
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || 
				Arrays.asList(contentTypes).contains(value.getContentType());
	}

}
