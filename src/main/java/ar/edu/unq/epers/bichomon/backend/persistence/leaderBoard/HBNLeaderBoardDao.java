package ar.edu.unq.epers.bichomon.backend.persistence.leaderBoard;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by whish on 03/07/17.
 */
public class HBNLeaderBoardDao implements LeaderBoardDao{

    private Session sesion;

    @Override
    public List<Entrenador> campeones() {
        Session session = Runner.getCurrentSession();

        String hql = "select e from Entrenador e join e.bichos b join b.dojo d join d.campeones";

        return session.createQuery(hql, Entrenador.class).getResultList();
    }
}
