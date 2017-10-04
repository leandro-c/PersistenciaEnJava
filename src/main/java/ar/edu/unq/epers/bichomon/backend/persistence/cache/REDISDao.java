package ar.edu.unq.epers.bichomon.backend.persistence.cache;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import redis.clients.jedis.Jedis;

import java.util.List;

public abstract class REDISDao {

    protected Jedis jedis;

    public REDISDao() {
        this.jedis = CacheProvider.getInstance().getJedis();
    }

    public void clear() {
        jedis.flushAll();
    }

    public int size() {
        return this.jedis.keys("*").size();
    }

    public boolean exists(String key) {
        return this.jedis.exists(key);
    }

}
