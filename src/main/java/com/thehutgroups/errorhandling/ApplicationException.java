package com.thehutgroups.errorhandling;

/**
 * Class to map application related exceptions
 * 
 *
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -8999932578270387947L;

	Integer status;
	String developerMessage;

	public ApplicationException(int status, String message, String developerMessage) {
		super(message);
		this.status = status;
		this.developerMessage = developerMessage;
	}

	public ApplicationException() {
	}

	public ApplicationException(String message, Throwable t) {
		super(message, t);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

}
