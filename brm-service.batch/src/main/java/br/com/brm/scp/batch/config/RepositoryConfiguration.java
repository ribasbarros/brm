package br.com.brm.scp.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(basePackages = {"br.com.brm.scp.api.service.repositories"})
@Order(3)
public class RepositoryConfiguration {

	private static final String DB_NAME = "test";
	private static final String ADDRESS = "127.0.0.1";
	private static final int PORT = 27017;

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {

		ServerAddress serverAddress = new ServerAddress(ADDRESS, PORT);
		MongoClient mongoClient = new MongoClient(serverAddress); //Arrays.asList(credential)
		SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient, DB_NAME);

		return simpleMongoDbFactory;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;

	}

}