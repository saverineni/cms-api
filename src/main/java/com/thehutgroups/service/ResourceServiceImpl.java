package com.thehutgroups.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thehutgroups.repository.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository repository	;


	@Override
	public String retrieveResources(String resources) {
		return repository.retrieveResources(resources);
	}


	@Override
	public String retrieveResourceById(String docId) {
		return repository.retrieveResourceById(docId);
	}


	@Override
	public String createResource(String createJson) {
		return repository.createResource(createJson);
	}
	

	@Override
	public String deleteResourceById(String docId) {
		return repository.deleteResourceById(docId);
	}

}
