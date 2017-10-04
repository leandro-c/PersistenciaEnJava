package ar.edu.unq.epers.bichomon.backend.service.Mongo;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.evento.Arribo;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.persistence.evento.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.service.evento.MongoEventoService;
import ar.edu.unq.epers.bichomon.backend.service.evento.MongoFeedService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by whish on 23/06/17.
 */
public class EventoDAOTest {

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
    private EventoDAO eventoDao;

    @Before
    public void prepararDatos(){
        this.feedService = new MongoFeedService();
        this.eventoService = new MongoEventoService();
        this.eventoDao = new EventoDAO(Evento.class);
        eventoService.deleteAll();

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
        eventoService.guardarEvento(new Arribo(pikachu, ubicacion5));
        eventoService.guardarEvento(new Arribo(jenny, ubicacion6));
    }

    @Test
    public void cuandoBuscoABrockPorNombreMeTraeUnEvento(){
        List<Evento>  eventos = eventoDao.getByEntrenador( brock );
        assertEquals( eventos.size(),1);
    }
}
