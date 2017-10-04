package ar.edu.unq.epers.bichomon.backend.persistence.leaderBoard;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.List;

/**
 * Created by whish on 03/07/17.
 */
public interface LeaderBoardDao {

    List<Entrenador> campeones();
}
