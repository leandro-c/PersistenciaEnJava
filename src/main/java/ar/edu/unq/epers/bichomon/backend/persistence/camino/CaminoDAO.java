package ar.edu.unq.epers.bichomon.backend.persistence.camino;

import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by whish on 14/06/17.
 */
public interface CaminoDAO {

    void setCostos();

    void clearDb();

    void crearUbicacion(Ubicacion ubicacion);

    void conectarUbicaciones(Ubicacion origen, Ubicacion destino, Camino camino);

    Integer costoDirecto(Ubicacion origen, Ubicacion destino);

    boolean conectados( Ubicacion origen, Ubicacion destino );

    List<String> conectados (Ubicacion ubicacion, TipoCamino tipoCamino);

    public String getUbicacion(Ubicacion ubicacion);

}