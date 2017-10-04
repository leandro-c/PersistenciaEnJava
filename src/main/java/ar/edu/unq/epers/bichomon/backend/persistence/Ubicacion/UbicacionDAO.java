package ar.edu.unq.epers.bichomon.backend.persistence.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public interface UbicacionDAO {

    void guardar( Ubicacion ubicacion );

    void nuevaUbicacion( Ubicacion ubicacion );

    Ubicacion recuperar( String nombre );

    void actualizarUbicacion(Ubicacion ubicacion);

    List<Dojo> todosLosDojos();

    List<Bicho> todosLosCampeones();
}
