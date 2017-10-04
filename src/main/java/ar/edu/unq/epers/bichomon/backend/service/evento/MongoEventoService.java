package ar.edu.unq.epers.bichomon.backend.service.evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.persistence.GenericMongoDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.evento.EventoDAO;

import java.util.List;

public class MongoEventoService implements EventoService {

    EventoDAO mongoDAO = new EventoDAO(Evento.class);

    @Override
    public void deleteAll() { mongoDAO.deleteAll(); }

    @Override
    public void guardarEvento(Evento evento) { mongoDAO.save(evento); }

    @Override
    public void guardarEventos(List<Evento> eventos) { mongoDAO.save(eventos); }

    @Override
    public Evento get(String id) { return (Evento) mongoDAO.get(id); }

}
