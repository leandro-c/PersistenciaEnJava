package ar.edu.unq.epers.bichomon.backend.persistence.bicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface BichoDAO {

    Bicho obtenerBicho(int bichoID);

    void guardarBicho( Bicho bicho );

    public Bicho obtenerRandom();

    void evolucionar(Bicho bicho);
}
