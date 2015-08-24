package br.com.scp.brm.security.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {
	/*private String dbName = "codehustler";
	private String mongoDbAddress = "127.0.0.1";

	@Bean
	public MongoClient getMongo() throws UnknownHostException {
		MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(150).autoConnectRetry(true)
				.writeConcern(WriteConcern.ERRORS_IGNORED).build();

		ServerAddress severAddress = new ServerAddress(mongoDbAddress);

		return new MongoClient(severAddress, options);
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/codehustler");
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource.setPassword("password");
		return driverManagerDataSource;
	}*/
}