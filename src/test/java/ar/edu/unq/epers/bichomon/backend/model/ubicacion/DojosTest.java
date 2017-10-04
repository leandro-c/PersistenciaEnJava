package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.factories.EspecieFactory;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class DojosTest {

    private Dojo dojo = new Dojo("Gimnasio Brock");
    private Bicho bicho1, bicho2, bicho3;
    private Especie especie1, especie2;
    private List<Bicho> bichos = new ArrayList<>();

    @Test
    public void cuandoLePidoUnBichoRandomAUnDojoMeDevuelveUnoConLaMismaClaseDelCampeonActual() {
        Especie especie = mock(Especie.class);
        when(especie.primeraEvolucion()).thenReturn(especie);

        Bicho bicho = mock(Bicho.class);
        when(bicho.getEspecie()).thenReturn(especie);

        List<Bicho> campeones = new ArrayList<>();
        campeones.add(bicho);
        dojo.setCampeones(campeones);

        Entrenador entrenador = mock(Entrenador.class);
        assertEquals(dojo.campeonActual().getEspecie(), dojo.buscarBichoPara(entrenador).getEspecie());
    }

    @Test
    public void cuandoLePidoUnBichoRandomAUnDojoSinCampeonNoEncuentroNingunBicho() {
        Entrenador entrenador = mock(Entrenador.class);
        assertEquals(null, dojo.buscarBichoPara(entrenador));
    }

    @Test
    public void cuandoLeDigoAUnDojoQueRealiceUnDueloMeDevuelveUnResultadoCombateConElGanador() {
        Bicho campeon = crearBicho();
        Bicho retador = crearBicho();
        retador.setEnergia(100);
        campeon.setEnergia(50);
        dojo.setCampeones(new ArrayList<>(Arrays.asList(campeon)));

        ResultadoCombate resultadoCombate = dojo.retarADuelo(retador);

        assertEquals(resultadoCombate.getGanador(), retador);
    }

    @Test
    public void cuandoLeDigoAUnDojoQueRealiceUnDueloElBichoGanadorSeConvierteEnElNuevoCampeonYLosDosLuchadoresGananExperiencia() {
        Bicho campeon = crearBicho();
        Bicho retador = crearBicho();
        retador.setEnergia(100);
        campeon.setEnergia(50);
        dojo.setCampeones(new ArrayList<>(Arrays.asList(campeon)));

        dojo.retarADuelo(retador);

        assertEquals(dojo.campeonActual(), retador);
    }

    private Bicho crearBicho() {
        return new Bicho(new EspecieFactory().dinosaurio());
    }
}
