package com.algaworks.algafood.infraestructure.service.storage;

public class StorageException extends RuntimeException{

	private static final long serialVersionUID = -3251771927025019886L;

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageException(String message) {
		super(message);
	}

}
