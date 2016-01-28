package com.thehutgroups.errorhandling;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;


@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

	private static final Logger logger = LoggerFactory .getLogger(ApplicationExceptionMapper.class);
	public Response toResponse(ApplicationException ex) {
		logger.error("exception occured ", ex);
		
		if (ex instanceof ResourceNotFoundException) {
			ErrorMessage errorMessage = createErrorMessage(ex, NOT_FOUND.getStatusCode());
			return Response.status(Response.Status.NOT_FOUND)
					.entity(errorMessage).type(MediaType.APPLICATION_JSON)
					.build();
		}
		return null;
	}

	private ErrorMessage createErrorMessage(ApplicationException ex, int statusCode) {				
		String stackTrace = getstackTraceFirstThreeLines(ex);		
		return new ErrorMessage(statusCode, ex.getMessage(), stackTrace);		
	}

	private String getstackTraceFirstThreeLines(ApplicationException ex) {
		ObjectMapper mapper = new ObjectMapper();		
		StackTraceElement[] stackTrace = ex.getStackTrace();		
		List<String> stackElements = new ArrayList<String>();
		stackElements.add(ex.getClass().getName());
		String json = null;		
		for (int i= 0; i < 3; i++) {
			stackElements.add(stackTrace[i].toString());							
		}
		try {
			json = mapper.writeValueAsString(stackElements);
		} catch (JsonProcessingException e) {		
			e.printStackTrace();
		}
		return json;
	}

}
