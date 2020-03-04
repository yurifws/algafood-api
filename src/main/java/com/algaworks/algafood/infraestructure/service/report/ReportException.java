package com.algaworks.algafood.infraestructure.service.report;

public class ReportException extends RuntimeException{
	
	private static final long serialVersionUID = -6368923606680282489L;

	public ReportException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReportException(String message) {
		super(message);
	}

}
