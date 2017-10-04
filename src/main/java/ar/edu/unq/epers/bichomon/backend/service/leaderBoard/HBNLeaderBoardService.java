package ar.edu.unq.epers.bichomon.backend.service.leaderBoard;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.persistence.cache.REDISCampeonesDao;
import ar.edu.unq.epers.bichomon.backend.persistence.leaderBoard.HBNLeaderBoardDao;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionService;

import java.util.*;

public class HBNLeaderBoardService implements LeaderBoardService {

    private REDISCampeonesDao campeonesDao = new REDISCampeonesDao();
    private HBNLeaderBoardDao leaderBoardDao = new HBNLeaderBoardDao();

    @Override
    public List<Entrenador> campeones() {
        return Runner.runInSession(() -> {
            if (campeonesDao.exists()) {
                return campeonesDao.get();
            }
            List<Entrenador> campeones = leaderBoardDao.campeones();

            /*List<Dojo> dojos = dojos();
            List<Entrenador> campeones = new ArrayList<>();
            for(Dojo dojo : dojos) {
            if (dojo.getCampeones().size() > 0) {
                campeones.add(dojo.campeonActual().getEntrenador());
            }
        }*/
            campeonesDao.put(campeones);
            return campeones;
        });
    }

    @Override
    public Especie especieLider() {
        List<Bicho> campeones = todosLosCampeones();
        if (campeones.size() > 0) {
            return especieLiderEnLista(campeones);
        }
        else throw new RuntimeException("No hay ningun campeon");
    }

    private Especie especieLiderEnLista(List<Bicho> bichos) {
        Map<Especie, Integer> apariciones = aparicionesPorEspecie(bichos);
        return Collections.max(apariciones.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
    }

    private Map<Especie, Integer> aparicionesPorEspecie(List<Bicho> bichos) {
        Map<Especie, Integer> apariciones = new HashMap<>();
        for(Bicho bicho : bichos) {
            agregarAparicionAMap(apariciones, bicho.getEspecie());
        }
        return apariciones;
    }

    private Map<Especie, Integer> agregarAparicionAMap(Map<Especie, Integer> apariciones, Especie especie) {
        if(apariciones.containsKey(especie)){
            apariciones.put(especie, apariciones.get(especie)+1);
        }
        else
        {
            apariciones.put(especie, 1);
        }
        return apariciones;
    }

    private List<Dojo> dojos() {
        return getUbicacionService().todosLosDojos();
    }

    private List<Bicho> todosLosCampeones() {
        return getUbicacionService().todosLosCampeones();
    }

    private UbicacionService getUbicacionService() {
        return new ServiceFactory().getUbicacionService();
    }
}
