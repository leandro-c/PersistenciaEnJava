package ar.edu.unq.epers.bichomon.backend.persistence.Nivel;

import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;

public interface NivelDAO {

    public void guardarNivel(Nivel nivel);
    public Nivel obtenerNivel(int numero);
    public void cambiarExperienciaMaxima(int numeroNivel, int nuevaExperiencia);
}
