package com.edhollingsworth.geotrak.trakker.jakker.config;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.apollographql.apollo.ApolloClient;

/**
 * Primary Configuration class for TrakkerJakker utility.
 *
 * @author Ed Hollingsworth
 */
@Configuration
@ComponentScan(basePackages = "com.edhollingsworth.geotrak")
public class TrakkerJakkerConfig implements InitializingBean {
	private final Log log = LogFactory.getLog(TrakkerJakkerConfig.class.getName());

	@Value(value = "${jakker.origin}")
	private String originString;
	
	private Float[] origin;
	
	@Bean
	public Float[] originPoint() {
		return this.origin;
	}
	
	@Value(value = "${graphql.endpoint}")
	private String endpoint;
	
	@Bean
	public String graphqlEndpoint() {
		return this.endpoint;
	}
	
	// ApolloClient is used to communicate with the GraphQL server
	private ApolloClient apollo;
	
	@Bean
	public ApolloClient apolloClient() {
		// Only create the ApolloClient on first call
		if (this.apollo == null) {
			log.debug("First call - initialize ApolloClient");
			this.apollo = ApolloClient.builder()
					.serverUrl(graphqlEndpoint())
					.build();
		}
		else
			log.debug("Repeat call - return existing ApolloClient");
		
		return this.apollo;		
	}
	
	@Override
	public void afterPropertiesSet() {
		log.info("Preparing TrakkerJakker...");
		
		// parse the configured origin point
		Float[] coords = new Float[2];
		StringTokenizer stok = new StringTokenizer(this.originString, ",");
		coords[0] = Float.valueOf(stok.nextToken()); //lat
		coords[1] = Float.valueOf(stok.nextToken()); //long
		this.origin = coords;
				
		log.info("Origin Point is: [" + originPoint()[0] + ", " + originPoint()[1] + "]");
		log.info("Endpoint value is: " + graphqlEndpoint());
		log.info("Loaded the ApolloClient: " + apolloClient());
	}
}
