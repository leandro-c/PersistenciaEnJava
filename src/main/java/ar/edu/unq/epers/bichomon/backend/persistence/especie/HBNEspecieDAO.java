package ar.edu.unq.epers.bichomon.backend.persistence.especie;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;


import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HBNEspecieDAO implements EspecieDAO {

    private Session sesion;

    @Override
    public void crearEspecie(Especie especie) {
        setSesion();
        sesion.save(especie);
    }

    @Override
    public Especie getEspecie(String nombre) {
        setSesion();
        return sesion.get(Especie.class, nombre);
    }

    @Override
    public List<Especie> getAllEspecies() {
        setSesion();
        Session session = Runner.getCurrentSession();

        String hql = "from Especie e";

        Query<Especie> query = session.createQuery(hql,  Especie.class);
        return query.getResultList();
    }

    @Override
    public void agregarBicho(String nombreEspecie) {
        setSesion();
        Query q = sesion.createQuery("from Especie where nombre = :tranId ", Especie.class);
        q.setParameter("tranId", nombreEspecie);
        Especie especie = (Especie) q.list().get(0);

        especie.setCantidadBichos(especie.getCantidadBichos() + 1);
        sesion.update(especie);
    }

    public void setSesion() {
        sesion = Runner.getCurrentSession();
    }

	@Override
	public Boolean tengoEvolucion(Especie especie) {
		// TODO Auto-generated method stub
		return true;
	}
}
