package com.thehutgroups.errorhandling;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	public Response toResponse(Throwable ex) {
		int httpStatus = getHttpStatus(ex);
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));
		ErrorMessage errorMessage = new ErrorMessage(httpStatus, ex.getMessage(), errorStackTrace.toString());
		return Response.status(errorMessage.getStatus()).entity(errorMessage) .type(MediaType.APPLICATION_JSON).build();
	}

	private int getHttpStatus(Throwable ex) {
		if (ex instanceof WebApplicationException) {
			return ((WebApplicationException) ex).getResponse().getStatus();
		} else {
			return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
		}
	}
}
