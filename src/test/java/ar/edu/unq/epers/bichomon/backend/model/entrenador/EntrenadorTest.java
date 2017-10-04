package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.factories.EspecieFactory;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EntrenadorTest {

    private Entrenador entrenador = new Entrenador("Ash Ketchum");
    private Bicho bicho = new Bicho( new EspecieFactory().dinosaurio() );
    Nivel nivel1;
    private Bicho bichoCampeon;

    @Before
    public void setup() {
        nivel1 = new Nivel(1, 100 , 10);
        entrenador.setNivel(nivel1);
    }

    @Test
    public void cuandoAgregoUnBichoLaCantidadDeBichosAumentaEnUno(){
        entrenador.agregarBicho( bicho );
        assertEquals( entrenador.getBichos().size(), 1 );
    }

    @Test
    public void cuandoUnEntrenadorGanaExperienciaEstaSeSumaAlaCantidadDeExperienciaQueYaTenia() throws Exception {
        entrenador.setExperiencia(15);

        entrenador.ganarExperiencia(10);
        assertEquals(25, entrenador.getExperiencia());
    }

    @Test
    public void cuandoUnEntrenadorGanaExperienciaSuficienteComoParaSubirDeNivelEsteSeActualiza() throws Exception {
        Nivel nivel2 = new Nivel(2, 200, 20);
        nivel1.setNivelProximo(nivel2);
        entrenador.setExperiencia(90);
        entrenador.ganarExperiencia(20);

        assertEquals(nivel2, entrenador.getNivel());
    }

    @Test
    public void cuandoLeDigoAUnEntrenadorQueEmpieceUnDueloConUnoDeSusPokemonYEsteTieneMuchaMasEnergiaEntoncesEsElGanador() throws Exception {
        setDueloData(100,50);

        Integer experienciaVieja = entrenador.getExperiencia();
        ResultadoCombate resultadoCombate = entrenador.retarADuelo(bicho);

        assertEquals(resultadoCombate.getGanador(), bicho);
        assertEquals(entrenador.getExperiencia(), experienciaVieja + 10);
    }

    @Test
    public void cuandoLeDigoAUnEntrenadorQueEmpieceUnDueloConUnoDeSusPokemonYEsteTieneMuchaMenosEnergiaEntoncesEsElPerdedor() throws Exception {
        setDueloData(50, 100);

        Integer experienciaVieja = entrenador.getExperiencia();
        ResultadoCombate resultadoCombate = entrenador.retarADuelo(bicho);

        assertEquals(resultadoCombate.getGanador(), bichoCampeon);
        assertEquals(entrenador.getExperiencia(), experienciaVieja + 10);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void cuandoUnEntrenadorGanaExperienciaSuficienteComoParaSubirDeNivelPeroEsteNoExisteTiraUnaExcepcion() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("No tiene mas niveles para subir");

        entrenador.setExperiencia(90);
        entrenador.ganarExperiencia(20);
    }

    @Test
    public void cuandoLePidoAUnEntrenadorQueEmpieceUnDueloYEstaEnUnaGuarderiaTiraUnaExcepcion() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("No se puede luchar en esta ubicacion");

        entrenador.setUbicacion(new Guarderia("Casita del viejo"));
        entrenador.retarADuelo(bicho);
    }

    @Test
    public void cuandoLePidoAUnEntrenadorQueEmpieceUnDueloYEstaEnUnPuebloTiraUnaExcepcion() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("No se puede luchar en esta ubicacion");

        entrenador.setUbicacion(new Pueblo("Pueblo paleta"));
        entrenador.retarADuelo(bicho);
    }

    private void setDueloData(Integer energiaRetador, Integer energiaCampeon) {
        Dojo dojoActual = new Dojo("Gimnasio de Brock");
        bichoCampeon = new Bicho();
        bichoCampeon.setEnergia(energiaCampeon);
        dojoActual.setCampeones(new ArrayList<>(Arrays.asList(bichoCampeon)));
        entrenador.setUbicacion(dojoActual);
        entrenador.agregarBicho(bicho);
        bicho.setEnergia(energiaRetador);
    }

}
