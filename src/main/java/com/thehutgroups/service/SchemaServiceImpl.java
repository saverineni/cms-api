package com.thehutgroups.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thehutgroups.repository.SchemRepository;

@Service
public class SchemaServiceImpl implements ShemaService {

	@Autowired
	private SchemRepository SchemRepository;

	@Override
	public String retrieveSchemDefinitions() {
		return SchemRepository.retrieveSchemDefinitions();
	}

}
