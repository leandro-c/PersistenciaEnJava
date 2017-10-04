package ar.edu.unq.epers.bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.persistence.HBNGenericDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.especie.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.especie.HBNEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.*;

public class HBNEspecieService implements EspecieService {

    EspecieDAO especieDao = new HBNEspecieDAO();
    HBNGenericDAO genericDAO = new HBNGenericDAO();

    @Override
    public void crearEspecie(Especie especie) {
        genericDAO.crearEntidad(especie);
    }

    @Override
    public Especie getEspecie(String nombreEspecie) {
        return genericDAO.recuperarEntidad(Especie.class, nombreEspecie);
    }

    @Override
    public List<Especie> getAllEspecies() {
        return Runner.runInSession(() -> this.especieDao.getAllEspecies());
    }

    @Override
    public Bicho crearBicho(String nombreEspecie, String nombreBicho) {
        return Runner.runInSession(() -> {
            this.especieDao.agregarBicho(nombreEspecie);
            Especie especie = this.especieDao.getEspecie(nombreEspecie);
            return new Bicho(especie);
        });
    }

    public List<Especie> populares(){
        return sortMapEspecies(uniqBichosPorEspecie()).subList(0, 4);
    }

    private Map<Especie, Integer> uniqBichosPorEspecie() {
        return Runner.runInSession(() -> {
            Map<Especie, Integer> res = new HashMap<>();
            List<Especie> especies = this.especieDao.getAllEspecies();
            for(Especie especie : especies) {
                List<Entrenador> entrenadoresConEspecie = new ServiceFactory().getEntrenadorService().entrenadoresConEspecie(especie);
                res.put(especie, entrenadoresConEspecie.size());
            }
            return res;
        });
    }

    private List<Especie> sortMapEspecies(Map<Especie, Integer> map) {
        Set<Especie> set = map.keySet();
        List<Especie> keys = new ArrayList<>(set);

        keys.sort((s1, s2) -> Integer.compare(map.get(s2), map.get(s1)));
        return keys;
    }
}