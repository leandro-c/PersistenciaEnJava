package ar.edu.unq.epers.bichomon.backend.service.nivel;

import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.persistence.Nivel.HBNNivelDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.Nivel.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class NivelServiceHBN implements NivelService {

    private NivelDAO nivelDAO = new HBNNivelDAO();

    @Override
    public void guardarNivel(Nivel nivel) {
        Runner.runInSession(() -> {
            nivelDAO.guardarNivel(nivel);
            return null;
        });
    }

    @Override
    public Nivel obtenerNivel(int numero) {
        return Runner.runInSession(() -> nivelDAO.obtenerNivel(numero));
    }

    @Override
    public void cambiarExperienciaMaxima(Nivel nivel, int nuevaExperiencia) {
        Runner.runInSession(() -> {
            this.nivelDAO.cambiarExperienciaMaxima(nivel.getNumero(), nuevaExperiencia);
            return null;
        });
    }
}
