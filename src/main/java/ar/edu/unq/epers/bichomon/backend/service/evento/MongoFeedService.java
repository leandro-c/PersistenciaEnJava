package ar.edu.unq.epers.bichomon.backend.service.evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.persistence.evento.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.service.mapa.HBNMapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;

import java.util.ArrayList;
import java.util.List;

public class MongoFeedService implements FeedService {

    EventoDAO mongoDao = new EventoDAO(Evento.class);
    MapaService mapaService = new HBNMapaService();

    @Override
    public List<Evento> feedEntrenador(Entrenador entrenador) {

        return mongoDao.getByEntrenador( entrenador );

    }

    @Override
    public List<Evento> feedUbicacion(Entrenador entrenador) {
        Ubicacion ubicacionActual = entrenador.getUbicacion();
        List<Evento> res = mongoDao.getByUbicacion(ubicacionActual);
        List<Ubicacion> conexiones = mapaService.conectados(ubicacionActual);
        

        for (Ubicacion conexion : conexiones) {
            res.addAll(mongoDao.getByUbicacion(conexion));
        }
        return res;
    }
}
