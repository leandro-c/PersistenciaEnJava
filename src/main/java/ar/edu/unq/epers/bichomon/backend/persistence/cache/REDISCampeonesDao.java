package ar.edu.unq.epers.bichomon.backend.persistence.cache;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import redis.clients.jedis.Jedis;

import java.util.List;

public class REDISCampeonesDao extends REDISDao {

    private String key;

    public REDISCampeonesDao() {
        super();
        this.key = "campeones";
    }

    public void put( List<Entrenador> entrenadores ) {
        jedis.setex(key, 60, JsonSerializer.toJson(entrenadores));
    }

    public List<Entrenador> get() {
        String value = this.jedis.get(key);
        return JsonSerializer.fromJsonList(value, Entrenador.class);
    }

    public boolean exists() {
        return jedis.exists(key);
    }
}
