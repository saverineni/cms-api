package com.thehutgroups.errorhandling;

public class ResourceNotFoundException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message, Throwable t) {
		super(message, t);
	}	
}
