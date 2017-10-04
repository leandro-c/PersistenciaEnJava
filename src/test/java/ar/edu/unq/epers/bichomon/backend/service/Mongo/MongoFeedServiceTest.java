package ar.edu.unq.epers.bichomon.backend.service.Mongo;

import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.evento.Arribo;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.evento.MongoEventoService;
import ar.edu.unq.epers.bichomon.backend.service.evento.MongoFeedService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.HBNMapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.HBNUbicacionService;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class MongoFeedServiceTest {

    private Ubicacion ubicacion1;
    private Ubicacion ubicacion2;
    private Ubicacion ubicacion3;
    private Ubicacion ubicacion4;
    private Ubicacion ubicacion5;
    private Ubicacion ubicacion6;
    private Entrenador brock;
    private Entrenador misty;
    private Entrenador oak;
    private Entrenador paleta;
    private Entrenador pikachu;
    private Entrenador jenny;
    private MongoEventoService eventoService;
    private MongoFeedService feedService;
    private MapaService mapaService;
    private UbicacionService ubicacionService;

    @Before
    public void prepararDatos(){
        this.feedService = new MongoFeedService();
        this.eventoService = new MongoEventoService();
        this.mapaService = new HBNMapaService();
        this.ubicacionService = new HBNUbicacionService();


        this.ubicacion1 = new Dojo("Gimnasio de Brock");
        this.ubicacion2 = new Dojo("Gimnasio de Misty");
        this.ubicacion3 = new Guarderia("Profesor Oak");
        this.ubicacion4 = new Pueblo("Pueblo Paleta");
        this.ubicacion5 = new Dojo("Gimnasio Pikachu");
        this.ubicacion6 = new Guarderia("Guarderia de Jenny");
        this.brock   = new Entrenador("Brock");
        this.misty   = new Entrenador("Misty");
        this.oak     = new Entrenador("Oak");
        this.paleta  = new Entrenador("Paleta");
        this.pikachu = new Entrenador("Pikachu");
        this.jenny   = new Entrenador("Jenny");

        eventoService.guardarEvento(new Arribo(brock, ubicacion1));
        eventoService.guardarEvento(new Arribo(misty, ubicacion2));
        eventoService.guardarEvento(new Arribo(oak, ubicacion3));
        eventoService.guardarEvento(new Arribo(paleta, ubicacion4));
        eventoService.guardarEvento(new Arribo(brock, ubicacion5));
        eventoService.guardarEvento(new Arribo(jenny, ubicacion5));
    }

    @Test
    public void cuandoLePidoLosEventosDeUnUsuarioQueNoTieneEventosMeDevuelveListaVacia(){
        List<Evento> eventos = feedService.feedEntrenador(this.pikachu);

        assertEquals(eventos.size(),0);
    }

    @Test
    public void cuandoLePidoLosEventosDeUnUsuarioConDosEventosMeLosDevuelve(){
        List<Evento> eventos = feedService.feedEntrenador(this.brock);

        assertEquals(eventos.size(),3);
    }

    @Test
    public void cuandoLePidoLosEventosDeUnaUbicacionAUnUsuarioYEstaNoTieneEventosDevuelveListaVacia() {
        brock.setUbicacion(ubicacion6);
        List<Evento> eventos = feedService.feedUbicacion(this.brock);

        assertEquals(eventos.size(),0);
    }

    @Test
    public void cuandoLePidoLosEventosDeUnaUbicacionAUnUsuarioYEstaTieneDosEventosLosDevuelve() {
        brock.setUbicacion(ubicacion5);
        List<Evento> eventos = feedService.feedUbicacion(this.brock);

        assertEquals(eventos.size(),2);
    }

    @Test
    public void cuandoLePidoLosEventosDeUnaUbicacionAUnUsuarioYEstaTieneDosEventosYSusConexionesTienenOtrosDosDevuelveLosCuatro() {
        brock.setUbicacion(ubicacion5);
        ubicacionService.nuevaUbicacion(ubicacion1);
        ubicacionService.nuevaUbicacion(ubicacion2);
        ubicacionService.nuevaUbicacion(ubicacion5);
        mapaService.conectarUbicaciones(ubicacion5.getNombre(), ubicacion1.getNombre(), new Camino(TipoCamino.AEREO, 10));
        mapaService.conectarUbicaciones(ubicacion5.getNombre(), ubicacion2.getNombre(), new Camino(TipoCamino.AEREO, 10));
        List<Evento> eventos = feedService.feedUbicacion(this.brock);

        assertEquals(eventos.size(),2);
    }

    @After
    public void tearDown(){
        eventoService.deleteAll();
        new DataServiceHBN().clearDb();
    }
}
