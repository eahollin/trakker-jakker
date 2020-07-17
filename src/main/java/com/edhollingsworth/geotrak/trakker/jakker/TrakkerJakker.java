package com.edhollingsworth.geotrak.trakker.jakker;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.edhollingsworth.geotrak.trakker.jakker.graphql.CreateTrakMutation;
import com.edhollingsworth.geotrak.trakker.jakker.graphql.type.GeoTrakInput;

@Component
public class TrakkerJakker implements InitializingBean {
	private final Log log = LogFactory.getLog(TrakkerJakker.class.getName());

	@Autowired
	private ApolloClient apolloClient;
	
	@Autowired
	private Float[] originPoint;
	
	private Random randomizer = new Random();
	private int callCount, errCount = 0;

	/**
	 * The process.
	 */
	public void process() {
		// The entity we are going to Jakk
		String geoId = UUID.randomUUID().toString();
		int max = randomizer.nextInt(50); // Pick a random number between 1 and 50
		log.info(geoId + " is going to execute " + max + " times!");
		
		// Start at the configured Origin
		Float[] curCoord = originPoint;
		for (int i = 0; i < max; i++) {
			Float[] lastCoord = curCoord;
			// Generate a new coord, based on the previous one
			curCoord = new Float[]{lastCoord[0]+randomizer.nextFloat(),lastCoord[1]-randomizer.nextFloat()};
			
			// First, build the input object
			GeoTrakInput trak = GeoTrakInput.builder()
					.trakId(null)
					.geoId(geoId)
					.latitude(curCoord[0])
					.longitude(curCoord[1])
					.altitude(randomizer.nextDouble())
					.timestamp(null)
				.build();
			
			// Then execute the mutation
			this.apolloClient.mutate(new CreateTrakMutation(trak)).enqueue(
					new ApolloCall.Callback<Optional<CreateTrakMutation.Data>>() {
						@Override
						public void onFailure(ApolloException e) {
							log.error("Error occurred while sending mutation: " + e.getMessage());
							e.printStackTrace(System.err);
							errCount++;
						}
						@Override
						public void onResponse(Response<Optional<CreateTrakMutation.Data>> response) {
							log.debug("Apollo repsonse received: " + response);
						}
					}
			);
			callCount++;
		}
		
		log.info("Complete.");
	}
	
	public String getStatus() {
		return "Total calls: " + this.callCount + " --- Errors: " + this.errCount;
	}

	@Override
	public void afterPropertiesSet() {
		log.info("Initializing TrakkerJakker...");
		
		log.info("ApolloClient is: " + this.apolloClient);
		log.info("originPoint is: [" + this.originPoint[0] + ", " + this.originPoint[1] + "]");
	}
}
