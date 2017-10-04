package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

public interface DataService {

	void eliminarDatos();

	void crearSetDatosIniciales();

    void crearNiveles();

    Especie getEspecie();

    void crearEspecies();

}


