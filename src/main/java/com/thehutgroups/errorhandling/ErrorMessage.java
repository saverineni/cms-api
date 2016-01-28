package com.thehutgroups.errorhandling;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "message", "developerMessage" })
public class ErrorMessage {	
	private int status;	
	private String message;
	private String developerMessage;

	ErrorMessage(int status, String message, String developerMessage) {
		this.status = status;
		this.message = message;
		this.developerMessage = developerMessage;

	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public ErrorMessage(ApplicationException ex) {
		try {
			BeanUtils.copyProperties(this, ex);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}	

	public ErrorMessage() {
	}
}
