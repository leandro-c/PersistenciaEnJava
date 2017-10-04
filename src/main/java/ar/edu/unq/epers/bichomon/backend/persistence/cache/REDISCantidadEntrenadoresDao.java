package ar.edu.unq.epers.bichomon.backend.persistence.cache;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import redis.clients.jedis.Jedis;

import java.util.List;

public class REDISCantidadEntrenadoresDao extends REDISDao {

    protected String generateKey(String ubicacion) {
        return "Ubicacion " + ubicacion;
    }

    public void put(String ubicacion, Integer cantidadEntrenadores ) {
        jedis.setex(generateKey(ubicacion), 60, JsonSerializer.toJson(cantidadEntrenadores));
    }

    public Integer get(String ubicacion) {
        String value = this.jedis.get(generateKey(ubicacion));
        return Integer.parseInt(value);
    }

    @Override
    public boolean exists(String ubicacion) {
        return this.jedis.exists(generateKey(ubicacion));
    }
}
