package ar.edu.unq.epers.bichomon.backend.service.evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

/**
 * Created by whish on 23/06/17.
 */
public interface FeedService {

    List<Evento> feedEntrenador(Entrenador enrenador);

    List<Evento> feedUbicacion(Entrenador entrenador);

}
