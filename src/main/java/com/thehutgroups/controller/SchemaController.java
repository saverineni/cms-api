package com.thehutgroups.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.thehutgroups.service.SchemaServiceImpl;

@Path("/api/schema")
public class SchemaController {

	@Autowired
	private SchemaServiceImpl impl;

	@GET
	public Response getSchemaDefinitions() {
		String schemDefinItions = impl.retrieveSchemDefinitions();
		return Response.status(Status.OK).entity(schemDefinItions ).build();
	}
}