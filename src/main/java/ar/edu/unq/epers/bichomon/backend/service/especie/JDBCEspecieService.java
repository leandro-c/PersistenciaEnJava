package ar.edu.unq.epers.bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.persistence.especie.JDBCEspecieDAO;

import java.util.List;

public class JDBCEspecieService implements EspecieService {

    JDBCEspecieDAO especieDAO = new JDBCEspecieDAO();

    public void crearEspecie(Especie especie){
        especieDAO.crearEspecie(especie);
    }

    public Especie getEspecie(String nombre){ return especieDAO.getEspecie(nombre); }

    public List<Especie> getAllEspecies(){
        return especieDAO.getAllEspecies();
    }

    public Bicho crearBicho(String nombreEspecie, String nombreBicho) {
        especieDAO.agregarBicho(nombreEspecie);
        Especie especie = especieDAO.getEspecie(nombreEspecie);
        return new Bicho(especie);
    }

    @Override
    public List<Especie> populares() {
        return null;
    }
}