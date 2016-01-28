package com.thehutgroups.service;

public interface ResourceService {
	String retrieveResources(String resources);
	String retrieveResourceById(String id);
	String createResource(String createJson);
	String deleteResourceById(String id);	
}
