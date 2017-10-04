package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PuebloTest {

    private Pueblo pueblo = new Pueblo("Pueblo paleta");
    private Especie especie;
    Map<Especie, Integer> especies;

    @Before
    public void setup() {
        especie = mock(Especie.class);

        Bicho bicho = mock(Bicho.class);
        when(bicho.getEspecie()).thenReturn(especie);

        especies = new HashMap<>();
        especies.put(especie, 100);
        pueblo.setEspeciesHabitantes(especies);
    }


    @Test
    public void cuandoLePidoUnBichoRandoMAUnPuebloMeDevuelveUnoConLaMismaClaseQueLaUnicaEspeciePosible() {
        Entrenador entrenador = mock(Entrenador.class);
        assertEquals(especie, pueblo.buscarBichoPara(entrenador).getEspecie());
    }

//    @Test
//    public void cuandoLePidoUnBichoRandomAUnPuebloSinPorcentajeDeEspeciePosibleDevuelveNull() {
//        especies.put(especie, 0);
//        Entrenador entrenador = mock(Entrenador.class);
//        assertEquals(null, pueblo.buscarBichoPara(entrenador));
//    }
}
