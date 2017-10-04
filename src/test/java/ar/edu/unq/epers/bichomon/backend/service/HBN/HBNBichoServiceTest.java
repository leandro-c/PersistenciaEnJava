package ar.edu.unq.epers.bichomon.backend.service.HBN;

import ar.edu.unq.epers.bichomon.backend.factories.EspecieFactory;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.bicho.HBNBichoService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.ELECTRICIDAD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class HBNBichoServiceTest {

    private Bicho bicho;
    private BichoService bichoService = new HBNBichoService();
    private EntrenadorService entrenadorService = new ServiceFactory().getEntrenadorService();
    private DataService dataService = new DataServiceHBN();
    private Entrenador entrenador;
    private Bicho bichoCampeon;
    private EspecieFactory especieFactory = new EspecieFactory();

    @Before
    public void crearModelo() {
        Especie especie = dataService.getEspecie();
        this.bicho = new Bicho(especie);
    }
    @After
    public void tearDown() { new DataServiceHBN().clearDb(); }

    @Test
    public void cuandoInicioUnDueloConUnEntrenadorParaUnBicho() throws Exception {
        setDueloData();

        ResultadoCombate resultado = bichoService.duelo(entrenador.getNombre(), bicho.getId());

        assertTrue(resultado.getAtaques().size() > 1);
        assertEquals(resultado.getGanador().getId(), bichoCampeon.getId());
    }

    private void setDueloData() {
        bicho.setEnergia(50);
        entrenador = new Entrenador("Juan");
        Dojo dojo = new Dojo("Liga de Johto");
        bichoCampeon = new Bicho(new EspecieFactory().dinosaurio());
        bichoCampeon.setEnergia(100);
        dojo.setCampeones(new ArrayList<>(Arrays.asList(bichoCampeon)));
        entrenador.setUbicacion(dojo);
        entrenador.agregarBicho(bicho);
        entrenador.setNivel(new Nivel(1, 100, 10));
        this.entrenadorService.crearEntrenador(entrenador);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void cuandoInicioUnDueloConUnEntrenadorParaUnBichoYNoEstaEnUnDojoTiraUnaExcepcion() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("No se puede luchar en esta ubicacion");

        Entrenador entrenador = new Entrenador("Juan");
        entrenador.setUbicacion(new Guarderia("Casita del viejo"));
        entrenador.agregarBicho(bicho);
        bichoCampeon = new Bicho(new EspecieFactory().dinosaurio());
        bichoCampeon.setEnergia(100);
        entrenador.agregarBicho(bichoCampeon);

        this.entrenadorService.crearEntrenador(entrenador);

        bichoService.duelo(entrenador.getNombre(), bicho.getId());
    }

    @Test
    public void cuandoUnEntrenadorAbandonaUnBichoEnUnaGuarderiaYanoLoTieneMas(){
        Bicho dragon = new Bicho( especieFactory.dragon() );
        Bicho tortuga = new Bicho( especieFactory.tortuga());
        entrenador = new Entrenador("Ash");
        entrenador.setUbicacion(new Guarderia("joy"));
        entrenador.agregarBicho(dragon);
        entrenador.agregarBicho(tortuga);
        entrenadorService.crearEntrenador(entrenador);
        bichoService.abandonar(entrenador.getNombre(), dragon.getId());
        entrenador = entrenadorService.getEntrenador(entrenador.getNombre());
        assertEquals(entrenador.getBichos().size(), 1);
    }

    @Test
    public void cuandoUnEntrenadorTieneUnSoloBichoNoPuedeAbandonarlo(){
        exception.expect(Exception.class);
        exception.expectMessage("Hasta las manos");

        Bicho dragon = new Bicho( especieFactory.dragon() );
        Bicho tortuga = new Bicho( especieFactory.tortuga());
        entrenador = new Entrenador("Ash");
        entrenador.setUbicacion(new Guarderia("joy"));
        entrenador.agregarBicho(dragon);
        entrenadorService.crearEntrenador(entrenador);
        bichoService.abandonar(entrenador.getNombre(), dragon.getId());
    }
    @Test
    public void evolucionarTest(){
    	Especie especie = new Especie();
    	Bicho bichoEvolucionado = new Bicho(especie);
    	when(especie.puedoEvolucionar(bichoEvolucionado)).thenReturn(true);	
    	Bicho evolucionado = this.bichoService.evolucionar(bichoEvolucionado.getId());
    	assertEquals(bichoEvolucionado.getEspecie(), especie);
    }
   
   
}
