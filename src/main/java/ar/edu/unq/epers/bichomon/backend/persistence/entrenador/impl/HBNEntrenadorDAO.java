package ar.edu.unq.epers.bichomon.backend.persistence.entrenador.impl;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.persistence.entrenador.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HBNEntrenadorDAO implements EntrenadorDAO {
    @Override
    public void crearEntrenador(Entrenador entrenador) {
        Session session = Runner.getCurrentSession();
        session.save(entrenador);
    }

    @Override
    public Entrenador getEntrenador(String nombre) {
        Session session = Runner.getCurrentSession();
        return session.get(Entrenador.class, nombre);
    }

    @Override
    public void actualizarEntrenador(Entrenador entrenador) {
        Session session = Runner.getCurrentSession();
        session.update(entrenador);
    }

    @Override
    public List<Entrenador> entrenadoresConEspecie(Especie especie) {
        Session session = Runner.getCurrentSession();
        Query query = session.createQuery( "select distinct e from Entrenador e join fetch e.bichos b join b.especie es where es.nombre = :especieId", Entrenador.class );
        query.setParameter("especieId", especie.getNombre());
        List<Entrenador> entrenadores = query.list();

        return entrenadores;
    }

}
