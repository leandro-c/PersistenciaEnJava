package ar.edu.unq.epers.bichomon.backend.persistence.HBN;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.persistence.entrenador.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.entrenador.impl.HBNEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class HBNEntrenadorDAOTest {

    private EntrenadorDAO entrenadorDAO = new HBNEntrenadorDAO();
    private Entrenador brock;
    private Entrenador ash;

    @Before
    public void crearModelo() {

        this.brock = new Entrenador("Brock");
        this.ash = new Entrenador("Ash");
    }
    @After
    public void tearDown() { new DataServiceHBN().clearDb(); }

    @Test
    public void cuandoCreoYrecuperoUnEntrenadorSonObjetosSimilares(){
        Runner.runInSession(() -> {
            entrenadorDAO.crearEntrenador(brock);
            Entrenador entrenadorRecuperado = entrenadorDAO.getEntrenador(brock.getNombre());
            assertTrue(brock.equalEntrenador(entrenadorRecuperado));
            return null;
        });

    }

    @Test
    public void cuandoModificoUnEntrenadorEsteCambioSePersiste(){
        Runner.runInSession(() -> {
            entrenadorDAO.crearEntrenador(ash);
            Entrenador entrenadorRecuperado = entrenadorDAO.getEntrenador(ash.getNombre());
            entrenadorRecuperado.setExperiencia(100);
            entrenadorDAO.actualizarEntrenador(entrenadorRecuperado);
            entrenadorRecuperado = entrenadorDAO.getEntrenador( ash.getNombre());
            assertEquals(100, entrenadorRecuperado.getExperiencia());
            return null;
        });
    }

}
