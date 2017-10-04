package ar.edu.unq.epers.bichomon.backend.service.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.persistence.Ubicacion.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.persistence.Ubicacion.HBNUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.List;

public class HBNUbicacionService implements UbicacionService{

    UbicacionDAO ubicacionDAO = new HBNUbicacionDAO();

    @Override
    public void nuevaUbicacion(Ubicacion ubicacion) {
        Runner.runInSession(() -> {
            this.ubicacionDAO.nuevaUbicacion(ubicacion);
            return null;
        });
    }

    @Override
    public Ubicacion reccuperarUbicacion( String ubicacion) {
        return Runner.runInSession(() -> this.ubicacionDAO.recuperar( ubicacion ));
    }

    @Override
    public void actualizarUbicacion(Ubicacion ubicacion) {
        Runner.runInSession(() -> {
            this.ubicacionDAO.actualizarUbicacion(ubicacion);
            return null;
        });
    }

    public List<Dojo> todosLosDojos() {
        return Runner.runInSession(() -> this.ubicacionDAO.todosLosDojos());
    }

    @Override
    public List<Bicho> todosLosCampeones() {
        return Runner.runInSession(() -> this.ubicacionDAO.todosLosCampeones());
    }

}
