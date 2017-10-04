package ar.edu.unq.epers.bichomon.backend.service.HBN;

import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.persistence.camino.N4JCaminoDAO;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.HBNEntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.HBNMapaService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.HBNUbicacionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino.AEREO;
import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino.MARITIMO;
import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino.TERRESTRE;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class HBNMapaServiceTest {

    HBNEntrenadorService entrenadorService;
    HBNUbicacionService ubicacionService;
    Entrenador entrenadorAsh;
    Entrenador entrenadorMisty;
    Pueblo puebloPaleta;
    Pueblo puebloPelota;
    HBNMapaService mapaService;
    N4JCaminoDAO n4JCaminoDAO;

    private Camino caminoTerrestre;
    private Camino caminoMaritimo;
    private Camino caminoAereo;

    @Before
    public void contruirModelo(){
        puebloPaleta = new Pueblo("Paleta");
        puebloPelota = new Pueblo("Pelota");
        entrenadorAsh = new Entrenador("Ash");
        entrenadorMisty = new Entrenador("Misty");
        entrenadorService = new HBNEntrenadorService();
        ubicacionService = new HBNUbicacionService();
        mapaService = new HBNMapaService();
        n4JCaminoDAO = new N4JCaminoDAO();

        mapaService.crearUbicacion(puebloPelota);
        mapaService.crearUbicacion(puebloPaleta);

        this.caminoTerrestre = new Camino( TERRESTRE, 1 );
        this.caminoAereo = new Camino( AEREO, 3 );
        this.caminoMaritimo = new Camino( MARITIMO, 5 );
    }

    @Test
    public void cuandoMuevoUnEntrenadorDeUbicacionEstoSeRefrejaEnLaPeristencia() throws Exception {
        /** esto crea una Ubicacion en HBN y en N4J */

        mapaService.conectarUbicaciones("Pelota","Paleta", caminoMaritimo );

        entrenadorAsh.cobrarMonedas(200);
        entrenadorAsh.setUbicacion(puebloPelota);
        entrenadorService.crearEntrenador(entrenadorAsh);

        mapaService.mover("Ash", "Paleta");

        Entrenador entrenadorRecuperado = entrenadorService.getEntrenador("Ash");
        assertEquals(entrenadorRecuperado.getMonedas(),195);
        assertEquals(entrenadorRecuperado.getUbicacion().getNombre(), "Paleta");
    }

    @Test
    public void cuandoLePreguntoAUnaUbicacionLaCantidadDeEntrenadoresMeDevuelveLaCantidadAgregada(){
        entrenadorAsh.setUbicacion(puebloPaleta);
        entrenadorMisty.setUbicacion(puebloPaleta);

        entrenadorService.crearEntrenador(entrenadorAsh);

        assertEquals(2, mapaService.cantidadEntrenadores("Paleta"));
    }

    @Test
    public void cuandoLePidoLosConectadosAlPuebloPaletaMevuelveElPuebloPelota(){
        mapaService.conectarUbicaciones(puebloPaleta.getNombre(), puebloPelota.getNombre(),caminoTerrestre);

        assertEquals( mapaService.conectados(puebloPaleta.getNombre(), "TERRESTRE").size(), 1);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void cuandoLeDigoAUnEntrenadorQueSeMuevaYNoLeAlcanzanLasMonedasTiraUnaExcepcion() throws Exception {
        exception.expect(RuntimeException.class);
        exception.expectMessage("El camino es muy costoso");

        entrenadorService.crearEntrenador(entrenadorAsh);
        mapaService.conectarUbicaciones("Pelota","Paleta", caminoMaritimo);

        entrenadorAsh.setUbicacion(puebloPelota);
        entrenadorService.actualizarEntrenador(entrenadorAsh);
        mapaService.mover(entrenadorAsh.getNombre(), puebloPaleta.getNombre());
    }

    @Test
    public void cuandoLeDigoAUnEntrenadorQueSeMuevaYNoExisteEseCaminoTiraUnaExepcion() throws Exception {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Ubicacion muy lejana");

        entrenadorService.crearEntrenador(entrenadorAsh);

        entrenadorAsh.setUbicacion(puebloPelota);
        entrenadorService.actualizarEntrenador(entrenadorAsh);
        mapaService.mover(entrenadorAsh.getNombre(), puebloPaleta.getNombre());
    }
    @Test
    public void cuandoLeDigoAUnEntrenadorQueSeMuevaMasCortoYNoLeAlcanzanLasMonedasTiraUnaExcepcion() throws Exception {
        exception.expect(RuntimeException.class);
        exception.expectMessage("El camino es muy costoso");

        entrenadorService.crearEntrenador(entrenadorAsh);
        mapaService.conectarUbicaciones("Pelota","Paleta", caminoMaritimo);

        entrenadorAsh.setUbicacion(puebloPelota);
        entrenadorService.actualizarEntrenador(entrenadorAsh);
        mapaService.moverMasCorto(entrenadorAsh.getNombre(), puebloPaleta.getNombre());
    }
    @Test
    public void cuandoMuevoAunEntrenadorConUnCaminoMasCortoDescuentoLasMonedasYLLegaAFinDeMes() throws Exception {
        mapaService.conectarUbicaciones("Pelota","Paleta", caminoMaritimo);

        entrenadorAsh.cobrarMonedas(200);
        entrenadorAsh.setUbicacion(puebloPelota);
        entrenadorService.crearEntrenador(entrenadorAsh);

        mapaService.moverMasCorto("Ash", "Paleta");

        Entrenador entrenadorRecuperado = entrenadorService.getEntrenador("Ash");
        assertEquals(entrenadorRecuperado.getMonedas(),195);
        assertEquals(entrenadorRecuperado.getUbicacion().getNombre(), "Paleta");
    }

    @After
    public void tearDown(){
        new DataServiceHBN().clearDb();
        n4JCaminoDAO.clearDb();
    }
}
