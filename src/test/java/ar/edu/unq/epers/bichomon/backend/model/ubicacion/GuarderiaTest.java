package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GuarderiaTest {

    private Guarderia guarderia = new Guarderia("Viejito copado");
    private Bicho bicho;
    List<Bicho> abandonados;

    @Before
    public void setup() {
        Especie especie = mock(Especie.class);

        bicho = mock(Bicho.class);

        abandonados = new ArrayList<>();
        abandonados.add(bicho);
        guarderia.setAbandonados(abandonados);
    }


    @Test
    public void cuandoLePidoUnBichoRandomAUnGuarderiaMeDevuelveUnoDeSusBichosAbandonados() {
        Entrenador entrenador = mock(Entrenador.class);
        assertEquals(bicho, guarderia.buscarBichoPara(entrenador));
    }

    @Test
    public void cuandoLePidoUnBichoRandomAUnaGuarderiaYUnoDeSusDosBichosAbandonadosYaPertenecioAlEntrenadorMeDevuelveElOtro(){
        Entrenador entrenador = mock(Entrenador.class);
        when(bicho.pertenecioA(entrenador)).thenReturn(true);
        Bicho bicho2 = mock(Bicho.class);
        guarderia.setAbandonados(Arrays.asList(bicho, bicho2));
        assertEquals(bicho2, guarderia.buscarBichoPara(entrenador));
    }

    @Test
    public void cuandoLePidoUnBichoRandomAUnGuarderiaCuandoElBichoYaPertenecioAlEntrenadorDevuelveNull() {
        Entrenador entrenador = mock(Entrenador.class);

        List<Entrenador> yaPertenecio = new ArrayList<>();
        yaPertenecio.add(entrenador);
        bicho.setEntrenadoresALosQuePertenecio(yaPertenecio);
        when(bicho.pertenecioA(entrenador)).thenReturn(true);

        assertEquals(null, guarderia.buscarBichoPara(entrenador));
    }
}
