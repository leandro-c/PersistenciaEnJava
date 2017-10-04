package ar.edu.unq.epers.bichomon.backend.service.evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;

import java.util.List;

public interface EventoService {

    void deleteAll();

    void guardarEvento(Evento evento);

    void guardarEventos(List<Evento> eventos);

    Evento get(String id);

}
