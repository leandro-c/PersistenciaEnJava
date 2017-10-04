package ar.edu.unq.epers.bichomon.backend.persistence.bicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class HBNBichoDAO implements BichoDAO {

    Session sesion;

    @Override
    public Bicho obtenerBicho(int bichoID) {
        setSesion();
        return sesion.get(Bicho.class, bichoID);
    }

    @Override
    public void guardarBicho(Bicho bicho) {
        sesion = Runner.getCurrentSession();
        sesion.save(bicho);
    }

    @Override
    public Bicho obtenerRandom() {
        setSesion();
        Bicho result = null;
        Criteria crit = sesion.createCriteria(Bicho.class);
        crit.setProjection(Projections.rowCount());
        int count = ((Number) crit.uniqueResult()).intValue();
        if (0 != count) {
            int index = new Random().nextInt(count);
            crit = sesion.createCriteria(Bicho.class);
            result = (Bicho) crit.setFirstResult(index).setMaxResults(1).uniqueResult();
        }
        return result;
    }

    @Override
    public void evolucionar(Bicho bicho) {
        sesion = Runner.getCurrentSession();
        sesion.update(bicho);
    }

    public void setSesion() {
        sesion = Runner.getCurrentSession();
    }
    
	public List<Bicho> getAll() {
		Session session = Runner.getCurrentSession();
		
		String hql = "from Bicho b ";
		
		Query<Bicho> query = session.createQuery(hql,  Bicho.class);
		return query.getResultList();
	}

	public void update(Bicho bicho){
		sesion = Runner.getCurrentSession();
		sesion.update(bicho);
	}
	public void delete(Bicho bicho){
		sesion = Runner.getCurrentSession();
		sesion.delete(bicho);
	}
}
