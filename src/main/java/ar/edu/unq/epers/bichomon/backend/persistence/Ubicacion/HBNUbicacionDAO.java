package ar.edu.unq.epers.bichomon.backend.persistence.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.persistence.Ubicacion.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HBNUbicacionDAO implements UbicacionDAO {
    @Override
    public void guardar(Ubicacion ubicacion) {
        Session session = Runner.getCurrentSession();
        session.save(ubicacion);
    }

    @Override
    public void nuevaUbicacion(Ubicacion ubicacion) {
        Session session = Runner.getCurrentSession();
        session.save(ubicacion);
    }

    @Override
    public Ubicacion recuperar(String nombre) {
        Session session = Runner.getCurrentSession();
        return session.get(Ubicacion.class, nombre);
    }

    @Override
    public void actualizarUbicacion(Ubicacion ubicacion) {
        Session session = Runner.getCurrentSession();
        session.update(ubicacion);
    }

    @Override
    public List<Dojo> todosLosDojos() {
        Session session = Runner.getCurrentSession();
        String hql = "from Ubicacion u where u.type = :dojo";

        Query query = session.createQuery(hql,  Ubicacion.class);
        query.setParameter("dojo", "dojo");

        return query.getResultList();
    }

    @Override
    public List<Bicho> todosLosCampeones() {
        Session session = Runner.getCurrentSession();
        String hql = "select distinct c from Dojo d join d.campeones as c";

        Query query = session.createQuery(hql,  Bicho.class);

        return query.getResultList();
    }
}
