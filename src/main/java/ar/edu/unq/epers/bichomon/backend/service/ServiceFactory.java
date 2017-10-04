package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.bicho.HBNBichoService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.HBNEntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.especie.HBNEspecieService;
import ar.edu.unq.epers.bichomon.backend.service.especie.JDBCEspecieService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.HBNUbicacionService;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionService;

/**
 * Esta clase es un singleton, el cual sera utilizado por equipo de frontend
 * para hacerse con implementaciones a los servicios.
 * 
 * @author Steve Frontend
 * 
 * TODO: Gente de backend, una vez que tengan las implementaciones de sus
 * servicios propiamente realizadas apunten a ellas en los metodos provistos
 * debajo. Gracias!
 */
public class ServiceFactory {

	private EntrenadorService entrenadorService;

	/**
	 * @return un objeto que implementa {@link EspecieService}
	 */
	public EspecieService getEspecieService() {
		return new HBNEspecieService();
		//throw new RuntimeException("Todavia no se ha implementado este metodo");
	}

	public BichoService getBichoService() {
		return new HBNBichoService();
	}
	
	/**
	 * @return un objeto que implementa {@link DataService}
	 */
	public DataService getDataService() {
		throw new RuntimeException("Todavia no se ha implementado este metodo");
	}

	public EntrenadorService getEntrenadorService() {
		return new HBNEntrenadorService();
	}

	public NivelService getNivelService() {
		return new NivelServiceHBN();
	}

	public UbicacionService getUbicacionService() {
		return new HBNUbicacionService();
	}
}
