package com.souzaoe.dscatalog.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String msg) {
		super(msg); // repassando o argumento para a super classe
	}
}
