package ar.edu.unq.epers.bichomon.backend.service.nivel;

import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;

public interface NivelService {

    void guardarNivel(Nivel nivel);
    Nivel obtenerNivel(int numero);
    void cambiarExperienciaMaxima(Nivel nivel, int nuevaExperiencia);
}
