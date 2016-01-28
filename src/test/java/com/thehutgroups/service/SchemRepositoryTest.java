package com.thehutgroups.service;

import org.junit.Test;

import com.thehutgroups.repository.SchemRepository;

public class SchemRepositoryTest {

	@Test
	public void test() {
		SchemRepository impl = new SchemRepository();
		impl.retrieveSchemDefinitions();
	}
}
