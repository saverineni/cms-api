package com.thehutgroups.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;

@Repository
public class SchemRepository {

	private static final String PASSWORD = "";
	private static final String BUCKET = "mymail";
	private static final String NODE = "127.0.0.1";

	public String retrieveSchemDefinitions() {

		List<String> nodes = Arrays.asList(NODE);
		Cluster cluster = CouchbaseCluster.create(nodes);
		Bucket bucket = cluster.openBucket(BUCKET, PASSWORD);
		/*String statement = SELECT_SCHEMA;
		N1qlQuery query = N1qlQuery.simple(statement);
		N1qlQueryResult result = bucket.query(query);

		for (JsonObject object : result.errors()) {
			System.out.println("\t" + object.toString());
		}
		
		StringBuilder builder = new StringBuilder();
		for (N1qlQueryRow row : result) {
			builder.append(row.value());
		}*/
				
		JsonDocument jsonDocument = bucket.get("schema");
		cluster.disconnect();
		return jsonDocument.content().toString();		
	}
}