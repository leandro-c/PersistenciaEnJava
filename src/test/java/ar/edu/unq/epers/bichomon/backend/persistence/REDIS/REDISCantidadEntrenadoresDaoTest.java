package ar.edu.unq.epers.bichomon.backend.persistence.REDIS;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.persistence.cache.REDISCantidadEntrenadoresDao;
import ar.edu.unq.epers.bichomon.backend.service.mapa.HBNMapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class REDISCantidadEntrenadoresDaoTest {

    private Pueblo puebloPaleta;
    private Pueblo puebloLavanda;
    private Entrenador ash;
    private Entrenador brock;
    private REDISCantidadEntrenadoresDao cantidadEntrenadores;
    MapaService mapaService = new HBNMapaService();

    @Before
    public void prepararDatos(){
        this.puebloPaleta = new Pueblo("paleta");
        this.puebloLavanda = new Pueblo("lavanda");
        this.ash = new Entrenador("ash");
        this.brock = new Entrenador("brock");
        this.cantidadEntrenadores = new REDISCantidadEntrenadoresDao();
        this.mapaService = new HBNMapaService();
    }

    @After
    public void tearDown(){
        this.cantidadEntrenadores.clear();
    }

    @Test
    public void cuandoPongoEnLaCacheUnaUbicacionYLePidoLaCantidadDeEntrenadoresMeDevuelveDos(){
        puebloPaleta.agregarEntrenador(ash);
        puebloPaleta.agregarEntrenador(brock);
        cantidadEntrenadores.put(puebloPaleta.getNombre(), puebloPaleta.getCantidadEntreadores());
        assertEquals(cantidadEntrenadores.get(puebloPaleta.getNombre()), (Integer) 2);
    }

    @Test
    public void cuandoMuevoUnEntrenadoAOtraUbicacionSeGuardaLaCantidadDeEntrenadoresDeCadaUbicacionEnCache() throws Exception {
        puebloPaleta.agregarEntrenador(ash);
        puebloPaleta.agregarEntrenador(brock);

        mapaService.mover(ash.getNombre(), puebloLavanda.getNombre());

        assertEquals(cantidadEntrenadores.get(puebloLavanda.getNombre()), (Integer) 1);
        assertEquals(cantidadEntrenadores.get(puebloPaleta.getNombre()), (Integer) 1);
    }

}
