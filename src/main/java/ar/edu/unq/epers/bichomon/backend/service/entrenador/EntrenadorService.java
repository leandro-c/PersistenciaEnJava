package ar.edu.unq.epers.bichomon.backend.service.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public interface EntrenadorService {

    void crearEntrenador( Entrenador entrenador );

    Entrenador getEntrenador( String nombre );

    void capturarBicho(String entrenador, Bicho bichoCapturado);

    List<Entrenador> entrenadoresConEspecie(Especie especie);

    void actualizarEntrenador( Entrenador entrenador );

    //void abandonarBicho( String entrenador, int bicho );
}
