package ar.edu.unq.epers.bichomon.frontend.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import ar.edu.unq.epers.bichomon.frontend.api.EspecieServiceREST;

/**
 * Esto es codigo de frontend, no deberian tocar nada de aca.
 */
@ApplicationPath("api")
public class BichomonWebApp extends ResourceConfig {

	public BichomonWebApp() {
		this.packages(true, EspecieServiceREST.class.getPackage().getName())
			.register(JsonObjectMapperProvider.class)
	        .register(JacksonFeature.class);
	}
	
}
