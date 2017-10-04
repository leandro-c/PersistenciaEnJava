package ar.edu.unq.epers.bichomon.backend.persistence.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public interface EntrenadorDAO {

    void crearEntrenador( Entrenador entrenador );

    Entrenador getEntrenador( String nombre );

    void actualizarEntrenador(Entrenador entrenador);

    List<Entrenador> entrenadoresConEspecie(Especie especie);
}
