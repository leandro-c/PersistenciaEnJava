package ar.edu.unq.epers.bichomon.frontend.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;

/**
 * Esta implementacion decora el servicio {@link EspecieService} devuelto
 * por {@link ServiceFactory} y expone sus metodos a traves de una API
 * REST.
 * 
 * Esto es codigo de frontend, no deberian tocar nada de aca.
 * 
 * @author Steve Frontend
 */
@Path("especie")
public class EspecieServiceREST implements EspecieService {
	
	private final EspecieService decorado;

	public EspecieServiceREST() {
		this.decorado = new ServiceFactory().getEspecieService();
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void crearEspecie(Especie especie) {
		this.decorado.crearEspecie(especie);
	}

	@Override
	@GET
	@Path("{nombreEspecie}")
	@Produces(MediaType.APPLICATION_JSON)
	public Especie getEspecie(@PathParam("nombreEspecie") String nombreEspecie) {
		return this.decorado.getEspecie(nombreEspecie);
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Especie> getAllEspecies() {
		return this.decorado.getAllEspecies();
	}

	@Override
	@POST
	@Path("{nombreEspecie}/{nombreBicho}")
	@Produces(MediaType.APPLICATION_JSON)
	public Bicho crearBicho(@PathParam("nombreEspecie") String nombreEspecie, @PathParam("nombreBicho") String nombreBicho) {
		return this.decorado.crearBicho(nombreEspecie, nombreBicho);
	}

	@Override
	public List<Especie> populares() {
		return null;
	}

}
