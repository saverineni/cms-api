package com.thehutgroups.repository;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.thehutgroups.errorhandling.ResourceNotFoundException;

@Repository
public class ResourceRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(ResourceRepository.class);

	private static final String PASSWORD = "";
	private static final String BUCKET = "mymail";
	private static final String NODE = "127.0.0.1";

	public String retrieveResources(String resources) {

		List<String> nodes = Arrays.asList(NODE);
		Cluster cluster = CouchbaseCluster.create(nodes);
		Bucket bucket = cluster.openBucket(BUCKET, PASSWORD);

		String statement = "select id, type, author, category, `created-date`, description, modules, name from mymail where category=\""
				+ resources + "\"";

		N1qlQuery query = N1qlQuery.simple(statement);
		N1qlQueryResult result = bucket.query(query);

		if (!result.finalSuccess()) {
			logger.info("Query returned with errors: " + result.errors());
			throw new IllegalArgumentException("Query error: "
					+ result.errors());
		}

		JsonArray array = JsonArray.empty();
		for (N1qlQueryRow row : result) {
			array.add(row.value());
		}

		cluster.disconnect();
		return array.toString();
	}

	public String retrieveResourceById(String id) {

		List<String> nodes = Arrays.asList(NODE);
		Cluster cluster = CouchbaseCluster.create(nodes);
		Bucket bucket = cluster.openBucket(BUCKET, PASSWORD);
		logger.info("id : {}", id);
		JsonDocument jsonDocument = bucket.get(id);
		cluster.disconnect();
		return jsonDocument.content().toString();
	}

	public String createResource(String createJson) {
		List<String> nodes = Arrays.asList(NODE);
		Cluster cluster = CouchbaseCluster.create(nodes);
		Bucket bucket = cluster.openBucket(BUCKET, PASSWORD);
		String prefix = "resource_";
		JsonLongDocument joCounter = bucket.counter(prefix, 1, 0);
		String id = prefix + joCounter.content();
		JsonDocument jsonDocument = JsonDocument.create(id,
				JsonObject.fromJson(createJson));
		bucket.upsert(jsonDocument);
		cluster.disconnect();
		return jsonDocument.id();
	}

	public String deleteResourceById(String docId) {
		List<String> nodes = Arrays.asList(NODE);
		Cluster cluster = CouchbaseCluster.create(nodes);
		Bucket bucket = cluster.openBucket(BUCKET, PASSWORD);
		JsonDocument jsonDocument = null;
		try {
			 jsonDocument = bucket.remove(docId);			
		} catch (DocumentDoesNotExistException e) {
			throw new ResourceNotFoundException(String.format("request id %s not found ", docId), e);
		} finally {
			cluster.disconnect();			
		}
		return jsonDocument.id();
	}

}