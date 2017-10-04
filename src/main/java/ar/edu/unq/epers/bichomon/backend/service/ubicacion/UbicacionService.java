package ar.edu.unq.epers.bichomon.backend.service.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public interface UbicacionService {

    void nuevaUbicacion( Ubicacion ubicacion );

    Ubicacion reccuperarUbicacion( String ubicacion );

    void actualizarUbicacion( Ubicacion ubicacion );

    List<Dojo> todosLosDojos();

    List<Bicho> todosLosCampeones();
}
