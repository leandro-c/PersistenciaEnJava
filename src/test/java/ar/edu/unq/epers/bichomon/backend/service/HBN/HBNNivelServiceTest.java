package ar.edu.unq.epers.bichomon.backend.service.HBN;

import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HBNNivelServiceTest {

    private Nivel nivel;
    private NivelService nivelService = new NivelServiceHBN();

    @Before
    public void crearModelo() {
        nivel = new Nivel(10,1000, 200);
        DataService dataService = new DataServiceHBN();
        dataService.crearNiveles();
    }
    @After
    public void tearDown() { new DataServiceHBN().clearDb(); }

    @Test
    public void alGuardarYLuegoRecuperarSeObtienenObjetosSimilares() {
        this.nivelService.guardarNivel(nivel);

        Nivel otroNivel = this.nivelService.obtenerNivel(this.nivel.getNumero());
        assertTrue(this.nivel.getNumero() == otroNivel.getNumero());
    }

    @Test
    public void alGuardarLeCambioLaExperienciaMaximaYEstaCambia() {
        this.nivel.setExperienciaMaxima(10);
        this.nivelService.guardarNivel(nivel);
        this.nivelService.cambiarExperienciaMaxima(nivel, 100);

        Nivel nivelPersistido = this.nivelService.obtenerNivel(nivel.getNumero());
        assertEquals(100, nivelPersistido.getExperienciaMaxima());
    }

}
