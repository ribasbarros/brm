package br.com.brm.scp.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(basePackages = {"br.com.brm.scp.api.service.*", "br.com.brm.scp.api.service.repositories"})
public class RepositoryConfigurationTest {

	/*
	 * <bean id="mongo"
	 * class="org.springframework.data.mongodb.core.MongoFactoryBean"> <property
	 * name="host" value="localhost" /> </bean>
	 * 
	 * <bean id="mongoTemplate"
	 * class="org.springframework.data.mongodb.core.MongoTemplate">
	 * <constructor-arg name="mongo" ref="mongo" /> <constructor-arg
	 * name="databaseName" value="test" /> </bean>
	 */

	private static final String DB_NAME = "test";
	private static final String ADDRESS = "127.0.0.1";
	private static final int PORT = 27017;

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {

		//MongoCredential credential = MongoCredential.createCredential("", DB_NAME, "".toCharArray());
		ServerAddress serverAddress = new ServerAddress(ADDRESS, PORT);

		MongoClient mongoClient = new MongoClient(serverAddress); //Arrays.asList(credential)

		// Mongo DB Factory
		SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient, DB_NAME);

		return simpleMongoDbFactory;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

	}

}