package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

public interface BichoService {

    void abandonar(String entrenador, int Bicho);

    Bicho buscar(String entrenador) throws Exception;

    Bicho recuperarBicho( int bicho );

    void guardarBicho( Bicho bicho );

    boolean puedeEvolucionar(String entrenador, int bicho);

    Bicho evolucionar(int bicho);

    Bicho obtenerBichoRandom();

    ResultadoCombate duelo(String entrenadorId, int bichoId) throws Exception;
}
