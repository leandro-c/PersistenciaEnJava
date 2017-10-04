package ar.edu.unq.epers.bichomon.backend.service.data;


import ar.edu.unq.epers.bichomon.backend.persistence.data.JDBCDataDAO;
import ar.edu.unq.epers.bichomon.backend.factories.EspecieFactory;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;

import ar.edu.unq.epers.bichomon.backend.service.especie.JDBCEspecieService;

public class DataServiceJDBC implements DataService {

    JDBCDataDAO dataDAO = new JDBCDataDAO();
    EspecieFactory especieFactory = new EspecieFactory();

    @Override
    public void eliminarDatos() {
        dataDAO.eliminarDatos();
    }

    @Override
    public void crearSetDatosIniciales() {
        EspecieService especieService = new JDBCEspecieService();

        especieService.crearEspecie(especieFactory.tortuga());
        especieService.crearEspecie(especieFactory.dinosaurio());
        especieService.crearEspecie(especieFactory.dragon());

        especieService.crearBicho("Bulbasaur", "Dinosaurio");
        especieService.crearBicho("Charmander", "Dragon");
        especieService.crearBicho("Squirtle", "Tortuga");
    }

    @Override
    public void crearNiveles() {

    }

    @Override
    public Especie getEspecie() {
        return null;
    }

    @Override
    public void crearEspecies() {

    }
}
