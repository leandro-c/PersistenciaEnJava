package ar.edu.unq.epers.bichomon.backend.persistence.Nivel;

import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;

public class HBNNivelDAO implements NivelDAO{

    private Session sesion;

    @Before
    public void setSesion() {
        sesion = Runner.getCurrentSession();
    }

    @Override
    public void guardarNivel(Nivel nivel) {
        Session session = Runner.getCurrentSession();
        session.save(nivel);
    }

    @Override
    public Nivel obtenerNivel(int numero) {
        Session session = Runner.getCurrentSession();
        return session.get(Nivel.class, numero);
    }

    @Override
    public void cambiarExperienciaMaxima(int numeroNivel, int nuevaExperiencia) {
        setSesion();
        /*Query q = sesion.createQuery("from Nivel where numero = :tranId ", Nivel.class);
        q.setParameter("tranId", numeroNivel);
        Nivel nivel = (Nivel) q.list().get(0);

        nivel.setExperienciaMaxima(nuevaExperiencia);
        */
        Nivel nivel = obtenerNivel(numeroNivel);
        nivel.setExperienciaMaxima(nuevaExperiencia);
        sesion.update(nivel);
    }
}
