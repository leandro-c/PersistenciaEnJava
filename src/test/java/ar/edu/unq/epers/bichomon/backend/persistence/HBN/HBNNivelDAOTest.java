package ar.edu.unq.epers.bichomon.backend.persistence.HBN;

import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.persistence.Nivel.HBNNivelDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.Nivel.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HBNNivelDAOTest {

    private NivelDAO nivelDAO = new HBNNivelDAO();
    private Nivel nivel;

    @Before
    public void crearModelo() {
        this.nivel = new Nivel(1, 100, 10);
    }
    @After
    public void tearDown() { new DataServiceHBN().clearDb(); }

    @Test
    public void CuandoCreoYRecuperoUnNivelSonObjetosSimilares(){
        Nivel nivelRecuperado = Runner.runInSession(() -> {
            nivelDAO.guardarNivel(nivel);
            return nivelDAO.obtenerNivel(nivel.getNumero());
        });
        assertEquals(nivel, nivelRecuperado);
    }

    @Test
    public void CuandoLeAumentoLaExperienciaMaximaAUnNivelEstoSeHaceCorrectamente() {
        nivel.setExperienciaMaxima(10);
        Nivel nivelRecuperado = Runner.runInSession(() -> {
            nivelDAO.guardarNivel(nivel);
            nivelDAO.cambiarExperienciaMaxima(1,20);
            return nivelDAO.obtenerNivel(nivel.getNumero());
        });
        assertEquals(nivelRecuperado.getExperienciaMaxima(), 20);
    }
}
