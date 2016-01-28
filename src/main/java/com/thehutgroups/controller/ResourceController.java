package com.thehutgroups.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.thehutgroups.service.ResourceServiceImpl;

@Path("/api/{resources}")
public class ResourceController {

	@Autowired
	private ResourceServiceImpl impl;

	@GET
	public Response getResources(@PathParam("resources") String resources) {
		try {
			 String retrieveResources = impl.retrieveResources(resources);
			return Response.status(Status.OK).entity(retrieveResources ).build();
		} catch (IllegalArgumentException e) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Couldn't retrieve : " + resources + e.getMessage()).build());
		}
	}

	@GET
	@Path("/{id}")
	public Response getResourceById(@PathParam("id") String id) {
		try {
			 String resourceById = impl.retrieveResourceById(id);
			return Response.status(Status.OK).entity(resourceById).build();
		} catch (IllegalArgumentException e) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Couldn't retrieve : " + id + e.getMessage()).build());
		}
	}
	
	@POST	
	public Response createResource(@RequestBody String createJson) throws URISyntaxException {
		try {
			String createResourceId = impl.createResource(createJson);
			URI createdUri = new URI("/cms-api/rest/api/pages/" + createResourceId);
			return Response.created(createdUri).build();
		} catch (IllegalArgumentException e) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Couldn't retrieve : " + createJson + e.getMessage()).build());
		}
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteResourceById(@PathParam("id") String id) {
		try {
			String deleteResourceById = impl.deleteResourceById(id);			
			return Response.status(Status.OK).entity("{\"message\" : \""+deleteResourceById+"\"}" ).build();
		} catch (IllegalArgumentException e) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Couldn't retrieve : " + id + e.getMessage()).build());
		}
	}
}