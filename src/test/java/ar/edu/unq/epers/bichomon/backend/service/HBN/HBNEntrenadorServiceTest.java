package ar.edu.unq.epers.bichomon.backend.service.HBN;

import ar.edu.unq.epers.bichomon.backend.factories.EspecieFactory;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.HBNEntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class HBNEntrenadorServiceTest {

    private Entrenador entrenador;
    private HBNEntrenadorService entrenadorService = new HBNEntrenadorService();
    private EspecieFactory especieFactory = new EspecieFactory();

    @Before
    public void crearModelo() {
        this.entrenador = new Entrenador("Ash");
    }
    @After
    public void tearDown() { new DataServiceHBN().clearDb(); }

    @Test
    public void cuandoCreoUnEntrenadorYloRecuperoConsigoUnEntrenadorSimilar(){
        this.entrenadorService.crearEntrenador(entrenador);
        Entrenador entrenadorRecuperado = this.entrenadorService.getEntrenador(entrenador.getNombre());
        assertTrue(entrenador.equalEntrenador(entrenadorRecuperado));
    }

    @Test
    public void cuandoLeGuardoUnBichoAunEntrenadorEsteSeAgregaAlaListaDeBichos() {
        Bicho bicho = Mockito.mock(Bicho.class);
        entrenador.setUbicacion(new Pueblo("Paleta"));
        this.entrenadorService.crearEntrenador(entrenador);

        this.entrenadorService.capturarBicho(entrenador.getNombre(), bicho);

        Entrenador nuevoEntrenador = this.entrenadorService.getEntrenador(entrenador.getNombre());
        assertEquals(1, nuevoEntrenador.getBichos().size());
    }


}
